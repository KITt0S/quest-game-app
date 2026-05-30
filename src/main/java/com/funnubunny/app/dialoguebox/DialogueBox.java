package com.funnubunny.app.dialoguebox;

import com.funnubunny.app.quest.Dialogue;
import lombok.Getter;

@Getter
public class DialogueBox {

    private Dialogue dialogue;

    public void show(Dialogue dialogue) {
        this.dialogue = dialogue;
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
        dialogue = null;
    }

    public boolean isActive() {
        return dialogue != null;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }
}
