package com.funnubunny.app.render.renderers;

import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.render.Renderable;
import com.funnubunny.app.dialoguebox.DialogueBox;

public class DialogueBoxRenderer implements Renderable {

    private final DialogueBox dialogueBox;

    public DialogueBoxRenderer(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
    }

    @Override
    public void render(RenderContext context) {
        if (!dialogueBox.isActive() || dialogueBox.getDialogue() == null) {
            return;
        }

        System.out.println("[DIALOGUE] " + dialogueBox.getDialogue().getCurrentLine());
    }

    @Override
    public int priority() {
        return 0;
    }
}
