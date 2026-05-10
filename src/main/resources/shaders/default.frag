#version 330 core

out vec4 fragColor;

uniform vec3 uColor;
uniform bool uLighthouseOn;

void main() {
    vec3 color = uColor;

    if (uLighthouseOn) {
        color += vec3(0.12, 0.12, 0.08);
    }

    fragColor = vec4(color, 1.0);
}