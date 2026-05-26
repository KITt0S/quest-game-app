package com.funnubunny.app.note;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.GameAnswer;
import com.funnubunny.app.command.commands.isenoughclues.IsEnoughCluesAnswer;
import com.funnubunny.app.command.commands.isenoughclues.IsEnoughCluesCommand;
import com.funnubunny.app.command.commands.VoidAnswer;
import com.funnubunny.app.command.commands.collectnote.CollectNoteCommand;
import com.funnubunny.app.state.WorldStateService;

public class NoteSystem {
    private final WorldStateService worldStateService;

    public NoteSystem(CommandBus commandBus, WorldStateService worldStateService) {
        this.worldStateService = worldStateService;
        commandBus.register(CollectNoteCommand.class, this::collectNote);
        commandBus.register(IsEnoughCluesCommand.class, this::isEnoughClues);
    }

    private GameAnswer collectNote(CollectNoteCommand command) {
        long noteId = command.getId();

        Note note = worldStateService.getNoteById(noteId);

        if (note.isCollected()) {
            return new VoidAnswer();
        }

        note.collect();

        System.out.println("\n--- FOUND NOTE ---");
        System.out.println(note.getText());
        System.out.println("------------------\n");

        return new VoidAnswer();
    }

    private IsEnoughCluesAnswer isEnoughClues(IsEnoughCluesCommand command) {
        boolean isEnoughClues = worldStateService.getNotes().stream().filter(Note::isCollected).count() >= 3;
        return new IsEnoughCluesAnswer(isEnoughClues);
    }

}
