package com.funnubunny.app.state;

import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.DestroyedLighthouseEvent;
import com.funnubunny.app.event.events.RelightedLighthouseEvent;

public class WorldStateSystem {
    private final WorldState worldState;

    public WorldStateSystem(WorldState worldState, EventBus eventBus) {
        this.worldState = worldState;
        eventBus.register(RelightedLighthouseEvent.class, this::onRelightedLighthouse);
        eventBus.register(DestroyedLighthouseEvent.class, this::onDestroyedLighthouse);
    }

    private void onRelightedLighthouse(RelightedLighthouseEvent event) {
        worldState.getLighthouse().setActive(true);
    }

    private void onDestroyedLighthouse(DestroyedLighthouseEvent event) {
        worldState.setBellRinging(false);
    }
}