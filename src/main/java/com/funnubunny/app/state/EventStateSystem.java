package com.funnubunny.app.state;

import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.NoteCollectedEvent;

public class EventStateSystem {

    private final EventState eventState;

    public EventStateSystem(EventState eventState, EventBus eventBus) {
        this.eventState = eventState;
        eventBus.register(NoteCollectedEvent.class, this::onNoteCollected);
    }

    public void onNoteCollected(NoteCollectedEvent event) {
        eventState.addEvent(event);
    }
}
