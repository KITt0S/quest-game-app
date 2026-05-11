package com.funnubunny.app.interaction;

import com.funnubunny.app.core.Input;
import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.GameEvent;
import com.funnubunny.app.quest.Note;
import com.funnubunny.app.quest.NoteManager;
import com.funnubunny.app.quest.QuestManager;
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
    private final QuestManager questManager;
    private final Lighthouse lighthouse;
    private final EventBus eventBus;

    private static final float INTERACTION_DISTANCE = 80f;

    public InteractionSystem(
            Player player,
            NPC npc,
            DialogueBox dialogueBox,
            List<Note> notes,
            NoteManager noteManager,
            QuestManager questManager,
            Lighthouse lighthouse,
            EventBus eventBus
    ) {

        this.player = player;
        this.npc = npc;
        this.dialogueBox = dialogueBox;
        this.notes = notes;
        this.noteManager = noteManager;
        this.questManager = questManager;
        this.lighthouse = lighthouse;
        this.eventBus = eventBus;
    }

    public void update() {

        handleNpcInteraction();

        handleDialogue();

        handleNoteInteraction();

        handleLighthouseInteraction();
    }

    private void handleNpcInteraction() {

        float distance = player.getPosition().distance(npc.getPosition());

        boolean canInteract = distance < INTERACTION_DISTANCE;

        if (canInteract && Input.isKeyPressed(KeyEvent.VK_E)) {

            if (!dialogueBox.isActive()) {

                dialogueBox.show(npc.getDialogue());

                eventBus.emit(GameEvent.TALKED_TO_KEEPER);
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

        for (Note note : notes) {

            if (note.isCollected()) {
                continue;
            }

            float distance = player.getPosition().distance(note.getPosition());

            if (distance < INTERACTION_DISTANCE && Input.isKeyPressed(KeyEvent.VK_E)) {

                note.interact();

                eventBus.emit(GameEvent.NOTE_COLLECTED);

                if (noteManager.hasEnoughClues()) {
                    eventBus.emit(GameEvent.ALL_NOTES_FOUND);
                }
            }
        }
    }

    private void handleLighthouseInteraction() {

        float distance = player.getPosition().distance(lighthouse.getPosition());

        boolean nearLighthouse = distance < INTERACTION_DISTANCE;

        boolean questReady = questManager.getState() == QuestState.FOUND_ALL_NOTES;

        if (!nearLighthouse || !questReady) {
            return;
        }

        eventBus.emit(GameEvent.REACHED_LIGHTHOUSE);

        System.out.println();
        System.out.println("The lighthouse mechanism vibrates softly...");
        System.out.println("Press R to relight");
        System.out.println("Press F to destroy");

        if (Input.isKeyPressed(KeyEvent.VK_R)) {
            eventBus.emit(GameEvent.CHOSE_RELIGHT);
        }

        if (Input.isKeyPressed(KeyEvent.VK_F)) {
            eventBus.emit(GameEvent.CHOSE_DESTROY);
        }
    }
}
