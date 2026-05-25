package com.funnubunny.app.sound;

import com.funnubunny.app.audio.AudioManager;
import com.funnubunny.app.core.Time;
import com.funnubunny.app.quest.QuestState;
import com.funnubunny.app.state.*;

public class AmbientSoundSystem {

    private final AudioManager audioManager;
    private final WeatherStateService weatherStateService;
    private final WorldStateService worldStateService;

    private float windVolume;
    private float bellVolume;
    private boolean windOn;
    private boolean bellOn;

    public AmbientSoundSystem(AudioManager audioManager, WeatherStateService weatherStateService, WorldStateService worldStateService) {
        this.audioManager = audioManager;
        this.weatherStateService = weatherStateService;
        this.worldStateService = worldStateService;
        init();
    }

    public void init() {
        audioManager.loop("wind");
        audioManager.loop("bell");
        audioManager.setVolume("wind", 1.0f);
        audioManager.setVolume("bell", 1.0f);
        windVolume = 1.0f;
        bellVolume = 1.0f;
        windOn = true;
        bellOn = true;
    }

    public void update() {
        controlWind();
        controlBell();
    }

    private void controlWind() {
        if (weatherStateService.isWind()) {
            return;
        }

        if (!windOn) {
            return;
        }

        windVolume -= Time.getDeltaTime() * 0.005f;
        audioManager.setVolume("wind", windVolume);

        if (windVolume <= 0.01f && windOn) {
            audioManager.setVolume("wind", 0.0f);
            windOn = false;
        }
    }

    private void controlBell() {
        if (worldStateService.isBellRinging()) {
            return;
        }

        if (!bellOn) {
            return;
        }

        bellVolume -= Time.getDeltaTime() * 0.005f;
        audioManager.setVolume("bell", bellVolume);

        if (bellVolume <= 0.01f && bellOn) {
            audioManager.setVolume("bell", 0.0f);
            bellOn = false;
        }
    }
}
