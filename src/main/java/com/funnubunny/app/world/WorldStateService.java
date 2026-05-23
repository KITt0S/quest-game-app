package com.funnubunny.app.world;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.note.Note;

import java.util.List;

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

    public List<Note> getNotes() {
        return worldState.getNotes();
    }

    public IslandScene getIslandScene() {
        return worldState.getIslandScene();
    }

    public FogSystem getFogSystem() {
        return worldState.getFogSystem();
    }

    public Note getNoteById(String id) {
        return worldState.getNotes().stream().filter(note -> note.getId().equals(id)).findFirst().orElse(null);
    }
}
