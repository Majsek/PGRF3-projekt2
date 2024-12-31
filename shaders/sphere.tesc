#version 460 core

layout(vertices = 3) out; // Teselujeme trojúhelníky

in vec2 tessellationUV[];  // Přijaté UV z vertex shaderu
in vec3 vertexColor[];     // Přijatá barva z vertex shaderu

out vec2 tessellationUV_out[];  // Posíláme dál
out vec3 vertexColor_out[];     // Posíláme dál

uniform int levelOfTessellation;   // Úroveň tessellace

void main() {
    // Každému vrcholu předej jeho pozici a atributy
    gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
    tessellationUV_out[gl_InvocationID] = tessellationUV[gl_InvocationID];
    vertexColor_out[gl_InvocationID] = vertexColor[gl_InvocationID];

    // Nastavení úrovně teselace (lze dynamicky měnit)
    if (gl_InvocationID == 0) {
        float tessLevelOuter = levelOfTessellation; // Úroveň detailu pro okraje
        float tessLevelInner = levelOfTessellation; // Úroveň detailu pro vnitřek

        gl_TessLevelOuter[0] = tessLevelOuter;
        gl_TessLevelOuter[1] = tessLevelOuter;
        gl_TessLevelOuter[2] = tessLevelOuter;
        gl_TessLevelInner[0] = tessLevelInner;
    }
}
