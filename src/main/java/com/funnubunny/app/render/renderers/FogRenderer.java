package com.funnubunny.app.render.renderers;

import com.funnubunny.app.entity.Transform;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.world.FogLayer;
import com.funnubunny.app.world.WorldStateService;
import com.jogamp.opengl.GL3;

public class FogRenderer extends Renderer {
    private final ShaderProgram shader;

    public FogRenderer(WorldStateService worldStateService, ShaderProgram shader) {
        super(worldStateService);
        this.shader = shader;
    }

    @Override
    public void render(RenderContext context) {
        GL3 gl = context.getGl();
        for (FogLayer layer : worldStateService.getFogSystem().getLayers()) {
            Transform transform = layer.getTransform();
            shader.use(gl);
            shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());
            shader.setUniformVec2(gl, "uPosition", transform.getPosition().x, transform.getPosition().y);
            shader.setUniformVec2(gl, "uScale", layer.getWidth(), layer.getHeight());
            gl.glActiveTexture(GL3.GL_TEXTURE0);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
            layer.getSprite().getTexture().bind(gl);
            shader.setUniformInt(gl, "uTexture", 0);
            Mesh.getSpriteMesh(gl).render(gl);
            shader.detach(gl);
        }
    }

    @Override
    public int priority() {
        return 200;
    }
}
