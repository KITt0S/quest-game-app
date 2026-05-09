package com.funnubunny.app.core;

import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

public class GameEngine implements GLEventListener {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int FPS = 60;

    private GLWindow window;
    private FPSAnimator animator;

    private final Input input = new Input();

    private ShaderProgram shaderProgram;
    private Mesh quad;
    private Camera2D camera;

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

        shaderProgram = new ShaderProgram(gl, "/shaders/default.vert", "/shaders/default.frag");

        camera = new Camera2D(WIDTH, HEIGHT);

        float[] vertices = {
                -50f,  50f, 0f,
                -50f, -50f, 0f,
                50f, -50f, 0f,
                50f,  50f, 0f
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        quad = new Mesh(gl, vertices, indices);
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

        if (Input.isKeyPressed(KeyEvent.VK_W)) {
            camera.move(0, 200 * Time.getDeltaTime());
        }

        if (Input.isKeyPressed(KeyEvent.VK_S)) {
            camera.move(0, -200 * Time.getDeltaTime());
        }

        if (Input.isKeyPressed(KeyEvent.VK_A)) {
            camera.move(-200 * Time.getDeltaTime(), 0);
        }

        if (Input.isKeyPressed(KeyEvent.VK_D)) {
            camera.move(200 * Time.getDeltaTime(), 0);
        }

        camera.update();
    }

    private void render(GL3 gl) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
        shaderProgram.use(gl);
        shaderProgram.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        quad.render(gl);
        shaderProgram.detach(gl);
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

        if (shaderProgram != null) {
            shaderProgram.delete(gl);
        }

        if (animator != null) {
            animator.stop();
        }

        if (window != null) {
            window.destroy();
        }
    }
}
