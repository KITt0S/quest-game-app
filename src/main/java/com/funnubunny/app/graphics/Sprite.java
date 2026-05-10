package com.funnubunny.app.graphics;

public class Sprite {
    private final Texture texture;

    public Sprite(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }
}
