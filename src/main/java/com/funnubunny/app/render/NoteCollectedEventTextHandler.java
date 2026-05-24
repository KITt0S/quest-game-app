package com.funnubunny.app.render;

import com.funnubunny.app.event.events.GameEvent;
import com.funnubunny.app.event.events.NoteCollectedEvent;

public class NoteCollectedEventTextHandler implements EventTextHandler<NoteCollectedEvent> {

    @Override
    public String getText(NoteCollectedEvent event) {
        return event.getNoteText();
    }
}
