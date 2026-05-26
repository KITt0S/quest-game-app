package com.funnubunny.app.event.events;

public class InteractedWithNpcEvent implements InteractionEvent {
    private final long npcId;

    public InteractedWithNpcEvent(long npcId) {
        this.npcId = npcId;
    }

    public long getNpcId() {
        return npcId;
    }
}
