package com.funnubunny.app.graphics;

import com.funnubunny.app.world.WorldObject;
import com.jogamp.opengl.GL3;

public class WorldObjectRenderer {

    public void render(GL3 gl, WorldObject worldObject, RenderContext context) {
        if (worldObject.getSprite() == null) {
            worldObject.render(gl, context.getShader(), context.getCamera(), context.isLighthouseOn());
            return;
        }

        ShaderProgram shader = context.getShader();
        shader.use(gl);
        context.getMesh().useShader(shader);
        context.getMesh().uploadTransform(gl,
                context.getCamera(),
                worldObject.getTransform().getPosition().x(),
                worldObject.getTransform().getPosition().y(),
                worldObject.getWidth(), worldObject.getHeight());

        shader.setUniformBool(gl, "uLighthouseOn", context.isLighthouseOn() && worldObject.isAffectedByLight());

        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        worldObject.getSprite().getTexture().bind(gl);
        shader.setUniformInt(gl, "uTexture", 0);

        context.getMesh().render(gl);
        shader.detach(gl);
    }
}
