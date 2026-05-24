package com.funnubunny.app.render;

import com.funnubunny.app.graphics.ShaderProgram;
import com.funnubunny.app.graphics.text.BitmapFont;
import com.funnubunny.app.graphics.text.Glyph;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;

public class TextRenderer {

    private final BitmapFont font;

    private final ShaderProgram shader;

    private int vao;
    private int vbo;

    private static final float GLYPH_SIZE = 50f;

    public TextRenderer(
            GL3 gl,
            BitmapFont font,
            ShaderProgram shader) {

        this.font = font;
        this.shader = shader;

        createBuffers(gl);
    }

    private void createBuffers(GL3 gl) {

        int[] vaoArray = new int[1];
        int[] vboArray = new int[1];

        gl.glGenVertexArrays(1, vaoArray, 0);
        gl.glGenBuffers(1, vboArray, 0);

        vao = vaoArray[0];
        vbo = vboArray[0];

        gl.glBindVertexArray(vao);

        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);

        gl.glBufferData(
                GL3.GL_ARRAY_BUFFER,
                6 * 4 * Float.BYTES,
                null,
                GL3.GL_DYNAMIC_DRAW
        );

        gl.glEnableVertexAttribArray(0);

        gl.glVertexAttribPointer(
                0,
                2,
                GL3.GL_FLOAT,
                false,
                4 * Float.BYTES,
                0
        );

        gl.glEnableVertexAttribArray(1);

        gl.glVertexAttribPointer(
                1,
                2,
                GL3.GL_FLOAT,
                false,
                4 * Float.BYTES,
                2 * Float.BYTES
        );

        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);

        gl.glBindVertexArray(0);
    }

    public void renderText(
            GL3 gl,
            String text,
            float x,
            float y,
            float percentageScale) {

        shader.use(gl);
        shader.setUniformMatrix4f(gl, "uProjection", new Matrix4f().ortho2D(0, 1280, 0, 720));
        gl.glActiveTexture(GL3.GL_TEXTURE0);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MIN_FILTER, GL3.GL_LINEAR);
        gl.glTexParameteri(GL3.GL_TEXTURE_2D, GL3.GL_TEXTURE_MAG_FILTER, GL3.GL_LINEAR);
        font.getTexture().bind(gl);
        shader.setUniformInt(gl, "uTexture", 0);
        gl.glBindVertexArray(vao);

        float cursorX = x;

        for (char c : text.toCharArray()) {

            if (c == '\n') {

                y -= 40f * percentageScale / 100.0f;

                cursorX = x;

                continue;
            }

            Glyph glyph = font.getGlyph(c);

            renderGlyph(gl, glyph, cursorX, y, percentageScale);

            cursorX += 20f * percentageScale / 100.0f;
        }

        gl.glBindVertexArray(0);
    }

    public void renderText(GL3 gl, String text, float x, float y) {
        renderText(gl, text, x, y, 100.0f);
    }

    private void renderGlyph(
            GL3 gl,
            Glyph glyph,
            float x,
            float y,
            float percentageScale) {

        float x0 = x;
        float y0 = y;

        float x1 = x + GLYPH_SIZE * percentageScale / 100.0f;
        float y1 = y + GLYPH_SIZE * percentageScale / 100.0f;

        float[] vertices = {

                x0, y1,
                glyph.getU0(), glyph.getV1(),

                x0, y0,
                glyph.getU0(), glyph.getV0(),

                x1, y0,
                glyph.getU1(), glyph.getV0(),

                x0, y1,
                glyph.getU0(), glyph.getV1(),

                x1, y0,
                glyph.getU1(), glyph.getV0(),

                x1, y1,
                glyph.getU1(), glyph.getV1()
        };

        FloatBuffer buffer =
                Buffers.newDirectFloatBuffer(vertices);

        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vbo);

        gl.glBufferSubData(
                GL3.GL_ARRAY_BUFFER,
                0,
                vertices.length * Float.BYTES,
                buffer
        );

        gl.glDrawArrays(GL3.GL_TRIANGLES, 0, 6);
    }

    public void delete(GL3 gl) {

        gl.glDeleteVertexArrays(
                1,
                new int[]{vao},
                0
        );

        gl.glDeleteBuffers(
                1,
                new int[]{vbo},
                0
        );
    }
}