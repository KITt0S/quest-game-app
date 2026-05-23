package com.funnubunny.app.dialoguebox;

import com.funnubunny.app.quest.Dialogue;
import com.jogamp.opengl.GL3;
import lombok.Getter;

@Getter
public class DialogueBox {

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
}
