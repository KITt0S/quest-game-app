package com.funnubunny.app.state;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.quest.QuestState;

import java.util.List;

public class EndingSequenceSystem {
    private final GameStateService gameStateService;

    private float endingTimer = 0.0f;


    public EndingSequenceSystem(GameStateService gameStateService) {
        this.gameStateService = gameStateService;
    }

    public void update() {
        if (gameStateService.getEngineState() != EngineState.PLAYING) {
            return;
        }

        if (!List.of(QuestState.RELIGHT_ENDING, QuestState.DESTROY_ENDING).contains(gameStateService.getQuestState())) {
            return;
        }

        endingTimer += Time.getDeltaTime();

        if (endingTimer >= 8f) {

            gameStateService.setEngineState(
                    EngineState.ENDING
            );

            endingTimer = 0f;
        }
    }
}
