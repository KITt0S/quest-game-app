#version 330 core

in vec2 vTexCoord;

out vec4 fragColor;

uniform sampler2D uTexture;

uniform float density;

void main() {
    vec4 texColor = texture(uTexture, vTexCoord);

    fragColor = vec4(texColor.rgb, texColor.a * density);
}