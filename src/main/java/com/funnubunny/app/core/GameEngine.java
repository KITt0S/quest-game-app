package com.funnubunny.app.core;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.graphics.*;
import com.funnubunny.app.quest.Dialogue;
import com.funnubunny.app.quest.QuestManager;
import com.funnubunny.app.quest.QuestState;
import com.funnubunny.app.ui.DialogueBox;
import com.funnubunny.app.ui.HUD;
import com.funnubunny.app.world.FogSystem;
import com.funnubunny.app.world.IslandScene;
import com.funnubunny.app.world.Lighthouse;
import com.funnubunny.app.world.WorldState;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

import java.util.List;

public class GameEngine implements GLEventListener {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS = 60;

    private static final float INTERACTION_DISTANCE = 60f;

    private GLWindow window;
    private FPSAnimator animator;

    private final Input input = new Input();

    private ShaderProgram colorShader;
    private ShaderProgram spriteShader;
    private ShaderProgram fogShader;

    private Mesh colorQuad;
    private Mesh spriteQuad;

    private SpriteRenderer spriteRenderer;

    private Camera2D camera;

    private Player player;
    private NPC npc;
    private IslandScene islandScene;
    private Lighthouse lighthouse;

    private QuestManager questManager;
    private WorldState worldState;

    private DialogueBox dialogueBox;
    private HUD hud;

    private FogSystem fogSystem;

    public void start() {
        initializeWindow();
        Time.init();
        animator = new FPSAnimator(window, FPS, true);
        animator.start();
    }

    private void initializeWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setHardwareAccelerated(true);
        capabilities.setOnscreen(true);
        window = GLWindow.create(capabilities);
        window.setSize(WIDTH, HEIGHT);
        window.setTitle("The Last Lighthouse");
        window.setResizable(true);
        window.setVisible(true);
        window.addGLEventListener(this);
        window.addKeyListener(input);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glViewport(0, 0, WIDTH, HEIGHT);
        gl.glClearColor(0.04f, 0.05f, 0.08f, 1.0f);
        gl.glEnable(GL3.GL_BLEND);
        gl.glBlendFunc(GL3.GL_SRC_ALPHA, GL3.GL_ONE_MINUS_SRC_ALPHA);

        System.out.println("====================================");
        System.out.println("OpenGL INITIALIZED");
        System.out.println("Version : " + gl.glGetString(GL3.GL_VERSION));
        System.out.println("Renderer: " + gl.glGetString(GL3.GL_RENDERER));
        System.out.println("====================================");

        colorShader = new ShaderProgram(gl, "/shaders/color.vert", "/shaders/color.frag");
        spriteShader = new ShaderProgram(gl, "/shaders/sprite.vert", "/shaders/sprite.frag");
        fogShader = new ShaderProgram(gl, "/shaders/fog.vert", "/shaders/fog.frag");

        camera = new Camera2D(WIDTH, HEIGHT);

        float[] shapeVertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f
        };

        float[] vertices = {
                // x y z u v
                -0.5f, 0.5f, 0f, 0f, 1f,
                -0.5f, -0.5f, 0f, 0f, 0f,
                0.5f, -0.5f, 0f, 1f, 0f,
                0.5f, 0.5f, 0f, 1f, 1f
        };

        float[] spriteVertices = {
                // x y z u v
                -0.5f, 0.5f, 0f, 0f, 1f,
                -0.5f, -0.5f, 0f, 0f, 0f,
                0.5f, -0.5f, 0f, 1f, 0f,
                0.5f, 0.5f, 0f, 1f, 1f
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        colorQuad = Mesh.getColorMesh(gl, shapeVertices, indices);
        spriteQuad = Mesh.getSpriteMesh(gl, spriteVertices, indices);

        spriteRenderer = new SpriteRenderer(spriteQuad);

        player = new Player();
        player.setSprite(new Sprite(new Texture("/textures/player.png")));
        player.setSpriteRenderer(new SpriteRenderer(spriteQuad));

        npc = new NPC("Old Keeper",
                new Dialogue(List.of(
                        "The lighthouse went dark...",
                        "Something is wrong in the fog.",
                        "Find the power source.")));
        npc.setMesh(colorQuad);

        islandScene = new IslandScene(colorQuad);
        lighthouse = new Lighthouse();
        lighthouse.setMesh(colorQuad);

        questManager = new QuestManager();
        worldState = new WorldState();

        dialogueBox = new DialogueBox();
        hud = new HUD();

        Texture fogTexture = new Texture("/textures/fog.png");
        Sprite fogSprite = new Sprite(fogTexture);
        fogSystem = new FogSystem(fogSprite, spriteRenderer, fogShader);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        Time.update();
        update();
        render(gl);
    }

    private void update() {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            stop();
        }

        player.update();

        float dx = player.getPosition().x - npc.getPosition().x;
        float dy = player.getPosition().y - npc.getPosition().y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        boolean canInteract = distance < INTERACTION_DISTANCE;

        hud.setCanInteract(canInteract);
        hud.setQuestState(questManager.getState());

        if (canInteract && Input.isKeyPressed(KeyEvent.VK_E)) {
            if (!dialogueBox.isActive()) {
                dialogueBox.show(npc.getDialogue());
                questManager.setState(QuestState.TALKED_TO_KEEPER);
            }
        }

        if (dialogueBox.isActive() && Input.isKeyPressed(KeyEvent.VK_SPACE)) {
            dialogueBox.next();
        }

        worldState.updateFromQuest(questManager.getState());

        islandScene.setLighthouseOn(worldState.isLighthouseOn());
        lighthouse.setActive(worldState.isLighthouseOn());

        camera.follow(player.getPosition(), Time.getDeltaTime());
        camera.update();

        fogSystem.update(Time.getDeltaTime());
    }

    private void render(GL3 gl) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        islandScene.render(gl, colorShader, camera);
        player.render(gl, spriteShader, camera);
        npc.render(gl, colorShader, camera);
        lighthouse.render(gl, colorShader, camera);
        fogSystem.render(gl, camera);
        dialogueBox.render(gl);
        hud.render(gl);
    }

    private void stop() {
        if (animator != null) {
            animator.stop();
        }

        if (window != null) {
            window.destroy();
        }

        System.exit(0);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();

        if (colorQuad != null) {
            colorQuad.delete(gl);
        }

        if (spriteQuad != null) {
            spriteQuad.delete(gl);
        }

        if (colorShader != null) {
            colorShader.delete(gl);
        }

        if (animator != null) {
            animator.stop();
        }

        if (window != null) {
            window.destroy();
        }
    }
}
