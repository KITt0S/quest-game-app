package com.funnubunny.app.state;

import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.command.ResetGameCommand;
import com.funnubunny.app.command.commands.GameAnswer;
import com.funnubunny.app.command.commands.VoidAnswer;
import com.funnubunny.app.command.commands.changeenginestate.ChangeEngineStateCommand;
import com.funnubunny.app.command.commands.changequeststate.ChangeQuestStateCommand;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.QuestStateChangedEvent;

public class GameStateSystem {
    private final GameStateService gameStateService;
    private final CommandBus commandBus;
    private final EventBus eventBus;

    public GameStateSystem(GameStateService gameStateService, CommandBus commandBus, EventBus eventBus) {
        this.gameStateService = gameStateService;
        this.eventBus = eventBus;
        this.commandBus = commandBus;
        commandBus.register(ChangeEngineStateCommand.class, this::changeEngineState);
        commandBus.register(ChangeQuestStateCommand.class, this::changeQuestState);
    }

    public GameAnswer changeEngineState(ChangeEngineStateCommand command) {
        if (gameStateService.getEngineState() == EngineState.STARTING) {
            gameStateService.setEngineState(EngineState.PLAYING);
            return new VoidAnswer();
        }

        if (gameStateService.getEngineState() == EngineState.ENDING) {
            commandBus.dispatch(new ResetGameCommand());
            return new VoidAnswer();
        }

        return new VoidAnswer();
    }

    public GameAnswer changeQuestState(ChangeQuestStateCommand command) {
        gameStateService.setQuestState(command.getQuestState());

        eventBus.emit(new QuestStateChangedEvent(command.getQuestState()));

        return new VoidAnswer();
    }
}
