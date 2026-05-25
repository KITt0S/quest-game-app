package com.funnubunny.app.render.renderers;

import com.funnubunny.app.graphics.Mesh;
import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.note.Note;
import com.funnubunny.app.render.RenderContext;
import com.funnubunny.app.state.EngineState;
import com.funnubunny.app.state.GameStateService;
import com.funnubunny.app.state.WorldStateService;
import com.jogamp.opengl.GL3;

import java.util.List;

public class NoteRenderer extends Renderer {
    private final ShaderProgram shader;

    public NoteRenderer(GameStateService gameStateService, WorldStateService worldStateService, ShaderProgram shader) {
        super(gameStateService, worldStateService);
        this.shader = shader;
    }

    @Override
    public void render(RenderContext context) {
        if (gameStateService.getEngineState() != EngineState.PLAYING) {
            return;
        }

        GL3 gl = context.getGl();

        List<Note> notes = worldStateService.getNotes();

        for (Note note : notes) {
            if (!note.isCollected()) {
                shader.use(gl);
                shader.setUniformMatrix4f(gl, "uProjectionView", context.getCamera().getProjectionView());
                shader.setUniformVec2(gl, "uPosition", note.getTransform().getPosition().x, note.getTransform().getPosition().y);
                shader.setUniformVec2(gl, "uScale", note.getWidth(), note.getHeight());
                gl.glActiveTexture(GL3.GL_TEXTURE0);
                gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
                gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
                note.getSprite().getTexture().bind(gl);
                shader.setUniformInt(gl, "uTexture", 0);
                Mesh.getSpriteMesh(gl).render(gl);
                shader.detach(gl);
            }
        }
    }

    @Override
    public int priority() {
        return 100;
    }
}
