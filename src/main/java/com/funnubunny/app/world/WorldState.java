package com.funnubunny.app.world;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.quest.NoteSystem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorldState {
    private final Player player;
    private final NPC npc;
    private final Lighthouse lighthouse;
    private final IslandScene islandScene;
    private final FogSystem fogSystem;
    private final NoteSystem noteSystem;
}
