package com.funnubunny.app.graphics;

import com.funnubunny.app.entity.Transform;
import com.jogamp.opengl.GL3;

public class SpriteRenderer {

    private final Mesh mesh;

    public SpriteRenderer(Mesh mesh) {
        this.mesh = mesh;
    }

    public void render(GL3 gl, ShaderProgram shader, Camera2D camera, Sprite sprite, Transform transform, float width, float height) {
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        shader.setUniformVec2(gl, "uPosition", transform.getPosition().x, transform.getPosition().y);
        shader.setUniformVec2(gl, "uScale", width, height);
        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        sprite.getTexture().bind(gl);
        shader.setUniformInt(gl, "uTexture", 0);
        mesh.render(gl);
        shader.detach(gl);
    }
}
