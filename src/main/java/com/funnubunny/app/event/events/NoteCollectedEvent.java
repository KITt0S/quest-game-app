package com.funnubunny.app.event.events;

import com.funnubunny.app.event.GameEvent;

public class NoteCollectedEvent implements GameEvent {
    private final String noteText;

    public NoteCollectedEvent(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteText() {
        return noteText;
    }
}
