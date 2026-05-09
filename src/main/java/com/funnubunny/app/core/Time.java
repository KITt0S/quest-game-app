package com.funnubunny.app.core;

public class Time {
    private static long lastTime;
    private static float deltaTime;

    private Time() {

    }

    public static void init() {
        lastTime = System.nanoTime();
    }


    public static void update() {
        long currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / 1_000_000_000.0f;
        lastTime = currentTime;
    }

    public static float getDeltaTime() {
        return deltaTime;
    }
}
