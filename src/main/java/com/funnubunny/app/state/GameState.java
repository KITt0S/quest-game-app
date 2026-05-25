package com.funnubunny.app.state;

import java.util.HashMap;
import java.util.Map;

public class GameState {
    private final Map<GameStateKey, String> state = new HashMap<>();

    public void put(GameStateKey key, String value) {
        state.put(key, value);
    }

    public String get(GameStateKey key) {
        return state.get(key);
    }

    public String getOrDefault(GameStateKey key, String defaultValue) {
        return state.getOrDefault(key, defaultValue);
    }

    public enum GameStateKey {
        QUEST_STATE,
        ENGINE_STATE
    }
}
