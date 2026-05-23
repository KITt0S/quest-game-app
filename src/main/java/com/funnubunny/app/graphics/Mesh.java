package com.funnubunny.app.graphics;

import com.jogamp.opengl.GL3;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static com.jogamp.common.nio.Buffers.newDirectFloatBuffer;
import static com.jogamp.common.nio.Buffers.newDirectIntBuffer;

public class Mesh {
    private ShaderProgram shader;

    private int vaoId;
    private int vboId;
    private int eboId;

    private int indexCount;

    public Mesh(int vaoId, int vboId, int eboId, int indexCount) {
        this.vaoId = vaoId;
        this.vboId = vboId;
        this.eboId = eboId;
        this.indexCount = indexCount;
    }

    public static Mesh getColorMesh(GL3 gl, float[] vertices, int[] indices) {
        int indexCount = indices.length;

        IntBuffer vaoBuffer = newDirectIntBuffer(1);
        IntBuffer vboBuffer = newDirectIntBuffer(1);
        IntBuffer eboBuffer = newDirectIntBuffer(1);

        gl.glGenVertexArrays(1, vaoBuffer);
        gl.glGenBuffers(1, vboBuffer);
        gl.glGenBuffers(1, eboBuffer);

        int vaoId = vaoBuffer.get(0);
        int vboId = vboBuffer.get(0);
        int eboId = eboBuffer.get(0);

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

        return new Mesh(vaoId, vboId, eboId, indexCount);
    }

    public static Mesh getSpriteMesh(GL3 gl, float[] vertices, int[] indices) {
        int indexCount = indices.length;

        IntBuffer vaoBuffer = newDirectIntBuffer(1);
        IntBuffer vboBuffer = newDirectIntBuffer(1);
        IntBuffer eboBuffer = newDirectIntBuffer(1);

        gl.glGenVertexArrays(1, vaoBuffer);
        gl.glGenBuffers(1, vboBuffer);
        gl.glGenBuffers(1, eboBuffer);

        int vaoId = vaoBuffer.get(0);
        int vboId = vboBuffer.get(0);
        int eboId = eboBuffer.get(0);

        gl.glBindVertexArray(vaoId);

        FloatBuffer vertexBuffer = newDirectFloatBuffer(vertices);
        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, vboId);
        gl.glBufferData(GL3.GL_ARRAY_BUFFER, (long) vertices.length * Float.BYTES, vertexBuffer, GL3.GL_STATIC_DRAW);

        IntBuffer indexBuffer = newDirectIntBuffer(indices);
        gl.glBindBuffer(GL3.GL_ELEMENT_ARRAY_BUFFER, eboId);
        gl.glBufferData(GL3.GL_ELEMENT_ARRAY_BUFFER, (long) indices.length * Integer.BYTES, indexBuffer, GL3.GL_STATIC_DRAW);

        int stride = 5 * Float.BYTES;
        gl.glVertexAttribPointer(0, 3, GL3.GL_FLOAT, false, stride, 0L);
        gl.glEnableVertexAttribArray(0);

        gl.glVertexAttribPointer(1, 2, GL3.GL_FLOAT, false, stride, 3 * Float.BYTES);
        gl.glEnableVertexAttribArray(1);

        gl.glBindBuffer(GL3.GL_ARRAY_BUFFER, 0);
        gl.glBindVertexArray(0);

        return new Mesh(vaoId, vboId, eboId, indexCount);
    }

    public static Mesh getSpriteMesh(GL3 gl) {
        return getSpriteMesh(gl, MeshData.SPRITE_VERTICES, MeshData.INDICES);
    }

    public void useShader(ShaderProgram shader) {
        this.shader = shader;
    }

    public void uploadTransform(GL3 gl, Camera2D camera, float x, float y, float width, float height) {
        shader.setUniformMatrix4f(gl, "uProjectionView", camera.getProjectionView());
        shader.setUniformVec2(gl, "uPosition", x, y);
        shader.setUniformVec2(gl, "uScale", width, height);
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
