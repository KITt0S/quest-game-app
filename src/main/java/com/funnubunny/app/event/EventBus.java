package com.funnubunny.app.event;

import java.util.ArrayList;
import java.util.List;

public class EventBus {

    private final List<EventListener> listeners = new ArrayList<>();

    public void register(EventListener listener) {
        listeners.add(listener);
    }

    public void emit(GameEvent event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }

    public interface EventListener {
        void onEvent(GameEvent event);
    }
}
