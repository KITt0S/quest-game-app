package com.funnubunny.app.state;

import com.funnubunny.app.world.Fog;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherState {
    private FogState fogState;
    private boolean wind;

    public WeatherState(FogState fogState, boolean wind) {
        this.fogState = fogState;
        this.wind = wind;
    }
}
