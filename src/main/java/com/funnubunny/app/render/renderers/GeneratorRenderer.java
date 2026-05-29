package com.funnubunny.app.render.renderers;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.entity.Generator;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.state.EngineState;
import com.funnubunny.app.state.GameStateService;
import com.funnubunny.app.state.WorldStateService;
import com.jogamp.opengl.GL3;

public class GeneratorRenderer extends Renderer {
    private final ShaderProgram shader;


    public GeneratorRenderer(GameStateService gameStateService, WorldStateService worldStateService, ShaderProgram shader) {
        super(gameStateService, worldStateService);
        this.shader = shader;
    }

    @Override
    public void render(RenderContext context) {
        if (gameStateService.getEngineState() != EngineState.PLAYING) {
            return;
        }

        GL3 gl = context.getGl();

        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());

        Generator generator = worldStateService.getGenerator();
        shader.setUniformVec2(gl, "uPosition", generator.getTransform().getPosition().x, generator.getTransform().getPosition().y);
        shader.setUniformVec2(gl, "uScale", generator.getWidth(), generator.getHeight());
        shader.setUniformInt(gl, "status", generator.getStatus().getShaderValue());
        shader.setUniformFloat(gl, "time", Time.getTime());
        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        generator.getSprites()[0].getTexture().bind(gl);
        shader.setUniformInt(gl, "textures[0]", 0);
        gl.glActiveTexture(GL3.GL_TEXTURE1);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        generator.getSprites()[1].getTexture().bind(gl);
        shader.setUniformInt(gl, "textures[1]", 1);
        Mesh.getSpriteMesh(gl).render(gl);
        shader.detach(gl);
    }

    @Override
    public int priority() {
        return 0;
    }
}
