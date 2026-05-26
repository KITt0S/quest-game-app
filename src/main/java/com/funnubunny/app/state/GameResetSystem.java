package com.funnubunny.app.state;

import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.command.ResetGameCommand;
import com.funnubunny.app.command.commands.GameAnswer;
import com.funnubunny.app.command.commands.VoidAnswer;
import com.funnubunny.app.sound.AmbientSoundSystem;

public class GameResetSystem {
    private final GameStateService gameStateService;
    private final WorldStateService worldStateService;
    private final WeatherStateService weatherStateService;
    private final AmbientSoundSystem ambientSoundSystem;

    public GameResetSystem(GameStateService gameStateService, WorldStateService worldStateService, WeatherStateService weatherStateService, CommandBus commandBus, AmbientSoundSystem ambientSoundSystem) {
        this.gameStateService = gameStateService;
        this.worldStateService = worldStateService;
        this.weatherStateService = weatherStateService;
        this.ambientSoundSystem = ambientSoundSystem;
        commandBus.register(ResetGameCommand.class, this::reset);
    }

    private GameAnswer reset(ResetGameCommand command) {
        gameStateService.setEngineState(EngineState.STARTING);
        gameStateService.setQuestState(QuestState.NOT_STARTED);
        worldStateService.getPlayer().getTransform().setPosition(0.0f, 0.0f);
        worldStateService.getNpc().getTransform().setPosition(0.0f, -150.0f);
        worldStateService.getNpc().getDialogue().reset();
        worldStateService.getNotes().forEach(note -> note.setCollected(false));
        worldStateService.getLighthouse().setActive(false);
        worldStateService.setBellRinging(true);
        weatherStateService.setFogStatus(FogState.Status.HALF);
        weatherStateService.setFogDensity(0.5f);
        weatherStateService.setWind(true);
        ambientSoundSystem.init();
        return new VoidAnswer();
    }
}
