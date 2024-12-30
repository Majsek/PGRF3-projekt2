#version 330 core

layout(location = 0) in vec3 position; // Pozice vrcholu
layout(location = 1) in vec3 color;    // Barva vrcholu

out vec3 fragColor; // Barva, která se pošle do fragment shaderu
out vec3 fragPosition;
out vec3 fragNormal;
uniform mat4 camMatrix;
uniform mat4 modelMatrix;

out vec2 fragTexCoords; // Přenos do fragment shaderu
uniform bool useTexture;

uniform mat4 lightSpaceMatrix = mat4(1.0);
uniform bool depthRendering = false;
out vec4 fragLightSpacePos;

vec3 calculatePos(float u, float v) {
    float x = u;
    float y = v;
    float z = 0;

    vec4 point = vec4(x, y, z, 1.0);
    vec4 transformedPoint = modelMatrix * point;

    return transformedPoint.xyz;
}

void main() {
    float u = position.x;
    float v = position.y;

    vec3 finalPosition = calculatePos(u, v);
    float smallvalue = 0.01; // Malý posun pro výpočet derivací
    vec3 neighbour1 = calculatePos(u + smallvalue, v);
    vec3 neighbour2 = calculatePos(u, v + smallvalue);

    vec3 tangent = neighbour1 - finalPosition;
    vec3 bitangent = neighbour2 - finalPosition;
    vec3 displacedNormal = cross(tangent, bitangent);

    if(depthRendering) {
        gl_Position = lightSpaceMatrix * vec4(finalPosition, 1.0); // Nastavení pozice
    } else {
        gl_Position = camMatrix * vec4(finalPosition, 1.0); // Nastavení pozice
    }

    if(useTexture) {
        fragColor = vec3(1, 1, 1);
    } else {
        fragColor = vec3(0.11, 0.55, 0.09);
    }

    fragPosition = finalPosition;
    fragNormal = displacedNormal;

    fragTexCoords = vec2(-position.x / 4, position.y / 4); // Předání texturových souřadnic

    fragLightSpacePos = lightSpaceMatrix * vec4(finalPosition, 1.0);
}
