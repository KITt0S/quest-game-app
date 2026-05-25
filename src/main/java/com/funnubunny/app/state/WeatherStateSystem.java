package com.funnubunny.app.state;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.DestroyedLighthouseEvent;
import com.funnubunny.app.event.events.RelightedLighthouseEvent;

public class WeatherStateSystem {
    private WeatherStateService weatherStateService;

    public WeatherStateSystem(WeatherStateService weatherStateService, EventBus eventBus) {
        this.weatherStateService = weatherStateService;
        eventBus.register(RelightedLighthouseEvent.class, this::onRelightedLighthouse);
        eventBus.register(DestroyedLighthouseEvent.class, this::onDestroyedLighthouse);
    }

    private void onRelightedLighthouse(RelightedLighthouseEvent event) {
        weatherStateService.setFogStatus(FogState.Status.FULL);
    }

    private void onDestroyedLighthouse(DestroyedLighthouseEvent event) {
        weatherStateService.setFogStatus(FogState.Status.ABSENT);
        weatherStateService.setWind(false);
    }

    public void update() {
        updateFog();
    }

    private void updateFog() {
        weatherStateService.getFogState().update(Time.getDeltaTime());
        updateDensity();
    }

    private void updateDensity() {
        float density = weatherStateService.getFogDensity();

        FogState.Status status = weatherStateService.getFogStatus();

        if (status == FogState.Status.HALF && density == 0.5f) {
        }

        if (status == FogState.Status.FULL && density < 1.0f) {
            density += Time.getDeltaTime() * 0.05f;
        }

        if (status == FogState.Status.ABSENT && density > 0.0f) {
            density -= Time.getDeltaTime() * 0.05f;
        }

        weatherStateService.setFogDensity(density);
    }
}
