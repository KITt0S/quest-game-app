package com.funnubunny.app.render.renderers;

import com.funnubunny.app.event.events.StateChangedEvent;
import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.state.EventStateService;

public class StateChangedEventRenderer extends EventRenderer<StateChangedEvent> {

    public StateChangedEventRenderer(EventStateService eventStateService, TextRenderer textRenderer) {
        super(StateChangedEvent.class, eventStateService, textRenderer);
    }
}
