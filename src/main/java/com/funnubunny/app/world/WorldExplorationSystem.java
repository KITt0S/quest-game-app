package com.funnubunny.app.world;

import com.funnubunny.app.entity.Player;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.ReachedLighthouseEvent;
import com.funnubunny.app.state.GameStateService;
import com.funnubunny.app.state.QuestState;
import com.funnubunny.app.state.WorldStateService;

public class WorldExplorationSystem {
    private final GameStateService gameStateService;
    private final WorldStateService worldStateService;
    private final EventBus eventBus;

    public WorldExplorationSystem(GameStateService gameStateService, WorldStateService worldStateService, EventBus eventBus) {
        this.gameStateService = gameStateService;
        this.worldStateService = worldStateService;
        this.eventBus = eventBus;
    }

    public void update() {
        handleReachLighthouse();
    }

    private void handleReachLighthouse() {
        if (gameStateService.getQuestState() != QuestState.FOUND_ALL_NOTES) {
            return;
        }

        Player player = worldStateService.getPlayer();
        Lighthouse lighthouse = worldStateService.getLighthouse();

        float distance = player.getPosition().distance(lighthouse.getPosition());

        boolean nearLighthouse = distance < WorldExplorationConstants.INTERACTION_DISTANCE;

        if (!nearLighthouse) {
            return;
        }

        eventBus.emit(new ReachedLighthouseEvent());
    }
}
