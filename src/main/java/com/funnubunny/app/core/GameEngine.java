package com.funnubunny.app.core;

import com.funnubunny.app.audio.AudioManager;
import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.dialoguebox.DialogueBox;
import com.funnubunny.app.dialoguebox.DialogueBoxSystem;
import com.funnubunny.app.entity.Generator;
import com.funnubunny.app.entity.Lighthouse;
import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.graphics.*;
import com.funnubunny.app.graphics.text.BitmapFont;
import com.funnubunny.app.input.InputSystem;
import com.funnubunny.app.world.WorldExplorationService;
import com.funnubunny.app.interaction.InteractionPolicyService;
import com.funnubunny.app.interaction.InteractionSystem;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.note.NoteSystem;
import com.funnubunny.app.quest.Dialogue;
import com.funnubunny.app.quest.QuestSystem;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.render.RenderingSystem;
import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.render.renderers.*;
import com.funnubunny.app.sound.AmbientSoundSystem;
import com.funnubunny.app.sound.SoundEffectSystem;
import com.funnubunny.app.state.*;
import com.funnubunny.app.world.*;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

import java.util.ArrayList;
import java.util.List;

public class GameEngine implements GLEventListener {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS = 60;

    private GLWindow window;
    private FPSAnimator animator;

    private final Input input = new Input();

    private ShaderProgram colorShader;
    private ShaderProgram spriteShader;
    private ShaderProgram fogShader;
    private ShaderProgram generatorShader;
    private ShaderProgram lighthouseShader;
    private ShaderProgram treesShader;
    private ShaderProgram textShader;

    private Camera2D camera;

    private Player player;
    private NPC npc;
    private IslandScene islandScene;

    private GameState gameState;

    private DialogueBox dialogueBox;

    private CommandBus commandBus;
    private EventBus eventBus;

    private EndingSequenceSystem endingSequenceSystem;
    private InputSystem inputSystem;
    private CameraSystem cameraSystem;
    private WeatherStateSystem weatherStateSystem;
    private WorldExplorationSystem worldExplorationSystem;
    private RenderingSystem renderingSystem;

    private AmbientSoundSystem ambientSoundSystem;

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
        generatorShader = new ShaderProgram(gl, "/shaders/generator.vert", "/shaders/generator.frag");
        lighthouseShader = new ShaderProgram(gl, "/shaders/lighthouse.vert", "/shaders/lighthouse.frag");
        treesShader = new ShaderProgram(gl, "/shaders/trees.vert", "/shaders/trees.frag");
        textShader = new ShaderProgram(gl, "/shaders/text.vert", "/shaders/text.frag");

        camera = new Camera2D(WIDTH, HEIGHT);

        player = new Player();
        player.setSprite(new Sprite(new Texture("/textures/player.png")));

        npc = new NPC("Old Keeper",
                new Dialogue(List.of(
                        "The lighthouse went dark...",
                        "Something is wrong in the fog.",
                        "Find the power source.")));
        npc.setSprite(new Sprite(new Texture("/textures/old_keeper.png")));
        npc.setSize(50.0f, 50.0f);

        islandScene = new IslandScene();

        Texture treesTexture = new Texture("/textures/trees.png");
        Sprite treesSprite = new Sprite(treesTexture);
        islandScene.setTreesSprite(treesSprite);

        Texture inactiveTexture = new Texture("/textures/inactive_lighthouse.png");
        Sprite inactiveSprite = new Sprite(inactiveTexture);
        Texture activeTexture = new Texture("/textures/active_lighthouse.png");
        Sprite activeSprite = new Sprite(activeTexture);
        Lighthouse lighthouse = new Lighthouse();
        lighthouse.setSprites(inactiveSprite, activeSprite);

        Texture inactiveGeneratorTexture = new Texture("/textures/inactive_generator.png");
        Sprite inactiveGeneratorSprite = new Sprite(inactiveGeneratorTexture);
        Texture activeGeneratorTexture = new Texture("/textures/active_generator.png");
        Sprite activeGeneratorSprite = new Sprite(activeGeneratorTexture);
        Generator generator = new Generator(new Sprite[]{inactiveGeneratorSprite, activeGeneratorSprite});

        dialogueBox = new DialogueBox();

        Texture fogTexture = new Texture("/textures/fog.png");
        Sprite fogSprite = new Sprite(fogTexture);

        List<Note> notes = new ArrayList<>();

        Texture noteTexture = new Texture("/textures/note.png");
        Sprite noteSprite = new Sprite(noteTexture);

        Note note1 = new Note("Fog swallowed the northern ship.\nThe light was already awake.");
        note1.setSprite(noteSprite);
        note1.getTransform().setPosition(-350, 120);

        Note note2 = new Note("Keeper says the light protects us.\nThen why do the bells ring underwater?");
        note2.setSprite(noteSprite);
        note2.getTransform().setPosition(250, -180);

