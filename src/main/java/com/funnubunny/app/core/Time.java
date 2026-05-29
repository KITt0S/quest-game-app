package com.funnubunny.app.core;

public class Time {
    private static long lastTime;
    private static float deltaTime;

    private static float startTime;

    private Time() {

    }

    public static void init() {
        startTime = System.nanoTime();
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

    public static float getTime() {
        return (System.nanoTime() - startTime) / 1_000_000_000.0f;
    }
}
