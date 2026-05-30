package com.funnubunny.app.state;

import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.command.commands.GameAnswer;
import com.funnubunny.app.command.commands.GameCommand;
import com.funnubunny.app.command.commands.VoidAnswer;
import com.funnubunny.app.command.commands.destroylighthouse.DestroyLighthouseCommand;
import com.funnubunny.app.command.commands.fixgenerator.RestoreGeneratorCommand;
import com.funnubunny.app.command.commands.moveplayer.MovePlayerCommand;
import com.funnubunny.app.command.commands.relightlighthouse.RelightLighthouseCommand;
import com.funnubunny.app.core.Input;
import com.funnubunny.app.core.Time;
import com.funnubunny.app.entity.Generator;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.DestroyedLighthouseEvent;
import com.funnubunny.app.event.events.RelightedLighthouseEvent;
import com.jogamp.newt.event.KeyEvent;

public class WorldStateSystem {
    private final WorldState worldState;
    private final WorldStateService worldStateService;

    public WorldStateSystem(WorldState worldState, WorldStateService worldStateService, CommandBus commandBus) {
        this.worldState = worldState;
        this.worldStateService = worldStateService;
        commandBus.register(MovePlayerCommand.class, this::movePlayer);
        commandBus.register(RestoreGeneratorCommand.class, this::restoreGenerator);
        commandBus.register(RelightLighthouseCommand.class, this::relightLighthouse);
        commandBus.register(DestroyLighthouseCommand.class, this::destroyLighthouse);
    }
    private VoidAnswer movePlayer(MovePlayerCommand command) {
        Player player = worldStateService.getPlayer();
        float dt = Time.getDeltaTime();

        switch (command.getDirection()) {
            case TOP -> player.getTransform().translate(0, player.getSpeed() * dt);
            case BOTTOM -> player.getTransform().translate(0, -player.getSpeed() * dt);
            case LEFT -> player.getTransform().translate(-player.getSpeed() * dt, 0);
            case RIGHT -> player.getTransform().translate(player.getSpeed() * dt, 0);
        }

        return new VoidAnswer();
    }

    private GameAnswer restoreGenerator(RestoreGeneratorCommand command) {
        worldState.getGenerator().setStatus(Generator.Status.RESTORED);
        return new VoidAnswer();
    }

    private VoidAnswer destroyLighthouse(DestroyLighthouseCommand command) {
        worldState.getLighthouse().setActive(false);
        return new VoidAnswer();
    }

    private VoidAnswer relightLighthouse(RelightLighthouseCommand command) {
        worldState.getLighthouse().setActive(true);
        return new VoidAnswer();
    }

}