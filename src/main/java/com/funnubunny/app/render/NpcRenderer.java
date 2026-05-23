package com.funnubunny.app.render;

import com.funnubunny.app.entity.NPC;
import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.world.WorldStateService;
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
        shader.setUniformVec3(gl, "uColor", npc.getColor()[0], npc.getColor()[1], npc.getColor()[2]);
        Mesh.getSpriteMesh(gl).render(gl);
        shader.detach(gl);
    }

    @Override
    public int priority() {
        return 0;
    }
}
