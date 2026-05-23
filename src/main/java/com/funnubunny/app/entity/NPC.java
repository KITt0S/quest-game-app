package com.funnubunny.app.entity;

import com.funnubunny.app.quest.Dialogue;
import org.joml.Vector2f;

public class NPC extends Entity {
    private final String name;
    private final Dialogue dialogue;

    public NPC(String name, Dialogue dialogue) {
        super();
        this.name = name;
        this.dialogue = dialogue;
        setSize(45f, 60f);
        setColor(0.7f, 0.65f, 0.5f);
        transform.setPosition(0, -150);
    }

    @Override
    public void update() {

    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public Vector2f getPosition() {
        return transform.getPosition();
    }
}
