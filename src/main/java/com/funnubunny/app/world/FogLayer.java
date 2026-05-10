package com.funnubunny.app.world;

import com.funnubunny.app.entity.Transform;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.graphics.Sprite;
import com.funnubunny.app.graphics.SpriteRenderer;
import com.jogamp.opengl.GL3;

public class FogLayer {
    private final Transform transform;

    private final Sprite sprite;

    private final float width;
    private final float height;

    private final float speed;

    private FogLayer(Transform transform, Sprite sprite, float width, float height, float speed) {
        this.transform = transform;
        this.sprite = sprite;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    public void update(float deltaTime) {
        transform.translate(speed * deltaTime, 0);

        if (transform.getPosition().x > 200) {
            transform.setPosition(-200, transform.getPosition().y);
        }
    }

    public void render(GL3 gl, ShaderProgram shader, Camera2D camera, SpriteRenderer renderer) {
        renderer.render(gl, shader, camera, sprite, transform, width, height);
    }

    public static FogLayerBuilder builder() {
        return new FogLayerBuilder();
    }

    public static class FogLayerBuilder {
        private Sprite sprite;
        private float x;
        private float y;
        private float width;
        private float height;
        private float speed;

        FogLayerBuilder() {
        }

        public FogLayerBuilder sprite(Sprite sprite) {
            this.sprite = sprite;
            return this;
        }

        public FogLayerBuilder x(float x) {
            this.x = x;
            return this;
        }
        public FogLayerBuilder y(float y) {
            this.y = y;
            return this;
        }

        public FogLayerBuilder width(float width) {
            this.width = width;
            return this;
        }

        public FogLayerBuilder height(float height) {
            this.height = height;
            return this;
        }

        public FogLayerBuilder speed(float speed) {
            this.speed = speed;
            return this;
        }

        public FogLayer build() {
            Transform transform = new Transform();
            transform.setPosition(x, y);
            return new FogLayer(transform, sprite, width, height, speed);
        }
    }
}
