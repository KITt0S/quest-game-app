package com.funnubunny.app.entity;

import com.funnubunny.app.core.Input;
import com.funnubunny.app.core.Time;
import com.funnubunny.app.graphics.*;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.GL3;
import org.joml.Vector2f;

public class Player extends Entity {
    private float speed = 200f;

    public Player() {
        super();
        setSize(32f, 48f);
        setColor(0.8f, 0.85f, 1.0f);
    }

    @Override
    public void update() {
        float dt = Time.getDeltaTime();

        if (Input.isKeyPressed(KeyEvent.VK_W)) {
            transform.translate(0, speed * dt);
        }

        if (Input.isKeyPressed(KeyEvent.VK_S)) {
            transform.translate(0, -speed * dt);
        }

        if (Input.isKeyPressed(KeyEvent.VK_A)) {
            transform.translate(-speed * dt, 0);
        }

        if (Input.isKeyPressed(KeyEvent.VK_D)) {
            transform.translate(speed * dt, 0);
        }
    }

    public Vector2f getPosition() {
        return transform.getPosition();
    }

    @Override
    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {
        if (sprite == null || spriteRenderer == null) {
            return;
        }

        spriteRenderer.render(gl, shader, camera, sprite, transform, width, height);
    }
}
