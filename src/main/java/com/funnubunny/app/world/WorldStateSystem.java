package com.funnubunny.app.world;

import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.RelightedLighthouseEvent;

public class WorldStateSystem {
    private final WorldState worldState;

    public WorldStateSystem(WorldState worldState, EventBus eventBus) {
        this.worldState = worldState;
        eventBus.register(RelightedLighthouseEvent.class, this::onRelightedLighthouse);
    }

    private void onRelightedLighthouse(RelightedLighthouseEvent event) {
        worldState.getLighthouse().setActive(true);
    }
}