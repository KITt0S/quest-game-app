package com.funnubunny.app.command.commands.collectnote;

import com.funnubunny.app.command.commands.GameCommand;

public class CollectNoteCommand implements GameCommand {
    private final long id;

    public CollectNoteCommand(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
