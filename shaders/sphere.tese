#version 460 core

layout(triangles, equal_spacing, ccw) in;

in vec2 tessellationUV_out[];  // UV přijaté z tesc shaderu
in vec3 vertexColor_out[];     // Barva přijatá z tesc shaderu

out vec3 fragColor;       // Výstupní barva
out vec3 fragPosition;    // Výstupní pozice
out vec3 fragNormal;      // Výstupní normála
out vec2 fragTexCoords;   // Výstupní texturové souřadnice

uniform mat4 camMatrix;        // Kamera
uniform mat4 modelMatrix;      // Modelová matice
uniform mat4 lightSpaceMatrix; // Matice pro stíny
uniform bool depthRendering;   // Stínový rendering

// Výpočet pozice na kouli
vec3 calculatePos(float u, float v) {
    float r = 3.0; // Poloměr koule

    // Úhly
    float angle1 = u * 4.0 * 3.1415926; // Azimutální úhel
    float angle2 = v * 3.1415926;       // Zenitální úhel

    // Výpočet pozice vrcholu
    float x = r * sin(angle2) * cos(angle1);
    float z = r * sin(angle2) * sin(angle1);
    float y = r * cos(angle2);

    vec3 transformedPosition = vec3(x, y, z);
    return (modelMatrix * vec4(transformedPosition, 1.0)).xyz;
}

void main() {
    // Barycentrické souřadnice
    float u = gl_TessCoord.x;
    float v = gl_TessCoord.y;
    float w = gl_TessCoord.z;

    // Interpolace UV souřadnic
    vec2 texCoord = u * tessellationUV_out[0] + v * tessellationUV_out[1] + w * tessellationUV_out[2];
    vec3 interpolatedColor = u * vertexColor_out[0] + v * vertexColor_out[1] + w * vertexColor_out[2];

    // Výpočet pozice vrcholu na kouli
    vec3 finalPosition = calculatePos(texCoord.x, texCoord.y);

    // Výpočet normály
    float smallvalue = 0.01; // Malý posun pro derivace
    vec3 neighbour1 = calculatePos(texCoord.x + smallvalue, texCoord.y);
    vec3 neighbour2 = calculatePos(texCoord.x, texCoord.y + smallvalue);
    vec3 tangent = neighbour1 - finalPosition;
    vec3 bitangent = neighbour2 - finalPosition;
    vec3 displacedNormal = normalize(cross(tangent, bitangent));

    // Výstupní pozice pro stíny nebo kameru
    if (depthRendering) {
        gl_Position = lightSpaceMatrix * vec4(finalPosition, 1.0);
    } else {
        gl_Position = camMatrix * vec4(finalPosition, 1.0);
    }

    // Předání hodnot fragment shaderu
    fragColor = interpolatedColor;
    fragPosition = finalPosition;
    fragNormal = displacedNormal;
    fragTexCoords = vec2(-texCoord.x*2, texCoord.y);
}
