#version 330 core

in vec2 vTexCoord;

out vec4 fragColor;

uniform sampler2D uTexture;

void main() {
    vec4 texColor = texture(uTexture, vTexCoord);

    float luminance = dot(texColor.rgb, vec3(0.2126, 0.7152, 0.0722));
    float threshold = 0.15;
    float softness = 0.10;

    float alpha = smoothstep(threshold, threshold + softness, luminance);
    fragColor = vec4(texColor.rgb, texColor.a * alpha);
}