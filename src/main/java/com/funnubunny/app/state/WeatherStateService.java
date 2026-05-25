package com.funnubunny.app.state;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.world.Fog;
import com.funnubunny.app.world.FogLayer;

import java.util.List;

public class WeatherStateService {
    private final WeatherState weatherState;

    public WeatherStateService(WeatherState weatherState) {
        this.weatherState = weatherState;
        init();
    }

    public void init() {
        weatherState.getFogState().setStatus(FogState.Status.HALF);

        switch (weatherState.getFogState().getStatus()) {
            case FULL -> weatherState.getFogState().setDensity(1.0f);
            case HALF -> weatherState.getFogState().setDensity(0.5f);
            case ABSENT -> weatherState.getFogState().setDensity(0.0f);
        }

        weatherState.setWind(true);
    }

    public List<FogLayer> getFogLayers() {
        return weatherState.getFogState().getLayers();
    }

    public FogState.Status getFogStatus() {
        return weatherState.getFogState().getStatus();
    }

    public void setFogStatus(FogState.Status status) {
        weatherState.getFogState().setStatus(status);
    }

    public void setWind(boolean wind) {
        weatherState.setWind(wind);
    }

    public boolean isWind() {
        return weatherState.isWind();
    }

    public void setFogDensity(float density) {
        weatherState.getFogState().setDensity(density);
    }

    public float getFogDensity() {
        return weatherState.getFogState().getDensity();
    }

    public FogState getFogState() {
        return weatherState.getFogState();
    }
}
