package com.funnubunny.app.state;

import com.funnubunny.app.event.events.GameEvent;

import java.util.*;

public class EventState {

    private final Map<Class<? extends GameEvent>, List<? extends GameEvent>> events;

    public EventState() {
        events = new HashMap<>();
    }

    public  <T extends GameEvent, E extends T> void addEvent(Class<T> type, E event) {
        @SuppressWarnings("unchecked")
        List<T> list = (LinkedList<T>) events.computeIfAbsent(type, aClass -> new LinkedList<T>());
        list.add(event);
    }

    public <T extends GameEvent> T pop(Class<T> type) {
        @SuppressWarnings("unchecked")
        LinkedList<T> list = (LinkedList<T>) events.get(type);

        if (list == null) {
            return null;
        }

        if (list.isEmpty()) {
            return null;
        }

        return list.removeFirst();
    }
}
