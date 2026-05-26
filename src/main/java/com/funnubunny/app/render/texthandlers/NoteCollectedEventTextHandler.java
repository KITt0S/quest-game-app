package com.funnubunny.app.render.texthandlers;

import com.funnubunny.app.event.events.CollectedNoteEvent;
import com.funnubunny.app.state.WorldStateService;

public class NoteCollectedEventTextHandler implements EventTextHandler<CollectedNoteEvent> {

    @Override
    public String getText(CollectedNoteEvent event) {
        return event.getText();
    }
}
