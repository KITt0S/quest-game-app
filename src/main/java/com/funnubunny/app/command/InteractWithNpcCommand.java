package com.funnubunny.app.command;

import com.funnubunny.app.command.commands.GameCommand;

public class InteractWithNpcCommand implements GameCommand {
    private final long npcId;

    public InteractWithNpcCommand(long npcId) {
        this.npcId = npcId;
    }

    public long getNpcId() {
        return npcId;
    }
}
