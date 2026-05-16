#version 330 core

layout (location = 0) in vec3 aPosition;
layout (location = 1) in vec2 aTexCoord;

uniform mat4 uProjectionView;

uniform vec2 uPosition;
uniform vec2 uScale;

out vec2 vTexCoord;

void main() {
    vec3 scaled = vec3(
        aPosition.x * uScale.x,
        aPosition.y * uScale.y,
        aPosition.z);

    vec3 translated = scaled + vec3(
        uPosition,
        0.0);

    gl_Position = uProjectionView * vec4(translated, 1.0);
    vTexCoord = aTexCoord;
}