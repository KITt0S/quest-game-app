package com.funnubunny.app.world;

import com.funnubunny.app.entity.Transform;
import com.funnubunny.app.graphics.*;
import com.jogamp.opengl.GL3;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class WorldObject {

    @Getter
    private final Transform transform;

    private final Mesh mesh;

    @Getter
    private final float width;
    @Getter
    private final float height;

    private final float r;
    private final float g;
    private final float b;

    @Getter
    private final boolean affectedByLight;

    @Getter
    @Setter
    private Sprite sprite;

    public float[] getColor() {
        return new float[] {r, g, b};
    }

    public static WorldObjectBuilder builder() {
        return new WorldObjectBuilder();
    }

    public static class WorldObjectBuilder {
        private Mesh mesh;

        private float x;
        private float y;

        private float width;
        private float height;

        private float r;
        private float g;
        private float b;

        private boolean affectedByLight;

        public WorldObjectBuilder mesh(Mesh mesh) {
            this.mesh = mesh;
            return this;
        }

        public WorldObjectBuilder x(float x) {
            this.x = x;
            return this;
        }

        public WorldObjectBuilder y(float y) {
            this.y = y;
            return this;
        }

        public WorldObjectBuilder width(float width) {
            this.width = width;
            return this;
        }

        public WorldObjectBuilder height(float height) {
            this.height = height;
            return this;
        }

        public WorldObjectBuilder r(float r) {
            this.r = r;
            return this;
        }

        public WorldObjectBuilder g(float g) {
            this.g = g;
            return this;
        }

        public WorldObjectBuilder b(float b) {
            this.b = b;
            return this;
        }

        public WorldObjectBuilder affectedByLight(boolean affectedByLight) {
            this.affectedByLight = affectedByLight;
            return this;
        }

        public WorldObject build() {
            Transform transform = new Transform();
            transform.setPosition(x, y);
            return new WorldObject(transform, mesh, width, height, r, g, b, affectedByLight);
        }
    }
}
