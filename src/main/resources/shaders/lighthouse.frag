#version 330 core

in vec2 vTexCoord;

out vec4 fragColor;

uniform bool active;

uniform sampler2D textures[2];

void main() {
    vec4 texColor;

    if (!active) {
       texColor  = texture(textures[0], vTexCoord);
    } else {
        texColor  = texture(textures[1], vTexCoord);
    }

    if (texColor.a < 0.01) {
        discard;
    }

    fragColor = texColor;
}