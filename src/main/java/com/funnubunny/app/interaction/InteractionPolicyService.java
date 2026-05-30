package com.funnubunny.app.interaction;

import com.funnubunny.app.note.Note;
import com.funnubunny.app.state.QuestState;
import com.funnubunny.app.state.GameStateService;
import com.funnubunny.app.state.WorldStateService;

public class InteractionPolicyService {
    private final GameStateService gameStateService;
    private final WorldStateService worldStateService;

    public InteractionPolicyService(GameStateService gameStateService, WorldStateService worldStateService) {
        this.gameStateService = gameStateService;
        this.worldStateService = worldStateService;
    }

    public boolean canInteractWithNpc(long npcId) {
        if (gameStateService.getQuestState() == QuestState.NOT_STARTED) {
            return true;
        }

        if (gameStateService.getQuestState() == QuestState.TALKED_TO_KEEPER) {
            return true;
        }

        if (gameStateService.getQuestState() == QuestState.FOUND_FIRST_NOTE) {
            return true;
        }

        return gameStateService.getQuestState() == QuestState.FOUND_ALL_NOTES;
    }

    public boolean canCollectNote(long noteId) {
        if (gameStateService.getQuestState() != QuestState.TALKED_TO_KEEPER && gameStateService.getQuestState() != QuestState.FOUND_FIRST_NOTE) {
            return false;
        }

        Note note = worldStateService.getNoteById(noteId);

        return !note.isCollected();
    }

    public boolean canInteractWithGenerator() {
        if (gameStateService.getQuestState() != QuestState.FOUND_ALL_NOTES) {
            return false;
        }

        return true;
    }
}
