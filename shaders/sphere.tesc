// #version 330 core

// layout(triangles) in; // Vstupní primitiva: trojúhelníky
// layout(triangle_strip, max_vertices = 64) out; // Výstupní primitiva: trojúhelníkový pás

// in vec3 fragColor[]; // Barvy z vertex shaderu
// in vec3 fragPosition[]; 
// in vec3 fragNormal[];
// in vec2 fragTexCoords[];
// in vec4 fragLightSpacePos[];

// out vec3 gColor; 
// out vec3 gPosition;
// out vec3 gNormal;
// out vec2 gTexCoords;
// out vec4 gLightSpacePos;

// uniform int tessellationLevel = 1; // Úroveň teselace

// // Barycentrická interpolace mezi třemi body
// vec3 barycentricInterpolate(vec3 v0, vec3 v1, vec3 v2, float u, float v, float w) {
//     return v0 * u + v1 * v + v2 * w;
// }

// vec2 barycentricInterpolate(vec2 v0, vec2 v1, vec2 v2, float u, float v, float w) {
//     return v0 * u + v1 * v + v2 * w;
// }

// vec4 barycentricInterpolate(vec4 v0, vec4 v1, vec4 v2, float u, float v, float w) {
//     return v0 * u + v1 * v + v2 * w;
// }

// void main() {
//     for (int i = 0; i < 30; ++i) {
//         gl_Position = gl_in[i].gl_Position;
//         gColor = fragColor[0]; // Pevná barva z prvního vrcholu
//         EmitVertex();
//         gTexCoords = fragTexCoords;
//     }
//     EndPrimitive();
// }

// const int MIN_TES = 2;
// const int MAX_TES = 16;
// const float MIN_DIST = 0.0;
// const float MAX_DIST = 1.5;

#version 460 core

// Quads
layout (vertices = 4) out;

in vec2 fragTexCoords[];
out vec2 uvsCoord[];


void main()
{
    gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
    uvsCoord[gl_InvocationID] = fragTexCoords[gl_InvocationID];

    if (gl_InvocationID == 0)
    {
        int tesselationLevel = 2;

        int tes0 = tesselationLevel;
        int tes1 = tesselationLevel;
        int tes2 = tesselationLevel;
        int tes3 = tesselationLevel;

        gl_TessLevelOuter[0] = tes0; // left for quads
        gl_TessLevelOuter[1] = tes1; // bot for quads
        gl_TessLevelOuter[2] = tes2; // right for quads
        gl_TessLevelOuter[3] = tes3; // top for quads
        
        gl_TessLevelInner[0] = max(tes1, tes3); // top bot for quads
        gl_TessLevelInner[1] = max(tes0, tes2); // left right for quads
    }
}


// Triangles
//layout (vertices = 3) out;
//
//in vec2 uvs[];
//out vec2 uvsCoord[];
//
//void main()
//{
//    gl_out[gl_InvocationID].gl_Position = gl_in[gl_InvocationID].gl_Position;
//    uvsCoord[gl_InvocationID] = uvs[gl_InvocationID];
//
//    gl_TessLevelOuter[0] = 2; // left for triangles
//    gl_TessLevelOuter[1] = 2; // bot for triangles
//    gl_TessLevelOuter[2] = 2; // right for triangles
//        
//    gl_TessLevelInner[0] = 4; // all inner sides for triangles
//}