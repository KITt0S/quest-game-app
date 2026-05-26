package com.funnubunny.app.interaction;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.*;
import com.funnubunny.app.command.commands.collectnote.CollectNoteCommand;
import com.funnubunny.app.command.commands.destroylighthouse.DestroyLighthouseCommand;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateAnswer;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateCommand;
import com.funnubunny.app.command.commands.interaction.InteractionCommand;
import com.funnubunny.app.command.commands.relightlighthouse.RelightLighthouseCommand;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.event.*;
import com.funnubunny.app.event.events.*;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.state.QuestState;
import com.funnubunny.app.world.Lighthouse;
import com.funnubunny.app.state.WorldStateService;
import com.funnubunny.app.world.WorldExplorationService;

import java.util.Optional;

public class InteractionSystem {
    private final CommandBus commandBus;
    private final EventBus eventBus;
    private final WorldStateService worldStateService;
    private final InteractionPolicyService policyService;
    private final WorldExplorationService explorationService;

    private static final float INTERACTION_DISTANCE = 80f;

    public InteractionSystem(
            CommandBus commandBus,
            EventBus eventBus,
            WorldStateService worldStateService,
            InteractionPolicyService interactionPolicyService,
            WorldExplorationService explorationService) {
        this.eventBus = eventBus;
        this.commandBus = commandBus;
        this.worldStateService = worldStateService;
        this.policyService = interactionPolicyService;
        this.explorationService = explorationService;
        commandBus.register(InteractionCommand.class, this::handle);
        commandBus.register(RelightLighthouseCommand.class, this::relightLighthouse);
        commandBus.register(DestroyLighthouseCommand.class, this::destroyLighthouse);
    }

    private void handleNpcInteraction() {
        Optional<Long> optionalNpcId = explorationService.getNearestNpcId();

        if (optionalNpcId.isEmpty()) {
            return;
        }

        long npcId = optionalNpcId.get();

        if (policyService.canInteractWithNpc(npcId)) {
            commandBus.dispatch(new InteractWithNpcCommand(npcId));
            eventBus.emit(new InteractedWithNpcEvent(npcId));
        }
    }

    private void handleNoteCollection() {
        Optional<Long> optionalNoteId = explorationService.getNearestNoteId();

        if (optionalNoteId.isEmpty()) {
            return;
        }

        long noteId = optionalNoteId.get();

        if (policyService.canCollectNote(noteId)) {
            commandBus.dispatch(new CollectNoteCommand(noteId));
            Note note = worldStateService.getNoteById(noteId);
            eventBus.emit(new CollectedNoteEvent(noteId, note.getText()));
        }
    }

    private void handleRelightLighthouse() {
        GetQuestStateAnswer getQuestStateAnswer = commandBus.dispatch(new GetQuestStateCommand());
        if (getQuestStateAnswer.getQuestState() != QuestState.REACHED_LIGHTHOUSE) {
            return;
        }

        eventBus.emit(new RelightedLighthouseEvent());
    }

    private void handleDestroyLighthouse() {
        GetQuestStateAnswer getQuestStateAnswer = commandBus.dispatch(new GetQuestStateCommand());
        if (getQuestStateAnswer.getQuestState() != QuestState.REACHED_LIGHTHOUSE) {
            return;
        }

        System.out.println("Lighthouse is destroyed");

        eventBus.emit(new DestroyedLighthouseEvent());
    }

    public GameAnswer handle(InteractionCommand command) {
        handleNpcInteraction();
        handleNoteCollection();
        return new VoidAnswer();
    }

    private GameAnswer relightLighthouse(RelightLighthouseCommand command) {
        handleRelightLighthouse();
        return new VoidAnswer();
    }

    private GameAnswer destroyLighthouse(DestroyLighthouseCommand command) {
        handleDestroyLighthouse();
        return new VoidAnswer();
    }
}