        Note note3 = new Note("Do not let him relight the tower.");
        note3.setSprite(noteSprite);
        note3.getTransform().setPosition(420, 260);

        notes.add(note1);
        notes.add(note2);
        notes.add(note3);

        AudioManager audioManager = new AudioManager();
        audioManager.load("wind", "/audio/wind.wav");
        audioManager.load("bell", "/audio/bell.wav");
        audioManager.load("note", "/audio/note.wav");
        audioManager.load("lightkeeper", "/audio/lightkeeper.wav");

        gameState = new GameState();
        WorldState worldState = new WorldState(player, npc, notes, generator, lighthouse, islandScene);
        EventState eventState = new EventState();
        WeatherState weatherState = new WeatherState(new FogState(fogSprite), true);

        GameStateService gameStateService = new GameStateService(gameState);
        WorldStateService worldStateService = new WorldStateService(worldState);
        EventStateService eventStateService = new EventStateService(eventState);
        WeatherStateService weatherStateService = new WeatherStateService(weatherState);
        InteractionPolicyService interactionPolicyService = new InteractionPolicyService(gameStateService, worldStateService);
        WorldExplorationService explorationService = new WorldExplorationService(worldStateService);

        commandBus = new CommandBus();
        eventBus = new EventBus();

        new GameStateSystem(gameStateService, commandBus, eventBus);
        endingSequenceSystem = new EndingSequenceSystem(gameStateService);
        new EventStateSystem(eventState, eventBus);
        inputSystem = new InputSystem(commandBus);
        cameraSystem = new CameraSystem(camera, player);
        new InteractionSystem(commandBus, eventBus, worldStateService, interactionPolicyService, explorationService);
        new NoteSystem(commandBus, worldStateService);
        new QuestSystem(commandBus, worldStateService, eventBus);
        new DialogueBoxSystem(dialogueBox, worldStateService, commandBus);
        new WorldStateSystem(worldState, commandBus, eventBus);
        weatherStateSystem = new WeatherStateSystem(weatherStateService, eventBus);
        worldExplorationSystem = new WorldExplorationSystem(gameStateService, worldStateService, eventBus);
        new GeneratorBehaviourSystem(worldStateService, eventBus);

        ambientSoundSystem = new AmbientSoundSystem(audioManager, weatherStateService, worldStateService);
        new SoundEffectSystem(audioManager, eventBus);

        new GameResetSystem(gameStateService, worldStateService, weatherStateService, commandBus, ambientSoundSystem);

        Texture fontTexture = new Texture("/textures/font.png");
        BitmapFont bitmapFont = new BitmapFont(fontTexture);

        TextRenderer textRenderer = new TextRenderer(gl, bitmapFont, textShader);

        renderingSystem = new RenderingSystem();

        renderingSystem.register(new StartScreenRenderer(gameStateService, textRenderer));
        renderingSystem.register(new EndingScreenRenderer(gameStateService, textRenderer));
        renderingSystem.register(new PlayerRenderer(gameStateService, worldStateService, spriteShader));
        renderingSystem.register(new NpcRenderer(gameStateService, worldStateService, spriteShader));
        renderingSystem.register(new GeneratorRenderer(gameStateService, worldStateService, generatorShader));
        renderingSystem.register(new LighthouseRenderer(gameStateService, worldStateService, lighthouseShader));
        renderingSystem.register(new NoteRenderer(gameStateService, worldStateService, spriteShader));
        renderingSystem.register(new IslandSceneRenderer(gameStateService, worldStateService, colorShader, treesShader));
        renderingSystem.register(new FogRenderer(gameStateService, weatherStateService, fogShader));
        renderingSystem.register(new DialogueBoxRenderer(gameStateService, dialogueBox, textRenderer));
        renderingSystem.register(new InteractionEventRenderer(gameStateService, eventStateService, textRenderer));
        renderingSystem.register(new StateChangedEventRenderer(gameStateService, eventStateService, textRenderer));
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        Time.update();
        update();
        render(gl);
    }

    private void update() {
        handleGlobalInput();
        player.update();
        inputSystem.update();
        cameraSystem.update();
        worldExplorationSystem.update();
        weatherStateSystem.update();
        ambientSoundSystem.update();
        endingSequenceSystem.update();
    }

    private void handleGlobalInput() {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            stop();
        }
    }

    private void render(GL3 gl) {
        renderingSystem.render(new RenderContext(gl, camera));
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

        if (colorShader != null) {
            colorShader.delete(gl);
        }

        if (spriteShader != null) {
            spriteShader.delete(gl);
        }

        if (generatorShader != null) {
            generatorShader.delete(gl);
        }

        if (lighthouseShader != null) {
            lighthouseShader.delete(gl);
        }

        if (fogShader != null) {
            fogShader.delete(gl);
        }

        if (treesShader != null) {
            treesShader.delete(gl);
        }

        if (animator != null) {
            animator.stop();
        }

        if (window != null) {
            window.destroy();
        }
    }
}
