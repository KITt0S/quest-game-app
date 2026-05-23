package com.funnubunny.app.quest;

import com.funnubunny.app.entity.Entity;
import org.joml.Vector2f;

import java.util.UUID;

public class Note extends Entity implements Interactable {
    private final String id = UUID.randomUUID().toString();

    private final String text;

    private boolean collected = false;

    public Note(String text) {
        this.text = text;
        setSize(20, 20);
    }

    public String getId() {
        return id;
    }

    @Override
    public void interact() {
    }

    public boolean isCollected() {
        return collected;
    }

    public Vector2f getPosition() {
        return transform.getPosition();
    }

    public void collect() {
        collected = true;
    }

    public String getText() {
        return text;
    }
}
