#version 330 core

in vec2 vTexCoord;

out vec4 fragColor;

uniform sampler2D uTexture;

void main() {
    vec4 texColor = texture(uTexture, vTexCoord);

    if (texColor.a < 0.01) {
        discard;
    }

    fragColor = texColor;
}