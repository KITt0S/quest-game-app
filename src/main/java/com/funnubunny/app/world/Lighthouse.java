package com.funnubunny.app.world;

import com.funnubunny.app.entity.Entity;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.graphics.Sprite;
import com.jogamp.opengl.GL3;
import org.joml.Vector2f;

public class Lighthouse extends Entity {
    private boolean active = false;
    private Sprite[] sprites;

    public Lighthouse() {
        super();
        setSize(350, 350);
        transform.setPosition(0, 340);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setSprites(Sprite inactive, Sprite active) {
        sprites = new Sprite[2];
        sprites[0] = inactive;
        sprites[1] = active;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    @Override
    public void update() {

    }

    public Vector2f getPosition() {
        return transform.getPosition();
    }
}
