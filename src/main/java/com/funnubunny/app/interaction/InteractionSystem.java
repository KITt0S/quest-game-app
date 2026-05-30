package com.funnubunny.app.interaction;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.*;
import com.funnubunny.app.command.commands.collectnote.CollectNoteCommand;
import com.funnubunny.app.command.commands.destroylighthouse.DestroyLighthouseCommand;
import com.funnubunny.app.command.commands.fixgenerator.RestoreGeneratorCommand;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateAnswer;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateCommand;
import com.funnubunny.app.command.commands.interaction.InteractionCommand;
import com.funnubunny.app.command.commands.relightlighthouse.RelightLighthouseCommand;
import com.funnubunny.app.event.*;
import com.funnubunny.app.event.events.*;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.state.QuestState;
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
        commandBus.register(RelightLighthouseCommand.class, this::handleRelightLighthouse);
        commandBus.register(DestroyLighthouseCommand.class, this::handleDestroyLighthouse);
    }

    public GameAnswer handle(InteractionCommand command) {
        handleNpcInteraction();
        handleNoteCollection();
        handleRestoreGenerator();
        return new VoidAnswer();
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

    private void handleRestoreGenerator() {
        if (!policyService.canInteractWithGenerator()) {
            return;
        }

        if (!explorationService.isNearOfGenerator()){
            return;
        }

        commandBus.dispatch(new RestoreGeneratorCommand());
        eventBus.emit(new RestoredGeneratorEvent());
    }

    private GameAnswer handleRelightLighthouse(RelightLighthouseCommand command) {
        GetQuestStateAnswer getQuestStateAnswer = commandBus.dispatch(new GetQuestStateCommand());
        if (getQuestStateAnswer.getQuestState() == QuestState.REACHED_LIGHTHOUSE) {
            commandBus.dispatch(new RelightLighthouseCommand());
            eventBus.emit(new RelightedLighthouseEvent());
        }

        return new VoidAnswer();
    }

    private GameAnswer handleDestroyLighthouse(DestroyLighthouseCommand command) {
        GetQuestStateAnswer getQuestStateAnswer = commandBus.dispatch(new GetQuestStateCommand());
        if (getQuestStateAnswer.getQuestState() == QuestState.REACHED_LIGHTHOUSE) {
            System.out.println("Lighthouse is destroyed");
            eventBus.emit(new DestroyedLighthouseEvent());
        }

        return new VoidAnswer();
    }

}
