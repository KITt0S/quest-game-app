package com.funnubunny.app.entity;

import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.opengl.GL3;

public abstract class Entity {

    protected final Transform transform;

    protected Mesh mesh;

    public Entity() {
        transform = new Transform();
    }

    public Transform getTransform() {
        return transform;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public abstract void update();

    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        mesh.render(gl);
        shader.detach(gl);
    }
}
