#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;   

out vec3 fragColor;
out vec3 fragPosition;
out vec3 fragNormal;
out vec2 fragTexCoords;

uniform mat4 camMatrix;
uniform mat4 modelMatrix;

uniform mat4 lightSpaceMatrix = mat4(1.0);
uniform bool depthRendering = false;


out vec4 fragLightSpacePos;

uniform float time;
uniform bool useTexture;

vec3 calculatePos(float u, float v) {
    // Výpočet pozice vrcholu na torusu
    float x = u * 4;
    float y = v * 3;
    float z = 0.5 * cos(sqrt(20.0 * x * x + 20.0 * y * y)) * 0.8;

    // Vlnění
    float wave = sin(z * 5.0 + time) * 0.2; // 10.0 je frekvence vlny, 0.1 je amplituda
    vec3 transformedPosition = vec3(x, y, z + wave);

    vec4 point = vec4(transformedPosition.x, transformedPosition.y, transformedPosition.z, 1.0);
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
        fragColor = vec3(0.2 + finalPosition.z, 0.4 + finalPosition.z * 0.5, 0.6 + finalPosition.z);
    }

    fragPosition = finalPosition;
    fragNormal = displacedNormal;
    fragTexCoords = vec2(-position.x - 1.40, -position.y + 1.45); // Předání texturových souřadnic

    fragLightSpacePos = lightSpaceMatrix * vec4(finalPosition, 1.0);
}
