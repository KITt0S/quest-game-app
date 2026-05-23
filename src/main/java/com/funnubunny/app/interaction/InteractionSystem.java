package com.funnubunny.app.interaction;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.*;
import com.funnubunny.app.command.commands.collectnote.CollectNoteCommand;
import com.funnubunny.app.command.commands.destroylighthouse.DestroyLighthouseCommand;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateAnswer;
import com.funnubunny.app.command.commands.getqueststate.GetQuestStateCommand;
import com.funnubunny.app.command.commands.interaction.InteractionCommand;
import com.funnubunny.app.command.commands.isactivedialoguebox.IsActiveDialogueBoxAnswer;
import com.funnubunny.app.command.commands.isactivedialoguebox.IsActiveDialogueBoxCommand;
import com.funnubunny.app.command.commands.isenoughclues.IsEnoughCluesAnswer;
import com.funnubunny.app.command.commands.isenoughclues.IsEnoughCluesCommand;
import com.funnubunny.app.command.commands.relightlighthouse.RelightLighthouseCommand;
import com.funnubunny.app.command.commands.showdialogue.ShowDialogueCommand;
import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.event.*;
import com.funnubunny.app.event.events.*;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.quest.QuestState;
import com.funnubunny.app.world.Lighthouse;
import com.funnubunny.app.world.WorldStateService;

public class InteractionSystem {
    private final CommandBus commandBus;
    private final EventBus eventBus;
    private final WorldStateService worldStateService;

    private static final float INTERACTION_DISTANCE = 80f;

    public InteractionSystem(
            CommandBus commandBus,
            EventBus eventBus,
            WorldStateService worldStateService) {
        this.eventBus = eventBus;
        this.commandBus = commandBus;
        this.worldStateService = worldStateService;
        commandBus.register(InteractionCommand.class, this::handle);
        commandBus.register(RelightLighthouseCommand.class, this::relightLighthouse);
        commandBus.register(DestroyLighthouseCommand.class, this::destroyLighthouse);
    }

    public void update() {
        handleReachLighthouseInteraction();
    }

    private void handleNpcInteraction() {
        if (((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState() != QuestState.NOT_STARTED) {
            return;
        }

        Player player = worldStateService.getPlayer();
        NPC npc = worldStateService.getNpc();

        float distance = player.getPosition().distance(npc.getPosition());

        boolean canInteract = distance < INTERACTION_DISTANCE;

        if (canInteract) {

            IsActiveDialogueBoxAnswer isActiveDialogueBoxAnswer = commandBus.dispatch(new IsActiveDialogueBoxCommand());
            if (!isActiveDialogueBoxAnswer.isActive()) {

                commandBus.dispatch(new ShowDialogueCommand(npc.getDialogue()));

                eventBus.emit(new TalkedToNpcEvent());
            }
        }
    }

    private void handleFirstNoteInteraction() {
        if (((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState() != QuestState.TALKED_TO_KEEPER) {
            return;
        }

        Player player = worldStateService.getPlayer();

        for (Note note : worldStateService.getNotes()) {
            float distance = player.getPosition().distance(note.getPosition());

            if (distance < INTERACTION_DISTANCE) {
                commandBus.dispatch(new CollectNoteCommand(note.getId()));
                eventBus.emit(new FirstNoteCollectedGameEvent());
                eventBus.emit(new NoteCollectedEvent(note.getText()));
            }
        }
    }

    private void handleNoteInteraction() {
        QuestState questState = ((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState();
        if (questState != QuestState.TALKED_TO_KEEPER && questState != QuestState.FOUND_FIRST_NOTE) {
            return;
        }

        Player player = worldStateService.getPlayer();

        for (Note note : worldStateService.getNotes()) {

            if (note.isCollected()) {
                continue;
            }

            float distance = player.getPosition().distance(note.getPosition());

            if (distance < INTERACTION_DISTANCE) {

                commandBus.dispatch(new CollectNoteCommand(note.getId()));
                eventBus.emit(new NoteCollectedEvent(note.getText()));

                IsEnoughCluesAnswer isEnoughCluesAnswer = commandBus.dispatch(new IsEnoughCluesCommand());

                if (isEnoughCluesAnswer.isEnoughClues()) {
                    eventBus.emit(new AllNotesFoundGameEvent());
                }
            }
        }
    }

    private void handleReachLighthouseInteraction() {
        GetQuestStateAnswer getQuestStateAnswer = commandBus.dispatch(new GetQuestStateCommand());
        if (getQuestStateAnswer.getQuestState() != QuestState.FOUND_ALL_NOTES) {
            return;
        }

        Player player = worldStateService.getPlayer();
        Lighthouse lighthouse = worldStateService.getLighthouse();

        float distance = player.getPosition().distance(lighthouse.getPosition());

        boolean nearLighthouse = distance < INTERACTION_DISTANCE;

        if (!nearLighthouse) {
            return;
        }

        eventBus.emit(new ReachedLighthouseEvent());
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
        handleFirstNoteInteraction();
        handleNoteInteraction();
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
