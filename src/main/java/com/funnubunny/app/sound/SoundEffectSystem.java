package com.funnubunny.app.sound;

import com.funnubunny.app.audio.AudioManager;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.*;

public class SoundEffectSystem {

    private final AudioManager audioManager;

    public SoundEffectSystem(AudioManager audioManager, EventBus eventBus) {
        this.audioManager = audioManager;
        eventBus.register(CollectedNoteEvent.class, this::onNoteCollected);
        eventBus.register(InteractedWithNpcEvent.class, this::onTalkedWithNpc);
    }

    private void onNoteCollected(CollectedNoteEvent event) {
        audioManager.play("note");
    }

    private  void onTalkedWithNpc(InteractedWithNpcEvent event) {
        audioManager.play("lightkeeper");
    }
}
