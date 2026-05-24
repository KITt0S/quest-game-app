package com.funnubunny.app.event.events;

public class NoteCollectedEvent implements InteractionEvent {
    private final String noteText;

    public NoteCollectedEvent(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return noteText;
    }
}
