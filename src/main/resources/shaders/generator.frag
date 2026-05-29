#version 330 core

out vec4 fragColor;

in vec2 vTexCoord;

uniform int status;
uniform sampler2D textures[2];
uniform float time;

const float pi = 3.14159265359;

void main()
{

    if (status == 0) {
        fragColor = texture(textures[0], vTexCoord);
        return;
    }

    if (status == 2) {
        fragColor = texture(textures[1], vTexCoord);
        return;
    }

    if (status == 1) {
        vec4 texColor = texture(textures[0], vTexCoord);

        // Center coordinates (-0.5 to 0.5)
        vec2 centered = vTexCoord - vec2(0.5);

        // Distance from center
        float dist = length(centered);

        // Circular mask
        float glow = smoothstep(0.4, 0.0, dist);

        // Blinking
        float blink = abs(sin(2 * pi * 0.5 * time));

        // Final glow strength
        float intensity = glow * blink;

        vec3 redGlow = vec3(1.0, 0.0, 0.0) * intensity;

        vec3 finalColor = texColor.rgb + redGlow;

        fragColor = vec4(finalColor, texColor.a);
    }
}