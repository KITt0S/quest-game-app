package com.funnubunny.app.render.renderers;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.entity.Player;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.state.WorldStateService;
import com.jogamp.opengl.GL3;

public class NpcRenderer extends Renderer {

    private final ShaderProgram shader;

    public NpcRenderer(WorldStateService worldStateService, ShaderProgram shader) {
        super(worldStateService);
        this.shader = shader;
    }

    @Override
    public void render(RenderContext context) {
        GL3 gl = context.getGl();
        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());

        NPC npc = worldStateService.getNpc();

        shader.setUniformVec2(gl, "uPosition", npc.getTransform().getPosition().x, npc.getTransform().getPosition().y);
        shader.setUniformVec2(gl, "uScale", npc.getWidth(), npc.getHeight());
        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        npc.getSprite().getTexture().bind(gl);
        shader.setUniformInt(gl, "uTexture", 0);
        Mesh.getSpriteMesh(gl).render(gl);
        shader.detach(gl);
    }

    @Override
    public int priority() {
        return 0;
    }
}
