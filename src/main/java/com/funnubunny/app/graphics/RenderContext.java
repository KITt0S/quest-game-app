package com.funnubunny.app.graphics;

import com.jogamp.opengl.GL3;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RenderContext {
    private GL3 gl;
    private ShaderProgram shader;
    private Camera2D camera;
    private boolean lighthouseOn;
    private Mesh mesh;
}
