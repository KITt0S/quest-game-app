package com.funnubunny.app.render.renderers;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.event.events.GameEvent;
import com.funnubunny.app.render.texthandlers.EventTextHandler;
import com.funnubunny.app.render.texthandlers.EventTextHandlerFactory;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.state.EventStateService;

public abstract class EventRenderer<T extends GameEvent> implements Renderable {
    private final EventStateService eventStateService;
    private final TextRenderer textRenderer;
    private final Class<T> type;

    private float deltaTime;

    private T event;

    public EventRenderer(Class<T> type, EventStateService eventStateService, TextRenderer textRenderer) {
        this.type = type;
        this.eventStateService = eventStateService;
        this.textRenderer = textRenderer;
    }

    @Override
    public void render(RenderContext context) {
        if (event == null) {
            event = pop();
        }

        if (event == null) {
            return;
        }

        deltaTime += Time.getDeltaTime();

        EventTextHandler<T> textHandler = EventTextHandlerFactory.getHandler((Class<T>) event.getClass());

        if (deltaTime < textHandler.duration()) {
            textRenderer.renderText(context.getGl(),  textHandler.getText(event), textHandler.position()[0], textHandler.position()[1], textHandler.scale());
            return;
        }

        deltaTime = 0.0f;
        event = null;
    }

    public T pop() {
        return eventStateService.pop(type);
    }

    @Override
    public int priority() {
        return 300;
    }
}
