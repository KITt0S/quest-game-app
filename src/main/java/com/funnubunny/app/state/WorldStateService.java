package com.funnubunny.app.state;

import com.funnubunny.app.entity.Generator;
import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.world.IslandScene;
import com.funnubunny.app.entity.Lighthouse;

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

    public Generator getGenerator() {
        return worldState.getGenerator();
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

    public Note getNoteById(long id) {
        return worldState.getNotes().stream().filter(note -> note.getId() == id).findFirst().orElse(null);
    }

    public boolean isBellRinging() {
        return worldState.isBellRinging();
    }

    public void setBellRinging(boolean bellRinging) {
        worldState.setBellRinging(bellRinging);
    }
}
