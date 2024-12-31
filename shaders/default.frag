#version 330 core

uniform sampler2D textureSampler; // Textura připojená na GPU
uniform bool useTexture = false;

// in vec3 fragColor; // Přijatá interpolovaná barva z vertex shaderu
out vec4 color;    // Výstupní barva pixelu
in vec2 uvs; // Přijmutí texturových souřadnic

void main() {
    vec4 textureColor;
    if(useTexture) {
        textureColor = texture(textureSampler, uvs); // Vzorkování textury
    } else {
        textureColor = vec4(1, 1, 1, 1);
    }

    color = textureColor;
}

// #version 460 core

// out vec4 FragColor;
// in vec2 uvs;

// uniform sampler2D screen;

// void main()
// {
// 	//FragColor = vec4(0.965, 0.318, 0.000, 1.0);
// 	FragColor = vec4(uvs + 1.0, 0.000, 1.0)/2.0;
// }