package com.funnubunny.app.render.renderers;

import com.funnubunny.app.event.events.InteractionEvent;
import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.state.EventStateService;
import com.funnubunny.app.state.GameStateService;

public class InteractionEventRenderer extends EventRenderer<InteractionEvent> {

    public InteractionEventRenderer(GameStateService gameStateService, EventStateService eventStateService, TextRenderer textRenderer) {
        super(InteractionEvent.class, gameStateService, eventStateService, textRenderer);
    }
}
