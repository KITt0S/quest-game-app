package com.funnubunny.app.world;

import com.funnubunny.app.entity.Transform;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.Renderable;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.opengl.GL3;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorldObject implements Renderable {

    private final Transform transform;

    private final Mesh mesh;

    private final float width;
    private final float height;

    private final float r;
    private final float g;
    private final float b;

    private final boolean affectedByLight;

    @Override
    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {
        render(gl, shader, camera, false);
    }

    private void render(GL3 gl, ShaderProgram shader, Camera2D camera, boolean lighthouseOn) {
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        shader.setUniformVec2(gl, "uPosition", transform.getPosition().x, transform.getPosition().y);
        shader.setUniformVec2(gl, "uScale", width, height);
        shader.setUniformVec3(gl, "uColor", r, g, b);
        shader.setUniformBool(gl, "uLighthouseOn", lighthouseOn && affectedByLight);
        mesh.render(gl);
        shader.detach(gl);
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
