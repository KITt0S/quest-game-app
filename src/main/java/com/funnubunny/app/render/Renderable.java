package com.funnubunny.app.render;

public interface Renderable {

    public void render(RenderContext context);

    public int priority();
}
