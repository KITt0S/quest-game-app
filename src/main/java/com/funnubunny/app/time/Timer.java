package com.funnubunny.app.time;

public class Timer {
    private boolean on;
    private float value;
    private float duration;

    public Timer() {
        on = false;
        value = 0.0f;
    }

    public void setup(float duration) {
        value = 0.0f;
        this.duration = duration;
    }

    public void start() {
        if (on == false) {
            return;
        }
    }
}
