package com.funnubunny.app.ui;

import com.funnubunny.app.quest.Dialogue;
import com.jogamp.opengl.GL3;

public class DialogueBox extends UI {
    private Dialogue dialogue;
    private boolean active = false;

    public void show(Dialogue dialogue) {
        this.dialogue = dialogue;
        this.active = true;
        dialogue.reset();
    }

    public void next() {
        if (dialogue == null) {
            return;
        }
        
        if (dialogue.isFinished()) {
            hide();
            return;
        }

        dialogue.next();
    }

    private void hide() {
        active = false;
        dialogue = null;
    }

    public boolean isActive() {
        return active;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    @Override
    public void render(GL3 gl) {
        if (!active || dialogue == null) {
            return;
        }

        System.out.println("[DIALOGUE] " + dialogue.getCurrentLine());
    }
}
