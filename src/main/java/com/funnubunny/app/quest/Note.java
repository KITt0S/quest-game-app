package com.funnubunny.app.quest;

import com.funnubunny.app.entity.Entity;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.opengl.GL3;
import org.joml.Vector2f;

public class Note extends Entity implements Interactable {

    private final Clue clue;

    private boolean collected = false;

    public Note(Clue clue) {
        this.clue = clue;
        setSize(20, 20);
    }

    @Override
    public void update() {

    }

    @Override
    public void interact() {
        if (collected) {
            return;
        }

        collected = true;

        clue.discover();

        System.out.println("\n--- FOUND NOTE ---");
        System.out.println(clue.getText());
        System.out.println("------------------\n");
    }

    public boolean isCollected() {
        return collected;
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
