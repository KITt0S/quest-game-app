package com.funnubunny.app.entity;

import com.funnubunny.app.core.Input;
import com.funnubunny.app.core.Time;
import com.jogamp.newt.event.KeyEvent;

public class Player extends Entity {
    private float speed = 200f;

    public Player() {
        super();
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
}
