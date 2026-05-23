package com.funnubunny.app.entity;

import com.funnubunny.app.graphics.*;
import com.jogamp.opengl.GL3;
import lombok.Getter;

public abstract class Entity {

    protected final Transform transform;

    protected Sprite sprite;

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

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public float[] getColor() {
        return new float[]{r, g, b};
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void update() {}

    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {}
}
