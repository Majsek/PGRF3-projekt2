#version 330 core

in vec3 fragPosition;// Pozice ve světových souřadnicích

uniform vec3 viewPos;// Pozice kamery

out vec4 FragColor;

void main()
{
    // Vektor z pozice kamery do pozice fragmentu
    vec3 viewDir = fragPosition - viewPos;

    // Převedení do rozsahu 0-1
    vec3 normalizedPos = normalize(viewDir) * 0.5 + 0.5;

    // Nastavení barvy pixelu podle vzdálenosti od kamery
    FragColor = vec4(normalizedPos, 1.0);
}
