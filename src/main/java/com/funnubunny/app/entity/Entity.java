package com.funnubunny.app.entity;

import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.opengl.GL3;

public abstract class Entity {

    protected final Transform transform;

    protected Mesh mesh;

    protected float width = 50f;
    protected float height = 50f;

    protected float r = 1f;
    protected float g = 1f;
    protected float b = 1f;

    public Entity() {
        transform = new Transform();
    }

    public Transform getTransform() {
        return transform;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public abstract void update();

    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        shader.setUniformVec2(gl, "uPosition", transform.getPosition().x, transform.getPosition().y);
        shader.setUniformVec2(gl, "uScale", width, height);
        shader.setUniformVec3(gl, "uColor", r, g, b);
        shader.setUniformBool(gl, "uLighthouseOn", false);
        mesh.render(gl);
        shader.detach(gl);
    }
}
