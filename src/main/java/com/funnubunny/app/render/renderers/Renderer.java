package com.funnubunny.app.render.renderers;

import com.funnubunny.app.state.GameStateService;
import com.funnubunny.app.state.WorldStateService;

public abstract class Renderer implements Renderable {
    protected final GameStateService gameStateService;
    protected final WorldStateService worldStateService;

    public Renderer(GameStateService gameStateService, WorldStateService worldStateService) {
        this.gameStateService = gameStateService;
        this.worldStateService = worldStateService;
    }
}
