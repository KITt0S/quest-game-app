package com.funnubunny.app.render.renderers;

import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.GameEvent;
import com.funnubunny.app.render.EventTextHandlerFactory;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.render.TextRenderer;

public class EventRenderer implements Renderable, EventBus.EventListener<GameEvent> {

    private final TextRenderer textRenderer;

    private long lastTime;
    private long currentTime;

    private boolean render;

    public EventRenderer(TextRenderer textRenderer, EventBus eventBus) {
        this.textRenderer = textRenderer;
        eventBus.register(GameEvent.class, this);
    }

    private String renderingText;

    @Override
    public void onEvent(GameEvent event) {
        renderingText = EventTextHandlerFactory.getHandler(event).getText(event);
        lastTime = System.nanoTime();
        render = true;
    }

    @Override
    public void render(RenderContext context) {
        currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
        if (deltaTime < 3 && render) {
            textRenderer.renderText(context.getGl(), renderingText, 300, 360);
            return;
        }

        lastTime = currentTime;
        render = false;
    }

    @Override
    public int priority() {
        return 300;
    }
}
