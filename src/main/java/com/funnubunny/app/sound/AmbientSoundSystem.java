package com.funnubunny.app.sound;

import com.funnubunny.app.audio.AudioManager;
import com.funnubunny.app.core.Time;
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
        audioManager.setVolume("wind", 1.0f);
        audioManager.setVolume("bell", 1.0f);

        audioManager.loop("wind");
        audioManager.loop("bell");

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

        windVolume -= Time.getDeltaTime() * 0.35f;
        audioManager.setVolume("wind", windVolume);

        if (windVolume <= 0.01f && windOn) {
            audioManager.stop("bell");
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

        bellVolume -= Time.getDeltaTime() * 0.35f;
        audioManager.setVolume("bell", bellVolume);

        if (bellVolume <= 0.01f && bellOn) {
            audioManager.stop("bell");
            bellOn = false;
        }
    }
}
