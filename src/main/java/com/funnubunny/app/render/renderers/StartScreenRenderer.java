package com.funnubunny.app.render.renderers;

import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.state.EngineState;
import com.funnubunny.app.state.GameStateService;

public class StartScreenRenderer implements Renderable {
    private final GameStateService gameStateService;
    private final TextRenderer textRenderer;

    public StartScreenRenderer(GameStateService gameStateService, TextRenderer textRenderer) {
        this.gameStateService = gameStateService;
        this.textRenderer = textRenderer;
    }

    @Override
    public void render(RenderContext context) {
        if (gameStateService.getEngineState() != EngineState.STARTING) {
            return;
        }

        textRenderer.renderText(context.getGl(), "THE LAST LIGHTHOUSE", 150, 450, 250);
        textRenderer.renderText(context.getGl(), "Press <ENTER> To Start", 400, 250, 100);
    }

    @Override
    public int priority() {
        return 0;
    }
}
