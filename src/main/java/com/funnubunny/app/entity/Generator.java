package com.funnubunny.app.entity;

import com.funnubunny.app.graphics.Sprite;

public class Generator extends Entity {
    private Status status;
    private final Sprite[] sprites;

    public Generator(Sprite[] sprites) {
        super();
        setSize(150, 150);
        transform.setPosition(375, 375);
        this.sprites = sprites;
        status = Status.OFF;
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        OFF(0),
        BLINKING(1),
        RESTORED(2);

        private final int shaderValue;

        Status(int shaderValue) {
            this.shaderValue = shaderValue;
        }

        public int getShaderValue() {
            return shaderValue;
        }
    }
}
