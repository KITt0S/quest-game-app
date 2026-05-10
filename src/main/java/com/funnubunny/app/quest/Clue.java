package com.funnubunny.app.quest;

public class Clue {

    private final String text;

    private boolean discovered;

    public Clue(String text) {
        this.text = text;
        discovered = false;
    }

    public String getText() {
        return text;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void discover() {
        discovered = true;
    }
}
