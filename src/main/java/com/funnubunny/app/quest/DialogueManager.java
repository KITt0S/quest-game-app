package com.funnubunny.app.quest;

public class DialogueManager {
    private Dialogue activeDialogue;

    private boolean active = false;

    public void start(Dialogue dialogue) {
        activeDialogue = dialogue;
        active = true;
        dialogue.reset();
        System.out.println(dialogue.getCurrentLine());
    }

    public void update() {
        if (!active) {
            return;
        }
    }

    public void next() {
        if (activeDialogue == null) {
            return;
        }

        if (activeDialogue.isFinished()) {
            end();
            return;
        }

        activeDialogue.next();
        System.out.println(activeDialogue.getCurrentLine());
    }

    public void end() {
        active = false;
        activeDialogue = null;
        System.out.println("[Dialogue ended]");
    }

    public boolean isActive() {
        return active;
    }
}
