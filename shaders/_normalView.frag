#version 330 core

// Příjem dat z vertex shaderu
in vec3 fragNormal;

// Výstupní barva fragmentu
out vec4 color;

void main() {
    // Normalizace normály
    vec3 normal = normalize(fragNormal);

    // Převod normály na barvu
    // Hodnoty normály se pohybují v intervalu [-1, 1], takže je převedeme do rozsahu [0, 1]
    vec3 normalColor = normal * 0.5 + 0.5;

    // Nastavení výsledné barvy
    color = vec4(normalColor, 1.0);
}
