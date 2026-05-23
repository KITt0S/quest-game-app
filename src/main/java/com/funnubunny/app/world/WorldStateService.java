package com.funnubunny.app.world;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.quest.NoteSystem;

public class WorldStateService {

    private final WorldState worldState;

    public WorldStateService(WorldState worldState) {
        this.worldState = worldState;
    }

    public Player getPlayer() {
        return worldState.getPlayer();
    }

    public NPC getNpc() {
        return worldState.getNpc();
    }

    public Lighthouse getLighthouse() {
        return worldState.getLighthouse();
    }

    public NoteSystem getNoteSystem() {
        return worldState.getNoteSystem();
    }
}
