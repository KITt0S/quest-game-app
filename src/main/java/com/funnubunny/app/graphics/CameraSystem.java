package com.funnubunny.app.graphics;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.entity.Player;

public class CameraSystem {

    private final Camera2D camera;
    private final Player player;

    public CameraSystem(Camera2D camera, Player player) {
        this.camera = camera;
        this.player = player;
    }

    public void update() {
        camera.follow(player.getPosition(), Time.getDeltaTime());
        camera.update();
    }
}
