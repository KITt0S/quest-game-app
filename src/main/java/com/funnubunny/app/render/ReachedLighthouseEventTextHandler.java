package com.funnubunny.app.render;

import com.funnubunny.app.event.events.ReachedLighthouseEvent;

public class ReachedLighthouseEventTextHandler implements EventTextHandler<ReachedLighthouseEvent> {

    @Override
    public String getText(ReachedLighthouseEvent event) {
        return "The lighthouse mechanism vibrates softly...\n" +
                "Press R to relight\n" +
                "Press F to destroy";
    }
}
