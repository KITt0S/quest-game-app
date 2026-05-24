package com.funnubunny.app.event;

import com.funnubunny.app.event.events.GameEvent;

import java.util.*;

public class EventBus {

    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();

    public <T extends GameEvent> void register(Class<T> type, EventListener<T> listener) {
        listeners.computeIfAbsent(type, aClass -> new ArrayList<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <T extends GameEvent> void emit(T event) {
        for (Map.Entry<Class<?>, List<EventListener<?>>> entry : listeners.entrySet()) {

            Class<?> listenerType = entry.getKey();

            if (listenerType.isAssignableFrom(event.getClass())) {
                for (EventListener<?> listener : entry.getValue()) {
                    ((EventListener<T>) listener).onEvent(event);
                }
            }
        }
    }

    public interface EventListener<T extends GameEvent> {

        void onEvent(T event);
    }
}
