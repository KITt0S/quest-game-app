package com.funnubunny.app.graphics;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.util.texture.TextureIO;

import java.io.IOException;
import java.io.InputStream;

public class Texture {

    private final com.jogamp.opengl.util.texture.Texture texture;

    public Texture(String path) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);

            if (stream == null) {
                throw new RuntimeException("Texture not found: " + path);
            }

            texture = TextureIO.newTexture(stream, true, TextureIO.PNG);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load texture: " + path, e);
        }
    }

    public void bind(GL3 gl) {
        texture.bind(gl);
    }
}
