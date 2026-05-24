package com.funnubunny.app.render.texthandlers;

import com.funnubunny.app.event.events.GameEvent;

public interface EventTextHandler<T extends GameEvent> {

    String getText(T event);

    default float[] position() {
        return new float[] {400, 360};
    }

    default float scale() {
        return 100.0f;
    }

    default float duration() {
        return 3.0f;
    }
}
