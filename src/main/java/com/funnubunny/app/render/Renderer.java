package com.funnubunny.app.render;

import com.funnubunny.app.world.WorldStateService;

public abstract class Renderer implements Renderable {

    protected final WorldStateService worldStateService;

    public Renderer(WorldStateService worldStateService) {
        this.worldStateService = worldStateService;
    }
}
