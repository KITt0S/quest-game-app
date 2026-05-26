package com.funnubunny.app.state;

public class GameStateService {
    private final GameState gameState;

    public GameStateService(GameState gameState) {
        this.gameState = gameState;
        init();
    }

    private void init() {
        gameState.put(GameState.GameStateKey.ENGINE_STATE, EngineState.STARTING.name());
        gameState.put(GameState.GameStateKey.QUEST_STATE, QuestState.NOT_STARTED.name());
    }

    public void setQuestState(QuestState state) {
        gameState.put(GameState.GameStateKey.QUEST_STATE, state.name());
    }

    public QuestState getQuestState() {
        return QuestState.valueOf(gameState.get(GameState.GameStateKey.QUEST_STATE));
    }

    public void setEngineState(EngineState state) {
        gameState.put(GameState.GameStateKey.ENGINE_STATE, state.name());
    }

    public EngineState getEngineState() {
        return EngineState.valueOf(gameState.get(GameState.GameStateKey.ENGINE_STATE));
    }
}
