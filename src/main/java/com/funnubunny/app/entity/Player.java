package com.funnubunny.app.entity;

import com.funnubunny.app.core.Input;
import com.funnubunny.app.core.Time;
import com.jogamp.newt.event.KeyEvent;
import org.joml.Vector2f;

public class Player extends Entity {
    private float speed = 200f;

    public Player() {
        super();
        setSize(40f, 40f);
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
}
