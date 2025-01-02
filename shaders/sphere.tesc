#version 460 core

layout(vertices = 3) out; // Teselujeme trojúhelníky

in vec2 tessellationUV[];  // Přijaté UV z vertex shaderu
in vec3 vertexColor[];     // Přijatá barva z vertex shaderu

out vec2 tessellationUV_out[];  // Posíláme dál
out vec3 vertexColor_out[];     // Posíláme dál

uniform int levelOfTessellation;   // Úroveň tessellace
uniform vec3 viewPos;
uniform mat4 modelMatrix;

void main() {
    // Pozice objektu ve světě
    vec3 objectOffset = vec3(modelMatrix[3][0], modelMatrix[3][1], modelMatrix[3][2]);

// Pozice primitiva (průměrná pozice vrcholů trojúhelníku)
    vec3 primitiveCenter = (gl_in[0].gl_Position.xyz + gl_in[1].gl_Position.xyz + gl_in[2].gl_Position.xyz) / 3.0 + objectOffset;

// Vzdálenosti mezi kamerou a středem primitiva
    float distance = length(viewPos - primitiveCenter);

    int maxTessellationLevel = 12; // Maximální úroveň tessellace
    int minTessellationLevel = 1; // Minimální úroveň tessellace
    // float LODDistance = 30.0;     // Vzdálenost, při které začíná tessellace klesat
    float LODDistance = 50.0;     // Vzdálenost, při které začíná tessellace klesat

    int finalLevelOfTessellation;

// Pokud je objekt blíž než 1, nastaví se maximální tessellace
    if(distance < 1.0) {
        finalLevelOfTessellation = maxTessellationLevel;
    } else if(distance > LODDistance) {
    // Pokud je objekt dál než LODDistance, vypočítá se úroveň tessellace na základě vzdálenosti
        float normalizedDistance = (distance - LODDistance) / LODDistance;
        finalLevelOfTessellation = int(maxTessellationLevel * (1.0 - normalizedDistance));

    // Popřípadě ořízne úroveń tessellace do rozsahu
        finalLevelOfTessellation = clamp(finalLevelOfTessellation, minTessellationLevel, maxTessellationLevel);
    } else {
        finalLevelOfTessellation = maxTessellationLevel;
    }

    // Každému vrcholu předá jeho pozici a atributy
    gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
    tessellationUV_out[gl_InvocationID] = tessellationUV[gl_InvocationID];
    vertexColor_out[gl_InvocationID] = vertexColor[gl_InvocationID];

    if(gl_InvocationID == 0) {
        float tessLevelOuter = finalLevelOfTessellation; // Úroveň detailu pro okraje
        float tessLevelInner = finalLevelOfTessellation; // Úroveň detailu pro vnitřek

        gl_TessLevelOuter[0] = tessLevelOuter;
        gl_TessLevelOuter[1] = tessLevelOuter;
        gl_TessLevelOuter[2] = tessLevelOuter;
        gl_TessLevelInner[0] = tessLevelInner;
    }
}
