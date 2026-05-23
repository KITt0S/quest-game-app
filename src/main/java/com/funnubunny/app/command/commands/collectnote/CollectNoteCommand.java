package com.funnubunny.app.command.commands.collectnote;

import com.funnubunny.app.command.commands.GameCommand;

public class CollectNoteCommand implements GameCommand {
    private final String id;

    public CollectNoteCommand(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
