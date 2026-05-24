package com.funnubunny.app.render.renderers;

import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.world.IslandScene;
import com.funnubunny.app.world.WorldObject;
import com.funnubunny.app.state.WorldStateService;
import com.jogamp.opengl.GL3;

import java.util.List;

public class IslandSceneRenderer extends Renderer {
    private final ShaderProgram shader;
    private final ShaderProgram treesShader;

    public IslandSceneRenderer(WorldStateService worldStateService, ShaderProgram shader, ShaderProgram treesShader) {
        super(worldStateService);
        this.shader = shader;
        this.treesShader = treesShader;
    }

    @Override
    public void render(RenderContext context) {
        GL3 gl = context.getGl();
        IslandScene islandScene = worldStateService.getIslandScene();

        List<WorldObject> worldObjects = islandScene.getWorldObjects();

        for (int i = 0; i < worldObjects.size(); i++) {
            WorldObject worldObject = worldObjects.get(i);

            if (i != 3 && i != 4) {
                shader.use(gl);
                shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());
                shader.setUniformVec2(gl, "uPosition", worldObject.getTransform().getPosition().x, worldObject.getTransform().getPosition().y);
                shader.setUniformVec2(gl, "uScale", worldObject.getWidth(), worldObject.getHeight());
                shader.setUniformVec3(gl, "uColor", worldObject.getColor()[0], worldObject.getColor()[1], worldObject.getColor()[2]);
                shader.setUniformBool(gl, "uLighthouseOn", worldStateService.getLighthouse().isActive() && worldObject.isAffectedByLight());
                Mesh.getColorMesh(gl).render(gl);
                shader.detach(gl);
                continue;
            }

            treesShader.use(gl);
            Mesh mesh = Mesh.getSpriteMesh(gl);

            treesShader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());
            treesShader.setUniformVec2(gl, "uPosition", worldObject.getTransform().getPosition().x(), worldObject.getTransform().getPosition().y());
            treesShader.setUniformVec2(gl, "uScale", worldObject.getWidth(), worldObject.getHeight());

            treesShader.setUniformBool(gl, "uLighthouseOn", worldStateService.getLighthouse().isActive() && worldObject.isAffectedByLight());

            gl.glActiveTexture(GL3.GL_TEXTURE0);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
            gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
            worldObject.getSprite().getTexture().bind(gl);
            treesShader.setUniformInt(gl, "uTexture", 0);

            mesh.render(gl);
            treesShader.detach(gl);        }
    }

    @Override
    public int priority() {
        return -100;
    }
}
