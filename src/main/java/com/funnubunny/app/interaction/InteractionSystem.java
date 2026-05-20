package com.funnubunny.app.interaction;

import com.funnubunny.app.command.*;
import com.funnubunny.app.core.Input;
import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.event.*;
import com.funnubunny.app.quest.Note;
import com.funnubunny.app.quest.NoteManager;
import com.funnubunny.app.quest.QuestState;
import com.funnubunny.app.ui.DialogueBox;
import com.funnubunny.app.world.Lighthouse;
import com.jogamp.newt.event.KeyEvent;

import java.util.List;

public class InteractionSystem {

    private final Player player;
    private final NPC npc;
    private final DialogueBox dialogueBox;
    private final List<Note> notes;
    private final NoteManager noteManager;
    private final Lighthouse lighthouse;
    private final CommandBus commandBus;
    private final EventBus eventBus;

    private static final float INTERACTION_DISTANCE = 80f;

    public InteractionSystem(
            Player player,
            NPC npc,
            DialogueBox dialogueBox,
            List<Note> notes,
            NoteManager noteManager,
            Lighthouse lighthouse,
            CommandBus commandBus,
            EventBus eventBus
    ) {

        this.player = player;
        this.npc = npc;
        this.dialogueBox = dialogueBox;
        this.notes = notes;
        this.noteManager = noteManager;
        this.lighthouse = lighthouse;
        this.eventBus = eventBus;
        this.commandBus = commandBus;
        commandBus.register(InteractionCommand.class, this::handle);
        commandBus.register(RelightLighthouseCommand.class, this::relightLighthouse);
        commandBus.register(DestroyLighthouseCommand.class, this::destroyLighthouse);
    }

    public void update() {
        handleDialogue();
        handleReachLighthouseInteraction();
    }

    private void handleNpcInteraction() {
        if (((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState() != QuestState.NOT_STARTED) {
            return;
        }

        float distance = player.getPosition().distance(npc.getPosition());

        boolean canInteract = distance < INTERACTION_DISTANCE;

        if (canInteract && Input.isKeyPressed(KeyEvent.VK_E)) {

            if (!dialogueBox.isActive()) {

                dialogueBox.show(npc.getDialogue());

                eventBus.emit(new TalkedToNpcEvent());
            }
        }
    }

    private void handleDialogue() {

        if (dialogueBox.isActive() &&
                Input.isKeyPressed(KeyEvent.VK_SPACE)) {

            dialogueBox.next();
        }
    }

    private void handleNoteInteraction() {
        if (((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState() != QuestState.TALKED_TO_KEEPER) {
            return;
        }

        for (Note note : notes) {

            if (note.isCollected()) {
                continue;
            }

            float distance = player.getPosition().distance(note.getPosition());

            if (distance < INTERACTION_DISTANCE && Input.isKeyPressed(KeyEvent.VK_E)) {

                note.interact();

                eventBus.emit(new FirstNoteCollectedGameEvent());

                if (noteManager.hasEnoughClues()) {
                    eventBus.emit(new AllNotesFoundGameEvent());
                }
            }
        }
    }

    private void handleReachLighthouseInteraction() {
        if (((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState() != QuestState.FOUND_ALL_NOTES) {
            return;
        }

        float distance = player.getPosition().distance(lighthouse.getPosition());

        boolean nearLighthouse = distance < INTERACTION_DISTANCE;

        if (!nearLighthouse) {
            return;
        }

        System.out.println();
        System.out.println("The lighthouse mechanism vibrates softly...");
        System.out.println("Press R to relight");
        System.out.println("Press F to destroy");

        eventBus.emit(new ReachedLighthouseEvent());
    }

    private void handleRelightLighthouse() {
        if (((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState() != QuestState.REACHED_LIGHTHOUSE) {
            return;
        }

        System.out.println("Lighthouse is relighted");

        eventBus.emit(new RelightedLighthouseEvent());
    }

    private void handleDestroyLighthouse() {
        if (((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState() != QuestState.REACHED_LIGHTHOUSE) {
            return;
        }

        System.out.println("Lighthouse is destroyed");

        eventBus.emit(new DestroyedLighthouseEvent());
    }

    public GameAnswer handle(InteractionCommand command) {
        handleNpcInteraction();
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
