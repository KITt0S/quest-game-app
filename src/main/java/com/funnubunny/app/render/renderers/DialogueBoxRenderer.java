package com.funnubunny.app.render.renderers;

import com.funnubunny.app.dialoguebox.DialogueBox;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.render.TextRenderer;

public class DialogueBoxRenderer implements Renderable {
    private final DialogueBox dialogueBox;
    private final TextRenderer textRenderer;

    public DialogueBoxRenderer(DialogueBox dialogueBox, TextRenderer textRenderer) {
        this.dialogueBox = dialogueBox;
        this.textRenderer = textRenderer;
    }

    @Override
    public void render(RenderContext context) {
        if (!dialogueBox.isActive() || dialogueBox.getDialogue() == null) {
            return;
        }

        textRenderer.renderText(context.getGl(), dialogueBox.getDialogue().getCurrentLine(), 0, 0);
    }

    @Override
    public int priority() {
        return 300;
    }
}
