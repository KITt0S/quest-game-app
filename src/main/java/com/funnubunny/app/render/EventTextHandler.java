package com.funnubunny.app.render;

import com.funnubunny.app.event.events.GameEvent;

public interface EventTextHandler<T extends GameEvent> {

    String getText(T event);
}
