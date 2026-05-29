package com.funnubunny.app.state;

import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.command.commands.GameAnswer;
import com.funnubunny.app.command.commands.VoidAnswer;
import com.funnubunny.app.command.commands.fixgenerator.RestoreGeneratorCommand;
import com.funnubunny.app.entity.Generator;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.DestroyedLighthouseEvent;
import com.funnubunny.app.event.events.RelightedLighthouseEvent;

public class WorldStateSystem {
    private final WorldState worldState;

    public WorldStateSystem(WorldState worldState, CommandBus commandBus, EventBus eventBus) {
        this.worldState = worldState;
        commandBus.register(RestoreGeneratorCommand.class, this::restoreGenerator);
        eventBus.register(RelightedLighthouseEvent.class, this::onRelightedLighthouse);
        eventBus.register(DestroyedLighthouseEvent.class, this::onDestroyedLighthouse);
    }

    private GameAnswer restoreGenerator(RestoreGeneratorCommand command) {
        worldState.getGenerator().setStatus(Generator.Status.RESTORED);
        return new VoidAnswer();
    }

    private void onRelightedLighthouse(RelightedLighthouseEvent event) {
        worldState.getLighthouse().setActive(true);
    }

    private void onDestroyedLighthouse(DestroyedLighthouseEvent event) {
        worldState.setBellRinging(false);
    }
}