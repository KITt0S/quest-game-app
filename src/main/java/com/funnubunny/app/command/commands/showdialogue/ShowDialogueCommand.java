package com.funnubunny.app.command.commands.showdialogue;

import com.funnubunny.app.command.commands.GameCommand;
import com.funnubunny.app.quest.Dialogue;

public class ShowDialogueCommand implements GameCommand {
    private final Dialogue dialogue;

    public ShowDialogueCommand(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }
}
