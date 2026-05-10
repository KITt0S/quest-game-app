package com.funnubunny.app.graphics;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import org.joml.Matrix2f;
import org.joml.Matrix4f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.IntBuffer;

public class ShaderProgram {

    private final int programId;

    public ShaderProgram(GL3 gl, String vertexShaderPath, String fragmentShaderPath) {
        String vertexSource = loadResource(vertexShaderPath);
        String fragmentSource = loadResource(fragmentShaderPath);

        int vertexShader = compileShader(gl, GL3.GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = compileShader(gl, GL3.GL_FRAGMENT_SHADER, fragmentSource);

        programId = gl.glCreateProgram();
        gl.glAttachShader(programId, vertexShader);
        gl.glAttachShader(programId, fragmentShader);

        gl.glLinkProgram(programId);
        validateProgram(gl);

        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);
    }

    public void use(GL3 gl) {
        gl.glUseProgram(programId);
    }

    public void detach(GL3 gl) {
        gl.glUseProgram(0);
    }

    public void delete(GL3 gl) {
        gl.glDeleteProgram(programId);
    }

    public int getProgramId() {
        return programId;
    }

    private int compileShader(GL3 gl, int shaderType, String source) {
        int shaderId = gl.glCreateShader(shaderType);

        String[] lines = new String[]{source};
        int[] lengths = new int[]{source.length()};

        gl.glShaderSource(shaderId, 1, lines, lengths, 0);

        gl.glCompileShader(shaderId);

        IntBuffer status = IntBuffer.allocate(1);
        gl.glGetShaderiv(shaderId, GL3.GL_COMPILE_STATUS, status);

        if (status.get(0) == GL3.GL_FALSE) {
            IntBuffer logLength = IntBuffer.allocate(1);
            gl.glGetShaderiv(shaderId, GL3.GL_INFO_LOG_LENGTH, logLength);

            byte[] log = new byte[logLength.get(0)];
            gl.glGetShaderInfoLog(shaderId, log.length, null, 0, log, 0);

            throw new RuntimeException("Shader compilation failed:\n" + new String(log));
        }

        return shaderId;
    }

    private void validateProgram(GL3 gl) {
        IntBuffer status = IntBuffer.allocate(1);
        gl.glGetProgramiv(programId, GL3.GL_LINK_STATUS, status);

        if (status.get(0) == GL3.GL_FALSE) {
            IntBuffer logLength = IntBuffer.allocate(1);
            gl.glGetProgramiv(programId, GL3.GL_INFO_LOG_LENGTH, logLength);

            byte[] log = new byte[logLength.get(0)];
            gl.glGetProgramInfoLog(programId, log.length, null, 0, log, 0);

            throw new RuntimeException("Shader program linking failed:\n" + new String(log));
        }
    }

    private String loadResource(String path) {
        InputStream inputStream = getClass().getResourceAsStream(path);

        if (inputStream == null) {
            throw new RuntimeException("Resource not found: " + path);
        }

        StringBuilder builder = new StringBuilder();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load shader: " + path, e);
        }

        return builder.toString();
    }

    public void setUniformMatrix4f(GL3 gl, String name, Matrix4f matrix) {
        int location = gl.glGetUniformLocation(programId, name);
        float[] buffer = new float[16];
        matrix.get(buffer);
        gl.glUniformMatrix4fv(location, 1, false, buffer, 0);
    }

    public void setUniformBool(GL3 gl, String name, boolean value) {
        int location = gl.glGetUniformLocation(programId, name);
        gl.glUniform1i(location, value ? 1 : 0);
    }
    
    public void setUniformVec2(GL3 gl, String name, float x, float y) {
        int location = gl.glGetUniformLocation(programId, name);
        gl.glUniform2f(location, x, y);
    }

    public void setUniformVec3(GL3 gl, String name, float x,float y, float z) {
        int location = gl.glGetUniformLocation(programId, name);
        gl.glUniform3f(location, x, y, z);
    }

    public void setUniformInt(GL3 gl, String name, int value) {
        int location = gl.glGetUniformLocation(programId, name);
        gl.glUniform1i(location, value);
    }
}
