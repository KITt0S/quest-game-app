package com.funnubunny.app.command.commands.isactivedialoguebox;

import com.funnubunny.app.command.commands.GameAnswer;

public class IsActiveDialogueBoxAnswer implements GameAnswer {
    private final boolean isActive;

    public IsActiveDialogueBoxAnswer(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }
}
