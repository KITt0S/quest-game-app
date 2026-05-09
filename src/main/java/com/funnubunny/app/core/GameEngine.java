package com.funnubunny.app.core;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;

public class GameEngine implements GLEventListener {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    public static final int TARGET_FPS = 60;

    private GLWindow window;
    private FPSAnimator animator;

    private final Input input = new Input();

    public void start() {
        initializeWindow();
        Time.init();
        animator = new FPSAnimator(window, TARGET_FPS, true);
        animator.start();
    }

    private void initializeWindow() {
        GLProfile profile = GLProfile.get(GLProfile.GL3);
        GLCapabilities capabilities = new GLCapabilities(profile);
        capabilities.setHardwareAccelerated(true);
        capabilities.setOnscreen(true);
        window = GLWindow.create(capabilities);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setTitle("The Last Lighthouse");
        window.setResizable(true);
        window.setVisible(true);
        window.addGLEventListener(this);
        window.addKeyListener(input);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL3 gl = drawable.getGL().getGL3();
        gl.glViewport(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        gl.glClearColor(0.04f, 0.05f, 0.08f, 1.0f);
        System.out.println("====================================");
        System.out.println("OpenGL INITIALIZED");
        System.out.println("Version : " + gl.glGetString(GL3.GL_VERSION));
        System.out.println("Renderer: " + gl.glGetString(GL3.GL_RENDERER));
        System.out.println("====================================");
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
            System.out.println("Moving up");
        }
    }

    private void render(GL3 gl) {
        gl.glClear(GL3.GL_COLOR_BUFFER_BIT);
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
        if (animator != null && animator.isStarted()) {
            animator.stop();
        }

        if (window != null) {
            window.destroy();
        }
    }
}
