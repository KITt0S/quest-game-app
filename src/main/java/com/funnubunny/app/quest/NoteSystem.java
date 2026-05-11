package com.funnubunny.app.quest;

import com.funnubunny.app.graphics.Camera2D;
import com.funnubunny.app.graphics.ShaderProgram;
import com.jogamp.opengl.GL3;

import java.util.List;

public class NoteSystem {

    private final List<Note> notes;

    public NoteSystem(List<Note> notes) {
        this.notes = notes;
    }

    public void update() {

        for (Note note : notes) {
            note.update();
        }
    }

    public void renger(GL3 gl, ShaderProgram spriteShader, Camera2D camera) {
        for (Note note : notes) {
            if (!note.isCollected()) {
                note.render(gl, spriteShader, camera);
            }
        }
    }
}
