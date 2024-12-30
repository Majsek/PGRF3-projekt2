#version 330 core

uniform sampler2D textureSampler; // Textura připojená na GPU
uniform bool useTexture = false;

in vec3 fragColor; // Přijatá interpolovaná barva z vertex shaderu
out vec4 color;    // Výstupní barva pixelu
in vec2 fragTexCoords; // Přijmutí texturových souřadnic

void main() {
    vec4 textureColor;
    if(useTexture) {
        textureColor = texture(textureSampler, fragTexCoords); // Vzorkování textury
    } else {
        textureColor = vec4(1, 1, 1, 1);
    }

    color = textureColor * vec4(fragColor, 1.0);
}
