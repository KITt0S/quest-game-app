package com.funnubunny.app.render.texthandlers;

import com.funnubunny.app.event.events.NoteCollectedEvent;

public class NoteCollectedEventTextHandler implements EventTextHandler<NoteCollectedEvent> {

    @Override
    public String getText(NoteCollectedEvent event) {
        return event.getNoteText();
    }
}
