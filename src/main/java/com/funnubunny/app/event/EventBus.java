package com.funnubunny.app.event;

import java.util.*;

public class EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

    public <T extends GameEvent> void register(Class<T> type, EventListener<T> listener) {
        listeners.computeIfAbsent(type, aClass -> new ArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameEvent> void emit(T event) {
        List<EventListener<?>> listeners = this.listeners.getOrDefault(event.getClass(), Collections.emptyList());

        for (EventListener<?> listener : listeners) {
            ((EventListener<T>) listener).onEvent(event);
        }
    }

    public interface EventListener<T extends GameEvent> {

        void onEvent(T event);
    }
}
