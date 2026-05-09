package com.funnubunny.app.entity;

import org.joml.Vector2f;

public class Transform {

    private final Vector2f position;

    public Transform() {
        position = new Vector2f(0, 0);
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void translate(float x, float y) {
        position.add(x, y);
    }
}
