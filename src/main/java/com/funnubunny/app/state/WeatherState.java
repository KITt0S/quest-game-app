package com.funnubunny.app.state;

import com.funnubunny.app.world.Fog;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherState {
    private Fog fog;
    private boolean wind;

    public WeatherState(Fog fog) {
        this.fog = fog;
        init();
    }

    private void init() {
        wind = true;
    }

    public void setFogDensity() {

    }
}
