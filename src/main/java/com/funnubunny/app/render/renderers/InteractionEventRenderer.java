package com.funnubunny.app.render.renderers;

import com.funnubunny.app.event.events.GameEvent;
import com.funnubunny.app.event.events.InteractionEvent;
import com.funnubunny.app.render.TextRenderer;
import com.funnubunny.app.state.EventStateService;

public class InteractionEventRenderer extends EventRenderer<InteractionEvent> {

    public InteractionEventRenderer(EventStateService eventStateService, TextRenderer textRenderer) {
        super(InteractionEvent.class, eventStateService, textRenderer);
    }

    @Override
    public float[] position() {
        return new float[] {300, 360};
    }
}
