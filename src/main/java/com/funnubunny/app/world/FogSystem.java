package com.funnubunny.app.world;

import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.graphics.Sprite;
import com.funnubunny.app.graphics.SpriteRenderer;
import com.jogamp.opengl.GL3;

import java.util.ArrayList;
import java.util.List;

public class FogSystem {
    private final List<FogLayer> layers;
    private final SpriteRenderer spriteRenderer;
    private final ShaderProgram spriteShader;

    public FogSystem(Sprite fogSprite, SpriteRenderer spriteRenderer, ShaderProgram spriteShader) {
        this.spriteRenderer = spriteRenderer;
        this.spriteShader = spriteShader;

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

    public void render(GL3 gl, Camera2D camera) {
        for (FogLayer layer : layers) {
            layer.render(gl, spriteShader, camera, spriteRenderer);
        }
    }
}
