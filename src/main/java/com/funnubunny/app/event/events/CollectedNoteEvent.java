package com.funnubunny.app.event.events;

public class CollectedNoteEvent implements InteractionEvent {
    private final long noteId;
    private final String text;

    public CollectedNoteEvent(long noteId, String text) {
        this.noteId = noteId;
        this.text = text;
    }

    public long getNoteId() {
        return noteId;
    }

    public String getText() {
        return text;
    }
}
