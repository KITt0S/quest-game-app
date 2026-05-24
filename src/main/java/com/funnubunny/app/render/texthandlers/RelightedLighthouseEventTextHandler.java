package com.funnubunny.app.render.texthandlers;

import com.funnubunny.app.event.events.RelightedLighthouseEvent;

public class RelightedLighthouseEventTextHandler implements EventTextHandler<RelightedLighthouseEvent> {

    @Override
    public String getText(RelightedLighthouseEvent event) {
        return """
                The mechanism roared back to life.
                                
                Light pierced the fog once more,
                spilling across the black sea like a wound reopening.
                                
                Far away, something answered.
                                
                The Keeper stood silently behind you,
                his face hidden beneath the trembling glow.
                                
                For a moment,
                the island felt safe again.
                                
                Then the bells began to ring beneath the water.
                                
                THE LIGHTHOUSE IS RELIT
                """;
    }

    @Override
    public float[] position() {
        return new float[]{350, 650};
    }

    @Override
    public float scale() {
        return 65.0f;
    }

    @Override
    public float duration() {
        return 10.0f;
    }
}
