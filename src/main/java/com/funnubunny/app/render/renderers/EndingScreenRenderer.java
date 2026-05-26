package com.funnubunny.app.render.renderers;

import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.state.QuestState;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.state.EngineState;
import com.funnubunny.app.state.GameStateService;

import java.util.List;

public class EndingScreenRenderer implements Renderable {
    private final GameStateService gameStateService;
    private final TextRenderer textRenderer;

    public EndingScreenRenderer(GameStateService gameStateService, TextRenderer textRenderer) {
        this.gameStateService = gameStateService;
        this.textRenderer = textRenderer;
    }

    @Override
    public void render(RenderContext context) {
        if (gameStateService.getEngineState() != EngineState.ENDING) {
            return;
        }

        QuestState questState = gameStateService.getQuestState();

        if (!List.of(QuestState.RELIGHT_ENDING, QuestState.DESTROY_ENDING).contains(questState)) {
            return;
        }

        textRenderer.renderText(context.getGl(), "GAME OVER", 380, 450, 250);
        textRenderer.renderText(context.getGl(), "Press <ENTER> To Restart", 380, 250, 100);
    }

    @Override
    public int priority() {
        return 0;
    }
}
