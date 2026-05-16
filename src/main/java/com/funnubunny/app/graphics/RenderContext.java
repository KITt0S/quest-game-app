package com.funnubunny.app.graphics;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RenderContext {
    private ShaderProgram shader;
    private Camera2D camera;
    private boolean lighthouseOn;
    private Mesh mesh;
}
