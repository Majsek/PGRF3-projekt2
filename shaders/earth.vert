#version 330 core

layout(location = 0) in vec3 position; // Pozice vrcholu
layout(location = 1) in vec3 color;    // Barva vrcholu

out vec3 fragColor;      
out vec3 fragPosition;   
out vec3 fragNormal;    
out vec2 fragTexCoords;  
out vec4 fragLightSpacePos;

uniform mat4 camMatrix;
uniform mat4 modelMatrix;
uniform mat4 lightSpaceMatrix = mat4(1.0);
uniform bool depthRendering = false;
uniform bool useTexture = false; 
uniform float time;

vec3 calculatePos(float u, float v) {
    float r = 3.0; // Poloměr koule

    // Úhly
    float angle1 = u * 4.0 * 3.1415926; // Azimutální úhel
    // Azimutální úhel (0 až 2π)
    float angle2 = v * 3.1415926;       // Zenitální úhel (0 až π)

    // Výpočet pozice vrcholu
    float x = r * sin(angle2) * cos(angle1);
    float z = r * sin(angle2) * sin(angle1);
    float y = r * cos(angle2);

    vec3 transformedPosition = vec3(x, y, z);

    vec4 point = vec4(transformedPosition.x, transformedPosition.y, transformedPosition.z, 1.0);
    vec4 transformedPoint = modelMatrix * point;

    return transformedPoint.xyz;
}

void main() {
    // Normalizace u a v na rozsah [0.0, 1.0]
    float u = (position.x + 1.0) * 0.5;
    float v = (position.y + 1.0) * 0.5;

    // Výpočet pozice vrcholu
    vec3 finalPosition = calculatePos(u, v);
    float smallvalue = 0.01; // Malý posun pro výpočet derivací
    vec3 neighbour1 = calculatePos(u + smallvalue, v);
    vec3 neighbour2 = calculatePos(u, v + smallvalue);

    vec3 tangent = neighbour1 - finalPosition;
    vec3 bitangent = neighbour2 - finalPosition;
    vec3 displacedNormal = cross(tangent, bitangent);

    // Výstupní pozice pro stíny nebo kameru
    if(depthRendering) {
        gl_Position = lightSpaceMatrix * vec4(finalPosition, 1.0);
    } else {
        gl_Position = camMatrix * vec4(finalPosition, 1.0);
    }

    if(useTexture) {
        fragColor = vec3(1.0);
    } else {
        fragColor = vec3(0.29, 0.1, 0.6);
    }

    fragPosition = finalPosition;
    fragNormal = displacedNormal;
    fragTexCoords = vec2(-u * 2, v);
    fragLightSpacePos = lightSpaceMatrix * vec4(finalPosition, 1.0);
}
