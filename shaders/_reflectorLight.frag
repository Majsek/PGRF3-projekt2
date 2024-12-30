#version 330 core

in vec3 fragNormal;   // Normála z vertex shaderu
in vec3 fragPosition; // Pozice vrcholu z vertex shaderu
in vec3 fragColor;
in vec4 fragLightSpacePos;
in vec2 fragTexCoords;

out vec4 color;

uniform sampler2D textureSampler;
uniform bool useTexture = false;
uniform float time;
uniform vec3 viewPos;

// Uniformy pro světlo a kameru
uniform vec3 lightPos; // Pozice světelného zdroje
uniform vec3 lightColor = vec3(1.0, 1.0, 1.0);    // Barva světla
uniform vec3 lightDir = vec3(0.0, -1.0, 1.0); // Směr reflektorového světla dolů
uniform float cutOff = 0.8;  // Mezní úhel reflektoru

uniform sampler2D shadowMap;

float ShadowCalculation(vec3 lightDir) {
    // Normalizace normály
    vec3 normal = normalize(fragNormal);
    // Konverze do NDC souřadnic (Normalized Device Coordinates)
    vec3 projCoords = fragLightSpacePos.xyz / fragLightSpacePos.w;
    projCoords = projCoords * 0.5 + 0.5;

    // Získání nejbližší hloubky z depth mapy (shadow map)
    float closestDepth = texture(shadowMap, projCoords.xy).r;

    // Aktuální hloubka fragmentu
    float currentDepth = projCoords.z;

    // Rozhodnutí, zda je fragment ve stínu
    float bias = max(0.05 * (1.0 - dot(normal, lightDir)), 0.005);
    float shadow = currentDepth - bias > closestDepth ? 1.0 : 0.0;

    return shadow;
}

void main() {

    vec3 normal = normalize(fragNormal);

    // Výpočet směru ke světlu
    vec3 lightDirToFrag = normalize(lightPos - fragPosition);

    // Kontrola úhlu reflektoru
    float theta = dot(lightDirToFrag, normalize(-lightDir));
    float epsilon = 0.05; // Mírné změkčení okraje
    float intensity = clamp((theta - cutOff) / epsilon, 0.0, 1.0);

    // Lambertův zákon - difúzní osvětlení
    float diff = max(dot(normal, lightDirToFrag), 0.0);

    // Barva objektu kombinovaná s difúzním osvětlením
    vec3 diffuse = diff * lightColor * fragColor;

    // Ambientní složka
    vec3 ambient = 0.1 * fragColor;

    // Výpočet spekulární složky
    vec3 viewDir = normalize(viewPos - fragPosition); // Směr k pozici kamery
    vec3 reflectDir = reflect(-lightDirToFrag, normal); // Odražený směr světla

    // Vypočítání spekulární složky (jiskry v oku)
    float shininess = 32.0;
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), shininess);
    vec3 specular = spec * lightColor; // Vynásobení barvou světla

    vec3 lighting = (ambient + intensity * (1.0 - ShadowCalculation(lightDirToFrag))) * (diffuse + specular);

    vec3 result = ambient + lighting;

    vec4 textureColor;
    if(useTexture) {
        textureColor = texture(textureSampler, fragTexCoords); // Vzorkování textury
    } else {
        textureColor = vec4(1, 1, 1, 1);
    }

    color = textureColor * vec4(result, 1.0);
}
