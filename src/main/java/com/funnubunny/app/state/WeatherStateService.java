package com.funnubunny.app.state;

import com.funnubunny.app.world.Fog;
import com.funnubunny.app.world.FogLayer;

public class WeatherStateService {
    private final WeatherState weatherState;

    public WeatherStateService(WeatherState weatherState) {
        this.weatherState = weatherState;
    }

    public Fog getFog() {
        return weatherState.getFog();
    }

    public boolean isWind() {
        return weatherState.isWind();
    }
}
