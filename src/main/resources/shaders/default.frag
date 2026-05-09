#version 330 core

out vec4 fragColor;

uniform bool uLightHouseOn;

void main() {
    vec3 darkFog = vec3(0.02, 0.03, 0.06);
    vec3 litFog = vec3(0.2, 0.25, 0.3);

    vec3 color = uLightHouseOn ? litFog : darkFog;

    fragColor = vec4(color, 1.0);
}