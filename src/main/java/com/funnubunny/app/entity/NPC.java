package com.funnubunny.app.entity;

import com.funnubunny.app.quest.Dialogue;
import com.funnubunny.app.quest.Interactable;

public class NPC extends Entity implements Interactable {
    private final String name;
    private final Dialogue dialogue;

    public NPC(String name, Dialogue dialogue) {
        super();
        this.name = name;
        this.dialogue = dialogue;
    }

    @Override
    public void update() {

    }

    @Override
    public void interact() {
        System.out.println(name + ":");
        System.out.println(dialogue.getCurrentLine());
    }

    public Dialogue getDialogue() {
        return dialogue;
    }
}
