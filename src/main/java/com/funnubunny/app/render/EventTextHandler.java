package com.funnubunny.app.render;

import com.funnubunny.app.event.GameEvent;

public interface EventTextHandler<T extends GameEvent> {

    String getText(T event);
}
