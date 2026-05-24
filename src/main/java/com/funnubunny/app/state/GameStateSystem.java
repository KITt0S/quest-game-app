package com.funnubunny.app.state;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.*;
import com.funnubunny.app.command.commands.changequeststate.ChangeQuestStateCommand;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateAnswer;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateCommand;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.QuestStateChangedEvent;
import com.funnubunny.app.quest.QuestState;

public class GameStateSystem {
    private final GameState gameState;
    private final EventBus eventBus;

    public GameStateSystem(GameState gameState, CommandBus commandBus, EventBus eventBus) {
        this.gameState = gameState;
        this.eventBus = eventBus;
        commandBus.register(ChangeQuestStateCommand.class, this::changeQuestState);
        commandBus.register(GetQuestStateCommand.class, this::getQuestState);
    }

    public GameAnswer changeQuestState(ChangeQuestStateCommand command) {
        gameState.set(GameState.GameStateKey.QUEST_STATE, command.getQuestState().name());
        eventBus.emit(new QuestStateChangedEvent(command.getQuestState()));
        return new VoidAnswer();
    }

    public GameAnswer getQuestState(GetQuestStateCommand command) {
        return new GetQuestStateAnswer(QuestState.valueOf(gameState.getOrDefault(GameState.GameStateKey.QUEST_STATE, QuestState.NOT_STARTED.name())));
    }
}
