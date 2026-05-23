package com.funnubunny.app.graphics.text;

import com.funnubunny.app.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class BitmapFont {
    private final Texture texture;

    private final Map<Character, Glyph> glyphs =
            new HashMap<>();

    private static final int COLUMNS = 16;
    private static final int ROWS = 16;

    public BitmapFont(Texture texture) {

        this.texture = texture;

        generateAsciiGlyphs();
    }

    private void generateAsciiGlyphs() {

        for (int i = 0; i < 256; i++) {

            int x = i % COLUMNS;
            int y = i / COLUMNS;

            float u0 = x / (float) COLUMNS;
            float v0 = y / (float) ROWS;

            float u1 = (x + 1) / (float) COLUMNS;
            float v1 = (y + 1) / (float) ROWS;

            glyphs.put((char) i, new Glyph(u0, v0, u1, v1));
        }
    }

    public Glyph getGlyph(char c) {

        return glyphs.getOrDefault(c, glyphs.get(' '));
    }

    public Texture getTexture() {

        return texture;
    }
}
