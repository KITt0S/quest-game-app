package com.funnubunny.app.state;

import com.funnubunny.app.event.GameEvent;

import java.util.*;

public class EventState {

    private final Deque<GameEvent> events;

    public EventState() {
        events = new ArrayDeque<>();
    }

    public void addEvent(GameEvent event) {
        events.add(event);
    }

    public GameEvent pop() {
        return events.pop();
    }
}
