package com.funnubunny.app.render.renderers;

import com.funnubunny.app.render.RenderContext;

public interface Renderable {

    public void render(RenderContext context);

    public int priority();
}
