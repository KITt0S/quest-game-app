package com.funnubunny.app.world;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.state.WorldStateService;

import java.util.List;
import java.util.Optional;

public class WorldExplorationService {
    private final WorldStateService worldStateService;

    public WorldExplorationService(WorldStateService worldStateService) {
        this.worldStateService = worldStateService;
    }

    public Optional<Long> getNearestNpcId() {
        Player player = worldStateService.getPlayer();
        NPC npc = worldStateService.getNpc();

        float distance = player.getPosition().distance(npc.getPosition());

        boolean canInteract = distance < WorldExplorationConstants.INTERACTION_DISTANCE;

        if (!canInteract) {
            return Optional.empty();
        }

        return Optional.of(worldStateService.getNpc().getId());
    }

    public Optional<Long> getNearestNoteId() {
        Player player = worldStateService.getPlayer();
        List<Note> notes = worldStateService.getNotes();

        return notes
                .stream()
                .filter(note -> {
                    float distance = player.getPosition().distance(note.getPosition());

                    boolean canInteract = distance < WorldExplorationConstants.INTERACTION_DISTANCE;

                    if (canInteract) {
                        return true;
                    }

                    return false;
                })
                .map(Note::getId)
                .findFirst();
    }
}
