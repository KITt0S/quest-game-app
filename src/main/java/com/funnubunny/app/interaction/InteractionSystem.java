package com.funnubunny.app.interaction;

import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.command.InteractWithNpcCommand;
import com.funnubunny.app.command.commands.GameAnswer;
import com.funnubunny.app.command.commands.VoidAnswer;
import com.funnubunny.app.command.commands.collectnote.CollectNoteCommand;
import com.funnubunny.app.command.commands.destroylighthouse.DestroyLighthouseCommand;
import com.funnubunny.app.command.commands.fixgenerator.RestoreGeneratorCommand;
import com.funnubunny.app.command.commands.interaction.ChoiceInteractionCommand;
import com.funnubunny.app.command.commands.interaction.InteractionCommand;
import com.funnubunny.app.command.commands.relightlighthouse.RelightLighthouseCommand;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.*;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.state.GameStateService;
import com.funnubunny.app.state.QuestState;
import com.funnubunny.app.state.WorldStateService;
import com.funnubunny.app.world.WorldExplorationService;

import java.util.Optional;

public class InteractionSystem {
    private final GameStateService gameStateService;
    private final WorldStateService worldStateService;
    private final InteractionPolicyService policyService;
    private final WorldExplorationService explorationService;
    private final CommandBus commandBus;
    private final EventBus eventBus;

    private static final float INTERACTION_DISTANCE = 80f;

    public InteractionSystem(
            GameStateService gameStateService,
            WorldStateService worldStateService,
            InteractionPolicyService interactionPolicyService,
            WorldExplorationService explorationService,
            CommandBus commandBus,
            EventBus eventBus) {
        this.gameStateService = gameStateService;
        this.worldStateService = worldStateService;
        this.policyService = interactionPolicyService;
        this.explorationService = explorationService;
        this.eventBus = eventBus;
        this.commandBus = commandBus;
        commandBus.register(InteractionCommand.class, this::handleInteraction);
        commandBus.register(ChoiceInteractionCommand.class, this::handleChoiceInteraction);
    }

    public GameAnswer handleInteraction(InteractionCommand command) {
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

    private VoidAnswer handleChoiceInteraction(ChoiceInteractionCommand command) {
        handleInteractLighthouse(command.getOption());

        return new VoidAnswer();
    }

    private void handleInteractLighthouse(ChoiceInteractionCommand.Choice choice) {
        switch (choice) {
            case FIRST -> handleRelightLighthouse();
            case SECOND -> handleDestroyLighthouse();
        }
    }


    private void handleRelightLighthouse() {
        if (gameStateService.getQuestState() == QuestState.REACHED_LIGHTHOUSE) {
            commandBus.dispatch(new RelightLighthouseCommand());
            eventBus.emit(new RelightedLighthouseEvent());
        }
    }

    private void handleDestroyLighthouse() {
        if (gameStateService.getQuestState() == QuestState.REACHED_LIGHTHOUSE) {
            commandBus.dispatch(new DestroyLighthouseCommand());
            eventBus.emit(new DestroyedLighthouseEvent());
        }
    }
}
