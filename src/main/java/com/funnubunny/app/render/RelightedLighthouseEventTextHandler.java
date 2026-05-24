package com.funnubunny.app.render;

import com.funnubunny.app.event.events.RelightedLighthouseEvent;

public class RelightedLighthouseEventTextHandler implements EventTextHandler<RelightedLighthouseEvent> {

    @Override
    public String getText(RelightedLighthouseEvent event) {
        return "Lighthouse is relighted";
    }
}
