#version 330 core

layout(location = 0) in vec3 position; // Vrcholové souřadnice
layout(location = 1) in vec3 color;    // Barva vrcholu

out vec2 tessellationUV;  // Normalizované souřadnice u a v
out vec3 vertexColor;     // Barva vrcholu

void main() {
    // Normalizace `position` na [0, 1] pro použití jako UV
    tessellationUV = (position.xy + vec2(1.0)) * 0.5;
    vertexColor = color;

    // Předání dál bez transformace
    gl_Position = vec4(position, 1.0);
}
