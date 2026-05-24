package com.funnubunny.app.render.texthandlers;

import com.funnubunny.app.event.events.*;

import java.util.Map;

public class EventTextHandlerFactory {
    private static final Map<Class<? extends GameEvent>, EventTextHandler<? extends GameEvent>> handlers = Map.ofEntries(
            Map.entry(NoteCollectedEvent.class, new NoteCollectedEventTextHandler()),
            Map.entry(ReachedLighthouseEvent.class, new ReachedLighthouseEventTextHandler()),
            Map.entry(RelightedLighthouseEvent.class, new RelightedLighthouseEventTextHandler()),
            Map.entry(DestroyedLighthouseEvent.class, new DestroyedLighthouseEventTextHandler()),
            Map.entry(QuestStateChangedEvent.class, new QuestStateChangedEventTextHandler()));

    @SuppressWarnings("unchecked")
    public static <T extends GameEvent> EventTextHandler<T> getHandler(Class<T> type) {
        return (EventTextHandler<T>) handlers.getOrDefault(type, new DefaultEventTextHandler());
    }
}
