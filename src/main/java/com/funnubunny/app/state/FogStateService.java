package com.funnubunny.app.state;

public class FogStateService {

    private final FogState fogState;

    public FogStateService(FogState fogState) {
        this.fogState = fogState;
        init();
    }

    private void init() {
    }

    public FogState.Status getFogStatus() {
        return fogState.getStatus();
    }

    public void setFogStatus(FogState.Status status) {
        fogState.setStatus(status);
    }
}
