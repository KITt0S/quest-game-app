package com.funnubunny.app.graphics;

import com.jogamp.opengl.GL3;

public interface Renderable {

    void render(GL3 gl, ShaderProgram shader, Camera2D camera);
}
