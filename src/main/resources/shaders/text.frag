#version 330 core

in vec2 vTexCoord;

out vec4 fragColor;

uniform sampler2D uTexture;

void main() {

    vec4 color = texture(uTexture, vTexCoord);

    fragColor = color;
}