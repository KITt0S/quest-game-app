package com.funnubunny.app.state;

import com.funnubunny.app.entity.Generator;
import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.world.IslandScene;
import com.funnubunny.app.entity.Lighthouse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
public class WorldState {
    private final Player player;
    private final NPC npc;
    private final List<Note> notes;
    private final Generator generator;
    private final Lighthouse lighthouse;
    private final IslandScene islandScene;
    private boolean bellRinging = true;
}
