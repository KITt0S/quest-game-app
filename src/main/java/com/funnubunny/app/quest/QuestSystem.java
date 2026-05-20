package com.funnubunny.app.quest;

import com.funnubunny.app.command.ChangeQuestStateCommand;
import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.event.*;

public class QuestSystem {

    private final CommandBus commandBus;

    public QuestSystem(CommandBus commandBus, EventBus eventBus) {
        this.commandBus = commandBus;
        eventBus.register(TalkedToNpcEvent.class, this::onTalkedWithNpc);
        eventBus.register(FirstNoteCollectedGameEvent.class, this::onFirstNoteCollected);
        eventBus.register(AllNotesFoundGameEvent.class, this::onAllNotesFound);
        eventBus.register(ReachedLighthouseEvent.class, this::onReachedLighthouse);
        eventBus.register(RelightedLighthouseEvent.class, this::onRelightedLighthouse);
        eventBus.register(DestroyedLighthouseEvent.class, this::onDestroyedLightouse);
    }

    public void onTalkedWithNpc(TalkedToNpcEvent event) {
        commandBus.dispatch(new ChangeQuestStateCommand(QuestState.TALKED_TO_KEEPER));
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
