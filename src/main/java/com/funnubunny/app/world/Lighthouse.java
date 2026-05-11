package com.funnubunny.app.world;

import com.funnubunny.app.entity.Entity;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.opengl.GL3;
import org.joml.Vector2f;

public class Lighthouse extends Entity {
    private boolean active = false;

    public Lighthouse() {
        super();
        setSize(350, 350);
        setColor(0.85f, 0.85f, 0.75f);
        transform.setPosition(0, 340);
    }

    public void setActive(boolean active) {
        this.active = active;

        if (active) {
            setColor(1.0f, 0.95f, 0.7f);
        } else {
            setColor(0.85f, 0.85f, 0.75f);
        }
    }

    @Override
    public void update() {

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
