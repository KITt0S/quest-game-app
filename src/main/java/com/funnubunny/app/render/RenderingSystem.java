package com.funnubunny.app.render;

import com.funnubunny.app.render.renderers.Renderable;
import com.jogamp.opengl.GL3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RenderingSystem {

    private final List<Renderable> renderers;

    public RenderingSystem() {
        renderers = new ArrayList<>();
    }

    public void register(Renderable renderer) {
        renderers.add(renderer);
    }

    public void render(RenderContext context) {
        context.getGl().glClear(GL3.GL_COLOR_BUFFER_BIT);
        renderers
                .stream()
                .sorted(Comparator.comparingInt(Renderable::priority))
                .forEach(renderer -> renderer.render(context));
    }
}
