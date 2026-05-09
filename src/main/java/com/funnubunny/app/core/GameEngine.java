package com.funnubunny.app.core;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.quest.Dialogue;
import com.funnubunny.app.quest.DialogueManager;
import com.funnubunny.app.quest.QuestManager;
import com.funnubunny.app.quest.QuestState;
import com.funnubunny.app.ui.DialogueBox;
import com.funnubunny.app.ui.HUD;
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

    private ShaderProgram shader;
    private Mesh quad;
    private Camera2D camera;

    private Player player;
    private NPC npc;
    private Lighthouse lighthouse;

    private QuestManager questManager;
    private WorldState worldState;
    
    private DialogueBox dialogueBox;
    private HUD hud;

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
        System.out.println("====================================");
        System.out.println("OpenGL INITIALIZED");
        System.out.println("Version : " + gl.glGetString(GL3.GL_VERSION));
        System.out.println("Renderer: " + gl.glGetString(GL3.GL_RENDERER));
        System.out.println("====================================");

        shader = new ShaderProgram(gl, "/shaders/default.vert", "/shaders/default.frag");

        camera = new Camera2D(WIDTH, HEIGHT);

        float[] vertices = {
                -25f, 25f, 0f,
                -25f, -25f, 0f,
                25f, -25f, 0f,
                25f, 25f, 0f
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        quad = new Mesh(gl, vertices, indices);
        player = new Player();
        player.setMesh(quad);

        npc = new NPC("Old Keeper",
                new Dialogue(List.of(
                        "The lighthouse went dark...",
                        "Something is wrong in the fog.",
                        "Find the power source.")));
        npc.setMesh(quad);

        lighthouse = new Lighthouse();
        lighthouse.setMesh(quad);
        lighthouse.getTransform().setPosition(200, 0);

        questManager = new QuestManager();
        worldState = new WorldState();

        dialogueBox = new DialogueBox();
        hud = new HUD();
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

        lighthouse.setActive(worldState.isLighthouseOn());

        camera.setPosition(player.getTransform().getPosition().x, player.getTransform().getPosition().y);
        camera.update();
    }

    private void render(GL3 gl) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        shader.use(gl);
        shader.setUniformBool(gl, "uLighthouseOn", worldState.isLighthouseOn());
        player.render(gl, shader, camera);
        npc.render(gl, shader, camera);
        lighthouse.render(gl, shader, camera);
        shader.detach(gl);
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

        if (quad != null) {
            quad.delete(gl);
        }

        if (shader != null) {
            shader.delete(gl);
        }

        if (animator != null) {
            animator.stop();
        }

        if (window != null) {
            window.destroy();
        }
    }
}
