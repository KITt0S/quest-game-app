package com.funnubunny.app.world;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.note.Note;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class WorldState {
    private final Player player;
    private final NPC npc;
    private final List<Note> notes;
    private final Lighthouse lighthouse;
    private final IslandScene islandScene;
    private final FogSystem fogSystem;
}
