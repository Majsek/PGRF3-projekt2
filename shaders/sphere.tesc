#version 460 core

layout(vertices = 3) out; // Teselujeme trojúhelníky

in vec2 tessellationUV[];  // Přijaté UV z vertex shaderu
in vec3 vertexColor[];     // Přijatá barva z vertex shaderu

out vec2 tessellationUV_out[];  // Posíláme dál
out vec3 vertexColor_out[];     // Posíláme dál

uniform int levelOfTessellation;       // Manuální úroveň tessellace
uniform bool useAutoLODTessellation;   // Povolit automatické LOD
uniform vec3 viewPos;                  // Pozice kamery
uniform mat4 modelMatrix;              // Modelová matice
uniform float planetScale;              // Planet Scale

void main() {
    // Parametry teselace
    const int maxTessellationLevel = 12; // Maximální úroveň teselace
    const int minTessellationLevel = 1;  // Minimální úroveň teselace
    const float maxDetailDistance = planetScale * 1.0;    // Vzdálenost, do které je teselace na maximu
    const float minDetailDistance = planetScale * 70.0;   // Vzdálenost, od které je teselace na minimu

    int finalLevelOfTessellation;

    if(useAutoLODTessellation) {
        // Pozice objektu ve světě
        vec3 objectOffset = vec3(modelMatrix[3][0], modelMatrix[3][1], modelMatrix[3][2]);

        // Pozice primitiva (průměrná pozice vrcholů trojúhelníku)
        vec3 primitiveCenter = (gl_in[0].gl_Position.xyz + gl_in[1].gl_Position.xyz + gl_in[2].gl_Position.xyz) / 3.0 + objectOffset;

        // Vzdálenosti mezi kamerou a středem primitiva
        float distance = length(viewPos - primitiveCenter);

        // Výpočet teselace
        if(distance <= maxDetailDistance) {
    // Maximální teselace pro objekty blízko kamery
            finalLevelOfTessellation = maxTessellationLevel;
        } else if(distance >= minDetailDistance) {
    // Minimální teselace pro vzdálené objekty
            finalLevelOfTessellation = minTessellationLevel;
        } else {
    // Parametr pro střední úroveň
            const int midDetailLevel = 2; // Mezní hodnota teselace

            float normalizedDistance = (distance - maxDetailDistance) / (minDetailDistance - maxDetailDistance);

            if(normalizedDistance < 0.3) {
        // Interpolace mezi maxDetailLevel a midDetailLevel
                float factor = normalizedDistance / 0.5; // Normalizace pro první polovinu
                finalLevelOfTessellation = int(mix(float(maxTessellationLevel), float(midDetailLevel), factor));
            } else {
        // Interpolace mezi midDetailLevel a minDetailLevel
                float factor = (normalizedDistance - 0.5) / 0.5; // Normalizace pro druhou polovinu
                finalLevelOfTessellation = int(mix(float(midDetailLevel), float(minTessellationLevel), factor));
            }
        }

    } else {
        // Použití manuální hodnoty teselace
        finalLevelOfTessellation = levelOfTessellation;
    }

    // Každému vrcholu předá jeho pozici a atributy
    gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
    tessellationUV_out[gl_InvocationID] = tessellationUV[gl_InvocationID];
    vertexColor_out[gl_InvocationID] = vertexColor[gl_InvocationID];

    if(gl_InvocationID == 0) {
        // Nastavení teselace na základě finalLevelOfTessellation
        float tessLevelOuter = float(finalLevelOfTessellation); // Úroveň detailu pro okraje
        float tessLevelInner = float(finalLevelOfTessellation); // Úroveň detailu pro vnitřek

        gl_TessLevelOuter[0] = tessLevelOuter;
        gl_TessLevelOuter[1] = tessLevelOuter;
        gl_TessLevelOuter[2] = tessLevelOuter;
        gl_TessLevelInner[0] = tessLevelInner;
    }
}
