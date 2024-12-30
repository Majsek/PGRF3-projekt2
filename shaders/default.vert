#version 330 core

layout(location = 0) in vec3 position; // Pozice vrcholu
layout(location = 1) in vec3 color;    // Barva vrcholu

out vec3 fragColor; // Barva, která se pošle do fragment shaderu
out vec3 fragPosition;
uniform mat4 camMatrix;
uniform mat4 modelMatrix;

uniform mat4 lightSpaceMatrix = mat4(1.0);
uniform bool depthRendering = false;
out vec4 fragLightSpacePos;

uniform float time;

void main() {

    if(depthRendering) {
        gl_Position = lightSpaceMatrix * camMatrix * modelMatrix * vec4(position, 1.0); // Nastavení pozice

    } else {
        gl_Position = camMatrix * modelMatrix * vec4(position, 1.0); // Nastavení pozice

    }

    fragColor = vec3(color.x + position.z / 1.5, color.y + position.z / 1.5, color.z + position.z / 1.5);
    fragPosition = position;

    fragLightSpacePos = lightSpaceMatrix * vec4(position, 1.0);
}
