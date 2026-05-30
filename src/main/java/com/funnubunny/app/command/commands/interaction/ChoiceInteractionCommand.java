package com.funnubunny.app.command.commands.interaction;

import com.funnubunny.app.command.commands.GameCommand;

public class ChoiceInteractionCommand implements GameCommand {
    private final Choice choice;

    public ChoiceInteractionCommand(Choice choice) {
        this.choice = choice;
    }

    public Choice getOption() {
        return choice;
    }

    public enum Choice {
        FIRST,
        SECOND
    }
}
