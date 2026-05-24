package com.funnubunny.app.state;

import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.GameEvent;
import com.funnubunny.app.event.events.InteractionEvent;
import com.funnubunny.app.event.events.QuestStateChangedEvent;
import com.funnubunny.app.event.events.StateChangedEvent;

public class EventStateSystem {

    private final EventState eventState;

    public EventStateSystem(EventState eventState, EventBus eventBus) {
        this.eventState = eventState;
        eventBus.register(InteractionEvent.class, this::onInteractionEvent);
        eventBus.register(StateChangedEvent.class, this::onStateChangedEvent);
    }

    private void onInteractionEvent(InteractionEvent event) {
        eventState.addEvent(InteractionEvent.class, event);
    }

    private <T extends GameEvent> void onStateChangedEvent(StateChangedEvent event) {
        eventState.addEvent(StateChangedEvent.class, event);
    }
}
