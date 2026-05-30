package com.funnubunny.app.entity;

import com.funnubunny.app.core.Input;
import com.funnubunny.app.core.Time;
import com.funnubunny.app.graphics.*;
import com.jogamp.newt.event.KeyEvent;
import org.joml.Vector2f;

public class Player extends Entity {
    private float speed = 200f;

    public Player() {
        super();
        setSize(32f, 48f);
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2f getPosition() {
        return transform.getPosition();
    }

    public Sprite getSprite() {
        return sprite;
    }
}
