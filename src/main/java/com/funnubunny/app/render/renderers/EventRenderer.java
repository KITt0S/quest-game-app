package com.funnubunny.app.render.renderers;

import com.funnubunny.app.event.events.GameEvent;
import com.funnubunny.app.render.texthandlers.EventTextHandlerFactory;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.graphics.text.TextRenderer;
import com.funnubunny.app.state.EventStateService;

public abstract class EventRenderer<T extends GameEvent> implements Renderable {
    private final EventStateService eventStateService;
    private final TextRenderer textRenderer;
    private final Class<T> type;

    private long lastTime;

    private T event;

    public EventRenderer(Class<T> type, EventStateService eventStateService, TextRenderer textRenderer) {
        this.type = type;
        this.eventStateService = eventStateService;
        this.textRenderer = textRenderer;
        lastTime = System.nanoTime();
    }

    @Override
    public void render(RenderContext context) {
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;

        if (event == null) {
            event = pop();
        }

        if (deltaTime < 3 && event != null) {

            textRenderer.renderText(context.getGl(),  EventTextHandlerFactory.getHandler((Class<T>) event.getClass()).getText(event), position()[0], position()[1], textScale());
            return;
        }

        lastTime = currentTime;
        event = null;
    }

    protected abstract float[] position();

    protected float textScale() {
        return 100.0f;
    }

    public T pop() {
        return eventStateService.pop(type);
    }

    @Override
    public int priority() {
        return 300;
    }
}
