package com.funnubunny.app.render;

import com.funnubunny.app.graphics.Camera2D;
import com.jogamp.opengl.GL3;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RenderContext {
    private final GL3 gl;
    private final Camera2D camera;
}
