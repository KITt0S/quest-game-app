package com.funnubunny.app.quest;

import com.funnubunny.app.command.commands.changequeststate.ChangeQuestStateCommand;
import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.event.*;
import com.funnubunny.app.event.events.*;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.state.QuestState;
import com.funnubunny.app.state.WorldStateService;

public class QuestSystem {
    private final WorldStateService worldStateService;
    private final CommandBus commandBus;

    public QuestSystem(CommandBus commandBus, WorldStateService worldStateService, EventBus eventBus) {
        this.worldStateService = worldStateService;
        this.commandBus = commandBus;
        eventBus.register(InteractedWithNpcEvent.class, this::onInteractedWithNpc);
        eventBus.register(CollectedNoteEvent.class, this::onCollectedNote);
        eventBus.register(FirstNoteCollectedGameEvent.class, this::onFirstNoteCollected);
        eventBus.register(AllNotesFoundGameEvent.class, this::onAllNotesFound);
        eventBus.register(ReachedLighthouseEvent.class, this::onReachedLighthouse);
        eventBus.register(RelightedLighthouseEvent.class, this::onRelightedLighthouse);
        eventBus.register(DestroyedLighthouseEvent.class, this::onDestroyedLightouse);
    }

    private void onCollectedNote(CollectedNoteEvent event) {
        boolean firstNoteCollected = worldStateService.getNotes().stream().filter(Note::isCollected).count() == 1;

        if (firstNoteCollected) {
            commandBus.dispatch(new ChangeQuestStateCommand(QuestState.FOUND_FIRST_NOTE));
            return;
        }

        boolean allNotesCollected = worldStateService.getNotes().stream().allMatch(Note::isCollected);

        if (allNotesCollected) {
            commandBus.dispatch(new ChangeQuestStateCommand(QuestState.FOUND_ALL_NOTES));
        }
    }

    public void onInteractedWithNpc(InteractedWithNpcEvent event) {
        if (event.getNpcId() == worldStateService.getNpc().getId()) {
            commandBus.dispatch(new ChangeQuestStateCommand(QuestState.TALKED_TO_KEEPER));
        }
    }

    public void onFirstNoteCollected(FirstNoteCollectedGameEvent event) {
        commandBus.dispatch(new ChangeQuestStateCommand(QuestState.FOUND_FIRST_NOTE));
    }

    public void onAllNotesFound(AllNotesFoundGameEvent event) {
        commandBus.dispatch(new ChangeQuestStateCommand(QuestState.FOUND_ALL_NOTES));
    }

    public void onReachedLighthouse(ReachedLighthouseEvent event) {
        commandBus.dispatch(new ChangeQuestStateCommand(QuestState.REACHED_LIGHTHOUSE));
    }

    private void onRelightedLighthouse(RelightedLighthouseEvent event) {
        commandBus.dispatch(new ChangeQuestStateCommand(QuestState.RELIGHT_ENDING));
    }

    private void onDestroyedLightouse(DestroyedLighthouseEvent event) {
        commandBus.dispatch(new ChangeQuestStateCommand(QuestState.DESTROY_ENDING));
    }
}
