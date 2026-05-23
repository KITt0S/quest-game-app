package com.funnubunny.app.render;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderingSystem {

    private final List<Renderer> renderers;

    public RenderingSystem() {
        renderers = new ArrayList<>();
    }

    public void register(Renderer renderer) {
        renderers.add(renderer);
    }

    public void render(RenderContext context) {
        renderers
                .stream()
                .sorted(Comparator.comparingInt(Renderable::priority))
                .forEach(renderer -> renderer.render(context));
    }
}
