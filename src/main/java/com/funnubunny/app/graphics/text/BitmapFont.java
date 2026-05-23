package com.funnubunny.app.graphics.text;

import com.funnubunny.app.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class BitmapFont {
    private final Texture texture;

    private final char[][] chars = {
            {'z', '{', '|', '}', '~' },
            {'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y'},
            {'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'},
            {'\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e'},
            {'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '['},
            {'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q'},
            {'>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G'},
            {'4', '5', '6', '7', '8', '9', ':', ';', '<', '='},
            {'*', '+', ',', '-', '.', '/', '0', '1', '2', '3'},
            {' ', '!', '"', '#', '$', '%', '&', '\'', '(', ')'},
    };

    private final Map<Character, Glyph> glyphs =
            new HashMap<>();

    private static final int COLUMNS = 10;
    private static final int ROWS = 10;

    public BitmapFont(Texture texture) {

        this.texture = texture;

        generateAsciiGlyphs();
    }

    private void generateAsciiGlyphs() {

        for (int i = 0; i < 100; i++) {

            int x = i % ROWS;
            int y = i / ROWS;

            if (y == 0 && x > 4) {
                continue;
            }

            float u0 = x / (float) COLUMNS;
            float v0 = y / (float) ROWS;

            float u1 = (x + 1) / (float) COLUMNS;
            float v1 = (y + 1) / (float) ROWS;

            glyphs.put(chars[y][x], new Glyph(u0, v0, u1, v1));
        }
    }

    public Glyph getGlyph(char c) {

        return glyphs.getOrDefault(c, glyphs.get(' '));
    }

    public Texture getTexture() {

        return texture;
    }
}
