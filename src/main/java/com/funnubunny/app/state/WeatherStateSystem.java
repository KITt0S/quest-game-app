package com.funnubunny.app.state;

import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.events.DestroyedLighthouseEvent;
import com.funnubunny.app.event.events.GameEvent;
import com.funnubunny.app.event.events.RelightedLighthouseEvent;
import com.funnubunny.app.world.Fog;

public class WeatherStateSystem {
    private WeatherState weatherState;

    public WeatherStateSystem(WeatherState weatherState, EventBus eventBus) {
        this.weatherState = weatherState;
        eventBus.register(RelightedLighthouseEvent.class, this::onRelightedLighthouse);
        eventBus.register(DestroyedLighthouseEvent.class, this::onDestroyedLighthouse);
    }

    private void onRelightedLighthouse(RelightedLighthouseEvent event) {
        weatherState.getFog().setStatus(Fog.Status.FULL);
    }

    private void onDestroyedLighthouse(DestroyedLighthouseEvent event) {
        weatherState.getFog().setStatus(Fog.Status.ABSENT);
        weatherState.setWind(false);
    }
}
