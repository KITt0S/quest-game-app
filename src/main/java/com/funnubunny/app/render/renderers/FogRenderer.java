package com.funnubunny.app.render.renderers;

import com.funnubunny.app.core.Time;
import com.funnubunny.app.entity.Transform;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.state.WeatherStateService;
import com.funnubunny.app.world.Fog;
import com.funnubunny.app.world.FogLayer;
import com.funnubunny.app.state.WorldStateService;
import com.jogamp.opengl.GL3;

public class FogRenderer implements Renderable {
    private final WeatherStateService weatherStateService;
    private final ShaderProgram shader;

    private float density;

    public FogRenderer(WeatherStateService weatherStateService, ShaderProgram shader) {
        this.weatherStateService = weatherStateService;
        this.shader = shader;
        init();
    }

    private void init() {
        switch (weatherStateService.getFog().getStatus()) {
            case FULL -> density = 1.0f;
            case HALF -> density = 0.5f;
            case ABSENT -> density = 0.0f;
        }
    }

    @Override
    public void render(RenderContext context) {
        GL3 gl = context.getGl();
        for (FogLayer layer : weatherStateService.getFog().getLayers()) {
            Transform transform = layer.getTransform();
            shader.use(gl);
            shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());
            shader.setUniformVec2(gl, "uPosition", transform.getPosition().x, transform.getPosition().y);
            shader.setUniformVec2(gl, "uScale", layer.getWidth(), layer.getHeight());
            shader.setUniformFloat(gl, "density", computeDensity());
            gl.glActiveTexture(GL3.GL_TEXTURE0);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
            layer.getSprite().getTexture().bind(gl);
            shader.setUniformInt(gl, "uTexture", 0);
            Mesh.getSpriteMesh(gl).render(gl);
            shader.detach(gl);
        }
    }

    private float computeDensity() {
        Fog.Status status = weatherStateService.getFog().getStatus();

        if (status == Fog.Status.HALF && density == 0.5f) {
            return density;
        }

        if (status == Fog.Status.FULL && density < 1.0f) {
            density += Time.getDeltaTime() * 0.05f;
            return density;
        }

        if (status == Fog.Status.ABSENT && density > 0.0f) {
            density -= Time.getDeltaTime() * 0.05f;
            return density;
        }

        return density;
    }

    @Override
    public int priority() {
        return 200;
    }
}
