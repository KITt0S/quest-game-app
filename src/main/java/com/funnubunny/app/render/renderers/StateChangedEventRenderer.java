package com.funnubunny.app.render.renderers;

import com.funnubunny.app.event.events.StateChangedEvent;
import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.state.EventStateService;
import com.funnubunny.app.state.GameStateService;

public class StateChangedEventRenderer extends EventRenderer<StateChangedEvent> {

    public StateChangedEventRenderer(GameStateService gameStateService, EventStateService eventStateService, TextRenderer textRenderer) {
        super(StateChangedEvent.class, gameStateService, eventStateService, textRenderer);
    }
}
