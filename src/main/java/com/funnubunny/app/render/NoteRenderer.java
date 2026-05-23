package com.funnubunny.app.render;

import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.quest.Note;
import com.funnubunny.app.world.WorldStateService;
import com.jogamp.opengl.GL3;

import java.util.List;

public class NoteRenderer extends Renderer {
    private final ShaderProgram shader;

    public NoteRenderer(WorldStateService worldStateService, ShaderProgram shader) {
        super(worldStateService);
        this.shader = shader;
    }

    @Override
    public void render(RenderContext context) {
        GL3 gl = context.getGl();

        List<Note> notes = worldStateService.getNoteSystem().getNotes();

        for (Note note : notes) {
            if (!note.isCollected()) {
                note.render(gl, shader, context.getCamera());
            }
        }
    }

    @Override
    public int priority() {
        return 0;
    }
}
