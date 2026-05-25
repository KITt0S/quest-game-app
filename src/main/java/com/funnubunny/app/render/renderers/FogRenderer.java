package com.funnubunny.app.render.renderers;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.entity.Transform;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.state.EngineState;
import com.funnubunny.app.state.GameStateService;
import com.funnubunny.app.state.WeatherStateService;
import com.funnubunny.app.world.Fog;
import com.funnubunny.app.world.FogLayer;
import com.jogamp.opengl.GL3;

public class FogRenderer implements Renderable {
    private final GameStateService gameStateService;
    private final WeatherStateService weatherStateService;
    private final ShaderProgram shader;

    private float density;

    public FogRenderer(GameStateService gameStateService, WeatherStateService weatherStateService, ShaderProgram shader) {
        this.gameStateService = gameStateService;
        this.weatherStateService = weatherStateService;
        this.shader = shader;
    }

    @Override
    public void render(RenderContext context) {
        if (gameStateService.getEngineState() != EngineState.PLAYING) {
            return;
        }

        GL3 gl = context.getGl();
        for (FogLayer layer : weatherStateService.getFogLayers()) {
            Transform transform = layer.getTransform();
            shader.use(gl);
            shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());
            shader.setUniformVec2(gl, "uPosition", transform.getPosition().x, transform.getPosition().y);
            shader.setUniformVec2(gl, "uScale", layer.getWidth(), layer.getHeight());
            shader.setUniformFloat(gl, "density", weatherStateService.getFogDensity());
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
