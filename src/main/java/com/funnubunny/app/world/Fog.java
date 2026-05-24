package com.funnubunny.app.world;

import com.funnubunny.app.graphics.Sprite;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Fog {
    private Status status;
    private List<FogLayer> layers;

    public Fog(Sprite fogSprite) {
        layers = new ArrayList<>();
        createLayers(fogSprite);
        init();
    }

    private void init() {
        status = Status.HALF;
    }

    private void createLayers(Sprite fogSprite) {
        layers.add(FogLayer
                .builder()
                .sprite(fogSprite)
                .x(-350)
                .y(200)
                .width(512)
                .height(512)
                .speed(10)
                .build());

        layers.add(FogLayer
                .builder()
                .sprite(fogSprite)
                .x(0)
                .y(0)
                .width(512)
                .height(512)
                .speed(6)
                .build());

        layers.add(FogLayer
                .builder()
                .sprite(fogSprite)
                .x(350)
                .y(-200)
                .width(512)
                .height(512)
                .speed(4)
                .build());
    }

    public void update(float deltaTime) {
        for (FogLayer layer : layers) {
            layer.update(deltaTime);
        }
    }

    public enum Status {
        FULL,
        HALF,
        ABSENT
    }
}
