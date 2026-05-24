package com.funnubunny.app.render;

import com.funnubunny.app.event.events.GameEvent;

public class DefaultEventTextHandler implements EventTextHandler<GameEvent> {

    @Override
    public String getText(GameEvent event) {
        return "";
    }
}
