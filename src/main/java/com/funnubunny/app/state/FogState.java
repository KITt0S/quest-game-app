package com.funnubunny.app.state;

import com.funnubunny.app.graphics.Sprite;
import com.funnubunny.app.world.FogLayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class FogState {
    private Status status;
    private float density;

    private List<FogLayer> layers;

    public FogState(Sprite fogSprite) {
        layers = new ArrayList<>();
        createLayers(fogSprite);
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

    enum Status {
        FULL,
        HALF,
        ABSENT
    }
}
