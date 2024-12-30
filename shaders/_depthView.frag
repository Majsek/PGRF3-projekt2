#version 330 core

in vec3 fragPosition; // Pozice fragmentu ve světových souřadnicích

out vec4 color; // Výstupní barva

uniform vec3 viewPos; // Pozice kamery

void main() {
    float distance = length(fragPosition - viewPos);
    
    float brightness = 4.0 / (1.0 + distance);
    color = vec4(vec3(brightness), 1.0);
}
