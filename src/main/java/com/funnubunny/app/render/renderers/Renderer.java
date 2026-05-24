package com.funnubunny.app.render.renderers;

import com.funnubunny.app.state.WorldStateService;

public abstract class Renderer implements Renderable {

    protected final WorldStateService worldStateService;

    public Renderer(WorldStateService worldStateService) {
        this.worldStateService = worldStateService;
    }
}
