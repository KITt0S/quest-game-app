package com.funnubunny.app.render.texthandlers;

import com.funnubunny.app.event.events.DestroyedLighthouseEvent;
import com.funnubunny.app.event.events.GameEvent;

public class DestroyedLighthouseEventTextHandler implements EventTextHandler<DestroyedLighthouseEvent> {

    @Override
    public String getText(DestroyedLighthouseEvent event) {
        return """
                The final strike shattered the ancient core.
                                
                The lighthouse trembled violently,
                its beam collapsing into darkness for the last time.
                                
                The fog stopped moving.
                                
                The sea became silent.
                                
                No bells.
                No whispers.
                No distant lights.
                                
                Only the cold wind remained.
                                
                Behind you,
                the Keeper fell to his knees without a word.
                                
                As dawn slowly touched the horizon,
                you realized something terrifying:
                                
                the fog had never been trying to enter.
                                
                It had been trying to stay out.
                                
                THE LIGHTHOUSE IS DESTROYED
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
