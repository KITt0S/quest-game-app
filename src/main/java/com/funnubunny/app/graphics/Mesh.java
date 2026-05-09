package com.funnubunny.app.graphics;

import com.jogamp.opengl.GL3;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static com.jogamp.common.nio.Buffers.newDirectFloatBuffer;
import static com.jogamp.common.nio.Buffers.newDirectIntBuffer;

public class Mesh {
    private final int vaoId;
    private final int vboId;
    private final int eboId;

    private final int indexCount;

    public Mesh(GL3 gl, float[] vertices, int[] indices) {
        indexCount = indices.length;

        IntBuffer vaoBuffer = newDirectIntBuffer(1);
        IntBuffer vboBuffer = newDirectIntBuffer(1);
        IntBuffer eboBuffer = newDirectIntBuffer(1);

        gl.glGenVertexArrays(1, vaoBuffer);
        gl.glGenBuffers(1, vboBuffer);
        gl.glGenBuffers(1, eboBuffer);

        vaoId = vaoBuffer.get(0);
        vboId = vboBuffer.get(0);
        eboId = eboBuffer.get(0);

        gl.glBindVertexArray(vaoId);

        FloatBuffer vertexBuffer = newDirectFloatBuffer(vertices);
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vboId);
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, vertexBuffer, GL3.GL_STATIC_DRAW);

        IntBuffer indexBuffer = newDirectIntBuffer(indices);
        gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, eboId);
        gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, (long) indices.length * Integer.BYTES, indexBuffer, GL3.GL_STATIC_DRAW);

        gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, 3 * Float.BYTES, 0L);
        gl.glEnableVertexAttribArray(0);

        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        gl.glBindVertexArray(0);
    }

    public void render(GL3 gl) {
        gl.glBindVertexArray(vaoId);
        gl.glDrawElements(GL3.GL_TRIANGLES, indexCount, GL3.GL_UNSIGNED_INT, 0L);
        gl.glBindVertexArray(0);
    }

    public void delete(GL3 gl) {
        gl.glDeleteVertexArrays(1, new int[]{vaoId}, 0);
        gl.glDeleteBuffers(1, new int[]{vboId}, 0);
        gl.glDeleteBuffers(1, new int[]{eboId}, 0);
    }
}
