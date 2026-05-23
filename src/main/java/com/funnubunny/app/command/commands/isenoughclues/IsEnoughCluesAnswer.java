package com.funnubunny.app.command.commands.isenoughclues;

import com.funnubunny.app.command.commands.GameAnswer;

public class IsEnoughCluesAnswer implements GameAnswer {
    private final boolean enoughClues;

    public IsEnoughCluesAnswer(boolean enoughClues) {
        this.enoughClues = enoughClues;
    }

    public boolean isEnoughClues() {
        return enoughClues;
    }
}
