package com.funnubunny.app.state;

import com.funnubunny.app.entity.Generator;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.QuestStateChangedEvent;

public class GeneratorBehaviourSystem {
    private final WorldStateService worldStateService;

    public GeneratorBehaviourSystem(WorldStateService worldStateService, EventBus eventBus) {
        this.worldStateService = worldStateService;
        eventBus.register(QuestStateChangedEvent.class, this::onQuestStateChanged);
    }

    private void onQuestStateChanged(QuestStateChangedEvent event) {
        if (event.getQuestState() != QuestState.FOUND_ALL_NOTES) {
            return;
        }

        Generator generator = worldStateService.getGenerator();

        if (generator.getStatus() != Generator.Status.OFF) {
            return;
        }

        generator.setStatus(Generator.Status.BLINKING);
    }
}
