package com.funnubunny.app.world;

import com.funnubunny.app.entity.Entity;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.opengl.GL3;

public class Lighthouse extends Entity {
    private boolean active = false;

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        mesh.render(gl);
        shader.detach(gl);
    }
}
