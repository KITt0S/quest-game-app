package com.funnubunny.app.state;

import com.funnubunny.app.command.*;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.quest.QuestState;

public class GameStateSystem {
    private final GameState gameState;

    public GameStateSystem(GameState gameState, CommandBus commandBus) {
        this.gameState = gameState;
        commandBus.register(ChangeQuestStateCommand.class, this::changeQuestState);
        commandBus.register(GetQuestStateCommand.class, this::getQuestState);
    }

    public GameAnswer changeQuestState(ChangeQuestStateCommand command) {
        gameState.set(GameState.GameStateKey.QUEST_STATE, command.getQuestState().name());
        return new VoidAnswer();
    }

    public GameAnswer getQuestState(GetQuestStateCommand command) {
        return new GetQuestStateAnswer(QuestState.valueOf(gameState.getOrDefault(GameState.GameStateKey.QUEST_STATE, QuestState.NOT_STARTED.name())));
    }
}
