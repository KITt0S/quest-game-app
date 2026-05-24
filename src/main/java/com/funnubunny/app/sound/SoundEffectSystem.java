package com.funnubunny.app.sound;

import com.funnubunny.app.audio.AudioManager;
import com.funnubunny.app.core.Time;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.*;

public class SoundEffectSystem {

    private final AudioManager audioManager;

    public SoundEffectSystem(AudioManager audioManager, EventBus eventBus) {
        this.audioManager = audioManager;
        eventBus.register(NoteCollectedEvent.class, this::onNoteCollected);
        eventBus.register(TalkedToNpcEvent.class, this::onTalkedWithNpc);
    }

    private void onNoteCollected(NoteCollectedEvent event) {
        audioManager.play("note");
    }

    private  void onTalkedWithNpc(TalkedToNpcEvent event) {
        audioManager.play("lightkeeper");
    }
}
