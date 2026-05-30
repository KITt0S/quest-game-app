package com.funnubunny.app.render.renderers;

import com.funnubunny.app.dialoguebox.DialogueBox;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.state.EngineState;
import com.funnubunny.app.state.GameStateService;

public class DialogueBoxRenderer implements Renderable {
    private final GameStateService gameStateService;
    private final DialogueBox dialogueBox;
    private final TextRenderer textRenderer;

    public DialogueBoxRenderer(GameStateService gameStateService, DialogueBox dialogueBox, TextRenderer textRenderer) {
        this.gameStateService = gameStateService;
        this.dialogueBox = dialogueBox;
        this.textRenderer = textRenderer;
    }

    @Override
    public void render(RenderContext context) {
        if (gameStateService.getEngineState() != EngineState.PLAYING) {
            return;
        }

        if (!dialogueBox.isActive()) {
            return;
        }

        textRenderer.renderText(context.getGl(), dialogueBox.getDialogue().getCurrentLine(), 0, 0);
    }

    @Override
    public int priority() {
        return 300;
    }
}
