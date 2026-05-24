package com.funnubunny.app.state;

import com.funnubunny.app.event.events.GameEvent;

public class EventStateService {

    private final EventState eventState;

    public EventStateService(EventState eventState) {
        this.eventState = eventState;
    }

    public <T extends GameEvent> T pop(Class<T> type) {
        return eventState.pop(type);
    }
}
