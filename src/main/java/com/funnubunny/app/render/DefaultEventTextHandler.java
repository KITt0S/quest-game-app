package com.funnubunny.app.render;

import com.funnubunny.app.event.GameEvent;

public class DefaultEventTextHandler implements EventTextHandler<GameEvent> {

    @Override
    public String getText(GameEvent event) {
        return "";
    }
}
