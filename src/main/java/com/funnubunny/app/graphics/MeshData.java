package com.funnubunny.app.graphics;

public class MeshData {
    public static final float[] SHAPE_VERTICES = {
            -0.5f, 0.5f, 0f,
            -0.5f, -0.5f, 0f,
            0.5f, -0.5f, 0f,
            0.5f, 0.5f, 0f
    };

    public static final float[] SPRITE_VERTICES = {
            // x y z u v
            -0.5f, 0.5f, 0f, 0f, 1f,
            -0.5f, -0.5f, 0f, 0f, 0f,
            0.5f, -0.5f, 0f, 1f, 0f,
            0.5f, 0.5f, 0f, 1f, 1f
    };

    public static final int[] INDICES = {
            0, 1, 2,
            2, 3, 0
    };
}
