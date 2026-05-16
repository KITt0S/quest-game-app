package com.funnubunny.app.world;

import com.funnubunny.app.entity.Entity;
import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.graphics.Sprite;
import com.jogamp.opengl.GL3;
import org.joml.Vector2f;

public class Lighthouse extends Entity {
    private boolean active = false;
    private Sprite[] sprites;

    public Lighthouse() {
        super();
        setSize(350, 350);
        transform.setPosition(0, 340);
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSprites(Sprite inactive, Sprite active) {
        sprites = new Sprite[2];
        sprites[0] = inactive;
        sprites[1] = active;
    }

    @Override
    public void update() {

    }

    public Vector2f getPosition() {
        return transform.getPosition();
    }

    @Override
    public void render(GL3 gl, ShaderProgram shader, Camera2D camera) {
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        shader.setUniformVec2(gl, "uPosition", transform.getPosition().x, transform.getPosition().y);
        shader.setUniformVec2(gl, "uScale", width, height);
        shader.setUniformBool(gl, "active", active);
        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        sprites[0].getTexture().bind(gl);
        shader.setUniformInt(gl, "textures[0]", 0);
        gl.glActiveTexture(GL3.GL_TEXTURE1);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        sprites[1].getTexture().bind(gl);
        shader.setUniformInt(gl, "textures[1]", 1);
        mesh.render(gl);
        shader.detach(gl);
    }
}
