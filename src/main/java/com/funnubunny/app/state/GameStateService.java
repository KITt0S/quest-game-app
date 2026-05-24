package com.funnubunny.app.state;

import com.funnubunny.app.quest.QuestState;

public class GameStateService {
    private final GameState gameState;

    public GameStateService(GameState gameState) {
        this.gameState = gameState;
    }

    public QuestState getQuestState() {
        return QuestState.valueOf(gameState.getOrDefault(GameState.GameStateKey.QUEST_STATE, QuestState.NOT_STARTED.name()));
    }
}
