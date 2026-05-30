package com.funnubunny.app.entity;

import java.util.List;

public class NPC extends Entity {
    private final String name;
    private final List<String> dialogue;

    public NPC(String name, List<String> dialogue) {
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

    public List<String> getDialogue() {
        return dialogue;
    }
}
