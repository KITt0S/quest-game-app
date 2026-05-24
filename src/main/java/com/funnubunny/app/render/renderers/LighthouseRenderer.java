package com.funnubunny.app.render.renderers;

import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.world.Lighthouse;
import com.funnubunny.app.state.WorldStateService;
import com.jogamp.opengl.GL3;

public class LighthouseRenderer extends Renderer {
    private final ShaderProgram shader;

    public LighthouseRenderer(WorldStateService worldStateService, ShaderProgram shader) {
        super(worldStateService);
        this.shader = shader;
    }

    @Override
    public void render(RenderContext context) {
        GL3 gl = context.getGl();
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());

        Lighthouse lighthouse = worldStateService.getLighthouse();
        shader.setUniformVec2(gl, "uPosition", lighthouse.getTransform().getPosition().x, lighthouse.getTransform().getPosition().y);
        shader.setUniformVec2(gl, "uScale", lighthouse.getWidth(), lighthouse.getHeight());
        shader.setUniformBool(gl, "active", lighthouse.isActive());
        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        lighthouse.getSprites()[0].getTexture().bind(gl);
        shader.setUniformInt(gl, "textures[0]", 0);
        gl.glActiveTexture(GL3.GL_TEXTURE1);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        lighthouse.getSprites()[1].getTexture().bind(gl);
        shader.setUniformInt(gl, "textures[1]", 1);
        Mesh.getSpriteMesh(gl).render(gl);
        shader.detach(gl);
    }

    @Override
    public int priority() {
        return 100;
    }
}
