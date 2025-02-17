package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_G;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_ADD;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_KP_SUBTRACT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_T;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.opengl.ARBFramebufferObject.GL_DEPTH_ATTACHMENT;
import static org.lwjgl.opengl.ARBFramebufferObject.GL_FRAMEBUFFER;
import static org.lwjgl.opengl.ARBFramebufferObject.glBindFramebuffer;
import static org.lwjgl.opengl.ARBFramebufferObject.glFramebufferTexture2D;
import static org.lwjgl.opengl.ARBFramebufferObject.glGenFramebuffers;
import static org.lwjgl.opengl.ARBFramebufferObject.glGenerateMipmap;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BORDER_COLOR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterfv;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL40.GL_PATCH_VERTICES;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL40.glPatchParameteri;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.example.objects.Cube;
import com.example.objects.Mesh;
import com.example.objects.TriangleGrid;

public class App {
    private long _window;

    // SHADER PROGRAMS
    private int _shaderProgramDefault;
    private int _shaderProgramSkybox;

    private final ArrayList<Integer> _shaderProgramsEarth = new ArrayList<>();

    // Kamera
    private Camera _camera;

    // Objects
    private final ArrayList<Mesh> _objects = new ArrayList<>();
    private final ArrayList<Mesh> _triangleStripObjects = new ArrayList<>();
    private final ArrayList<Mesh> _planeWaveObjects = new ArrayList<>();

    private Mesh _light;
    private Mesh _light2;

    // Planets
    private Mesh _sun;
    private Mesh _mercury;
    private Mesh _venus;
    private Mesh _earth;
    private Mesh _mars;
    private Mesh _jupiter;
    private Mesh _saturn;
    private Mesh _uranus;
    private Mesh _neptune;

    private Mesh _stars;

    // Draw modes
    private boolean _drawTriangles = true;
    private boolean _drawLines = false;
    private boolean _drawPoints = false;
    private boolean _drawTriangleStrips = true;
    private boolean _useTexture = true;
    private boolean _movingLight = false;
    private boolean _rotatingLight = false;

    private Vector3f _lightDir = new Vector3f(0.0f, -1.0f, 0.0f);
    private float _cutOff = 0.8f;

    // UNIFORMS
    // default
    private int _timeDefaultUniformLocation;

    private ArrayList<Integer> _timeEarthUniformLocations = new ArrayList<>();
    private ArrayList<Integer> _lightPosEarthUniformLocations = new ArrayList<>();

    // skybox
    private int _timeSkyboxUniformLocation;

    private int _shaderMode = 0;
    private int _shaderModeMax;

    private final int _windowHeight = 900;
    private final int _windowWidth = 1400;

    private int _depthMapFBO;
    private int _depthMap;
    private boolean _depthRendering = false;

    private int _shadowResolution = 1024 * 10;

    private int _textureSun;
    private int _textureMercury;
    private int _textureVenus;
    private int _textureEarth;
    private int _textureMars;
    private int _textureJupiter;
    private int _textureSaturn;
    private int _textureUranus;
    private int _textureNeptune;
    private int _textureStars;

    private boolean _restart = false;

    private int _levelOfTessellation = 3;
    private boolean _useAutoLODTessellation = true;

    float _baseSpeed = 0.01f; // Základní rychlost simulace (1 = reálná rychlost)

    public void run() {
        init();
        loop();
        // Po zrušení loopu:

        // Uvolnění bufferů
        for (Mesh mesh : _objects) {
            mesh.cleanup();
        }
        for (Mesh mesh : _triangleStripObjects) {
            mesh.cleanup();
        }

        for (Mesh mesh : _planeWaveObjects) {
            mesh.cleanup();
        }

        // Uvolnění okna a terminace GLFW
        glfwDestroyWindow(_window);
        glfwTerminate();
        if (_restart) {
            new App().run();
        }
    }

    private void init() {
        // Inicializace GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Nelze inicializovat GLFW");
        }

        // Nastavení pro okno
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Skrytí okna

        // Vytvoření okna
        _window = glfwCreateWindow(_windowWidth, _windowHeight,
                "PGRF3 - LWJGL projekt2 - teselace - Minařík Matěj - minarma1@uhk.cz", NULL, NULL);
        if (_window == NULL) {
            throw new IllegalStateException("Nelze vytvořit okno");
        }

        // Centrování okna
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);

            glfwGetWindowSize(_window, pWidth, pHeight);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(_window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2);
        }

        // Inicializace kamery
        _camera = new Camera(
                new Vector3f(-130.0f, 0.0f, -30.0f), // pozice kamery
                new Vector3f(0.0f, 0.0f, -1.0f), // směr kamery
                new Vector3f(0.0f, 1.0f, 0.0f), // up vektor
                1400, 900 // rozměry okna
        );

        glfwMakeContextCurrent(_window); // Nastavení aktivního okna pro GLFW
        glfwSwapInterval(1); // Povolení vertikální synchronizace
        glfwShowWindow(_window); // Zobrazení okna

        GL.createCapabilities(); // Inicializace OpenGL

        setupKeyCallback(); // Nastavení input callbacků

        // Vytvoř framebuffer
        _depthMapFBO = glGenFramebuffers();

        // Vytvoř texturu pro depth map
        _depthMap = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, _depthMap);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, _shadowResolution, _shadowResolution, 0, GL_DEPTH_COMPONENT,
                GL_FLOAT,
                (ByteBuffer) null);

        // Textura depth map
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);

        float[] borderColor = { 1.0f, 1.0f, 1.0f, 1.0f };
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);

        // Připojení depth mapy k framebufferu
        glBindFramebuffer(GL_FRAMEBUFFER, _depthMapFBO);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, _depthMap, 0);
        glDrawBuffer(GL_NONE); // Nezapisuje do barevného bufferu
        glReadBuffer(GL_NONE); // Nečte barevný buffer
        glBindFramebuffer(GL_FRAMEBUFFER, 0);

        try {
            _shaderProgramDefault = createShaderProgram("default");

            _shaderProgramsEarth.add(createShaderProgram("earth", "sphere", "_orthoLight"));
            _shaderProgramsEarth.add(createShaderProgram("earth", "sphere", "default"));
            _shaderProgramsEarth.add(createShaderProgram("earth", "sphere", "_xyzColor"));
            _shaderProgramsEarth.add(createShaderProgram("earth", "sphere", "_normalView"));
            _shaderProgramsEarth.add(createShaderProgram("earth", "sphere", "_depthView"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        glPointSize(2.0f); // Nastaví velikost bodů

        // Získání ID uniform proměnné z shaderu
        _timeDefaultUniformLocation = glGetUniformLocation(_shaderProgramDefault, "time");
        _timeSkyboxUniformLocation = glGetUniformLocation(_shaderProgramSkybox, "time");

        for (int i = 0; i < _shaderProgramsEarth.size(); i++) {

            _timeEarthUniformLocations
                    .add(glGetUniformLocation(_shaderProgramsEarth.get(i), "time"));
            _lightPosEarthUniformLocations.add(glGetUniformLocation(_shaderProgramsEarth.get(i),
                    "lightPos"));
        }

        _shaderModeMax = _shaderProgramsEarth.size() - 1;

        // ============================== OBJEKTY ==============================

        // ============================== PLANETS ==============================

        int rows = 4, cols = 4;
        float step = 65f;
        _light = new Cube(10f, new ArrayList<>(List.of(_shaderProgramDefault)));
        _light.translate(-step * 4.0f, 0f, -35f);
        _light.rotate(10f, 0f, 1f, 0f);

        _light2 = new Cube(10f, new ArrayList<>(List.of(_shaderProgramDefault)));
        _light2.translate(-step * 4.0f, 0f, -35f);
        _light2.rotate(10f, 0f, 1f, 0f);

        _sun = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _sun.translate(-step * 4.0f, 0f, -35f);
        _sun.scale(10f, 10f, 10f);

        _mercury = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _mercury.translate(-step * 3f, 0f, -35f);
        _mercury.scale(0.38f, 0.38f, 0.38f);

        _venus = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _venus.translate(-step * 2.5f, 0f, -35f);
        _venus.scale(0.95f, 0.95f, 0.95f);

        _earth = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _earth.translate(-step * 2f, 0f, -35f);
        _earth.scale(1f, 1f, 1f);

        _mars = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _mars.translate(-step * 1.5f, 0f, -35f);
        _mars.scale(0.53f, 0.53f, 0.53f);

        _jupiter = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _jupiter.translate(-step * 0.5f, 0f, -35f);
        _jupiter.scale(11.21f, 11.21f, 11.21f);

        _saturn = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _saturn.translate(step * 0.8f, 0f, -35f);
        _saturn.scale(9.45f, 9.45f, 9.45f);

        _uranus = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _uranus.translate(step * 1.5f, 0f, -35f);
        _uranus.scale(4.08f, 4.08f, 4.08f);

        _neptune = new TriangleGrid(1, 2, rows, cols, _shaderProgramsEarth);
        _neptune.translate(step * 2f, 0f, -35f);
        _neptune.scale(3.88f, 3.88f, 3.88f);

        _stars = new TriangleGrid(1, 2, 5, 5, _shaderProgramsEarth);
        _stars.scale(300f, 300f, 300f);
        _textureSun = loadTexture("textures/sun.jpg");
        _textureMercury = loadTexture("textures/mercury.jpg");
        _textureVenus = loadTexture("textures/venus.jpg");
        _textureEarth = loadTexture("textures/earth.jpg");
        _textureMars = loadTexture("textures/mars.jpg");
        _textureJupiter = loadTexture("textures/jupiter.jpg");
        _textureSaturn = loadTexture("textures/saturn.jpg");
        _textureUranus = loadTexture("textures/uranus.jpg");
        _textureNeptune = loadTexture("textures/neptune.jpg");

        _textureStars = loadTexture("textures/stars.jpg");

        // glPatchParameteri(GL_PATCH_VERTICES, 4); // Quads
        glPatchParameteri(GL_PATCH_VERTICES, 3); // Triangles
    }

    // Hlavní smyčka
    private void loop() {
        float lastFrameTime = 0.0f;

        while (!glfwWindowShouldClose(_window)) {
            Vector3f lightPos;
            if (_shaderMode >= 5) {
                lightPos = _light2.getTranslation();
            } else {
                lightPos = _light.getTranslation();
            }

            // Delta času
            float currentFrameTime = (float) glfwGetTime();
            float deltaTime = currentFrameTime - lastFrameTime;
            lastFrameTime = currentFrameTime;

            // Zpracování vstupů kamery
            _camera.processInputs(_window, deltaTime);
            _stars.setTranslation(_camera.getPosition());

            // animace slunce
            float pulse = 1f + 0.02f * org.joml.Math.sin(currentFrameTime) / 3f;
            _sun.setScale(10 * pulse, 10f * pulse, 10f * pulse);

            // Parametry orbitálních period (v dnech)
            float mercuryOrbitDays = 88f;
            float venusOrbitDays = 224.7f;
            float earthOrbitDays = 365.25f;
            float marsOrbitDays = 687f;
            float jupiterOrbitDays = 4331f;
            float saturnOrbitDays = 10747f;
            float uranusOrbitDays = 30589f;
            float neptuneOrbitDays = 59800f;

            // Parametry rotačních period (v hodinách)
            float mercuryRotationHours = 1407.5f;
            float venusRotationHours = -5832.5f; // Retrográdní
            float earthRotationHours = 24f;
            float marsRotationHours = 24.6f;
            float jupiterRotationHours = 9.9f;
            float saturnRotationHours = 10.7f;
            float uranusRotationHours = -17.2f; // Retrográdní
            float neptuneRotationHours = 16.1f;

            // Přepočet na rychlost orbitální a rotační rotace (závisí na baseSpeed)
            _mercury.rotate(deltaTime * _baseSpeed * (360f / (mercuryRotationHours / 24f)), 0, 1, 0);
            _venus.rotate(deltaTime * _baseSpeed * (360f / (venusRotationHours / 24f)), 0, 1, 0);
            _earth.rotate(deltaTime * _baseSpeed * (360f / (earthRotationHours / 24f)), 0, 1, 0);
            _mars.rotate(deltaTime * _baseSpeed * (360f / (marsRotationHours / 24f)), 0, 1, 0);
            _jupiter.rotate(deltaTime * _baseSpeed * (360f / (jupiterRotationHours / 24f)), 0, 1, 0);
            _saturn.rotate(deltaTime * _baseSpeed * (360f / (saturnRotationHours / 24f)), 0, 1, 0);
            _uranus.rotate(deltaTime * _baseSpeed * (360f / (uranusRotationHours / 24f)), 0, 1, 0);
            _neptune.rotate(deltaTime * _baseSpeed * (360f / (neptuneRotationHours / 24f)), 0, 1, 0);

            // Parametry rotace kolem Slunce
            Vector3f sunPosition = new Vector3f(-65.0f * 4.0f, 0f, -35f); // Pozice Slunce
            Vector3f rotationAxis = new Vector3f(0f, 1f, 0f); // Rotace kolem osy Y
            float earthOrbitSpeed = 360f / earthOrbitDays; // Rotace Země kolem Slunce

            _mercury.rotateAround(sunPosition, rotationAxis,
                    _baseSpeed * earthOrbitSpeed * (earthOrbitDays / mercuryOrbitDays), deltaTime);
            _venus.rotateAround(sunPosition, rotationAxis,
                    _baseSpeed * earthOrbitSpeed * (earthOrbitDays / venusOrbitDays), deltaTime);
            _earth.rotateAround(sunPosition, rotationAxis, _baseSpeed * earthOrbitSpeed, deltaTime);
            _mars.rotateAround(sunPosition, rotationAxis,
                    _baseSpeed * earthOrbitSpeed * (earthOrbitDays / marsOrbitDays), deltaTime);
            _jupiter.rotateAround(sunPosition, rotationAxis,
                    _baseSpeed * earthOrbitSpeed * (earthOrbitDays / jupiterOrbitDays), deltaTime);
            _saturn.rotateAround(sunPosition, rotationAxis,
                    _baseSpeed * earthOrbitSpeed * (earthOrbitDays / saturnOrbitDays), deltaTime);
            _uranus.rotateAround(sunPosition, rotationAxis,
                    _baseSpeed * earthOrbitSpeed * (earthOrbitDays / uranusOrbitDays), deltaTime);
            _neptune.rotateAround(sunPosition, rotationAxis,
                    _baseSpeed * earthOrbitSpeed * (earthOrbitDays / neptuneOrbitDays), deltaTime);

            _stars.rotate(0.0005f, 1, 1, 0);
            //
            // reflector light animace
            if (_rotatingLight) {
                float oscillation = (float) Math.sin(currentFrameTime) * 0.4f; // jak moc se bude točit
                float angle = currentFrameTime * 8; // jak rychle se bude točit
                float rotx = (float) Math.cos(angle) * oscillation;
                float roty = (float) Math.sin(angle) * oscillation;
                float rotz = (float) Math.cos(angle / 2) * oscillation;

                // rotace světla
                _light2.rotate(rotx, 1, 0, 0);
                _light2.rotate(roty, 0, 1, 0);
                _light2.rotate(rotz, 0, 0, 1);
            }
            if (_movingLight) {
                float oscillation = (float) Math.sin(currentFrameTime) * 0.1f; // jak moc se bude točit
                float angle = currentFrameTime * 3; // jak rychle se bude točit
                float rotx = (float) Math.cos(angle) * oscillation;
                float rotz = (float) Math.cos(angle / 2) * oscillation;

                // rotace světla
                _light2.translate(rotx, 0, 0);
                _light2.translate(0, 0, rotz);
            }

            // 1. Renderování do shadow mapy

            glViewport(0, 0, _shadowResolution, _shadowResolution); // Nastaví viewport pro shadow mapu
            glEnable(GL_DEPTH_TEST); // Povolení hloubkového testu
            glBindFramebuffer(GL_FRAMEBUFFER, _depthMapFBO); // Aktivuj FBO pro shadow mapu
            glClear(GL_DEPTH_BUFFER_BIT); // Vymaž hloubkový buffer

            _depthRendering = true;
            renderScene(currentFrameTime, lightPos, _shaderMode);
            _depthRendering = false;
            glBindFramebuffer(GL_FRAMEBUFFER, 0); // Zpět na defaultní framebuffer

            // 2. Renderování scény
            glClearColor(0.2f, 0.3f, 0.3f, 1.0f); // Nastavení barvy pozadí
            glViewport(0, 0, _windowWidth, _windowHeight); // Nastav viewport na obrazovku
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Vymazání obrazovky
            glEnable(GL_DEPTH_TEST); // Povolení hloubkového testu

            glBindTexture(GL_TEXTURE_2D, _depthMap);
            renderScene(currentFrameTime, lightPos, _shaderMode);

            // Přepínání bufferů
            glfwSwapBuffers(_window);
            glfwPollEvents();
        }

    }

    private void renderScene(float currentFrameTime, Vector3f lightPos, int currentShaderID) {
        // ----------------------------- LIGHT -----------------------------
        glUseProgram(_shaderProgramDefault);
        glUniform1f(_timeSkyboxUniformLocation, currentFrameTime);
        // Nastavení kamery - předání matic do shaderu
        _camera.setCameraMatrixIntoShader(_shaderProgramDefault);
        // Vykreslení objektu
        glPointSize(10f);

        if (_shaderMode >= 5) {
            _light2.draw(GL_TRIANGLES, _shaderProgramDefault);
        } else {
            _light.draw(GL_TRIANGLES, _shaderProgramDefault);
        }

        // ----------------------------- EARTH -----------------------------
        glUseProgram(_shaderProgramsEarth.get(currentShaderID));
        // Pošle uniformy do aktivního shaderu
        glUniform1f(_timeEarthUniformLocations.get(currentShaderID), currentFrameTime);
        glUniform3f(_lightPosEarthUniformLocations.get(currentShaderID), lightPos.x, lightPos.y, lightPos.z);

        // Nastavení kamery - předání matic do shaderu
        _camera.setCameraMatrixIntoShader(_shaderProgramsEarth.get(currentShaderID));

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 10.0f);
        drawMesh(_sun, _shaderProgramsEarth.get(currentShaderID), _textureSun, true);
        // glUniform1i(glGetUniformLocation(currentShaderID, "isSun"), 0);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 0.38f);
        drawMesh(_mercury, _shaderProgramsEarth.get(currentShaderID), _textureMercury);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 0.95f);
        drawMesh(_venus, _shaderProgramsEarth.get(currentShaderID), _textureVenus);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 1f);
        drawMesh(_earth, _shaderProgramsEarth.get(currentShaderID), _textureEarth);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 0.53f);
        drawMesh(_mars, _shaderProgramsEarth.get(currentShaderID), _textureMars);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 11.21f);
        drawMesh(_jupiter, _shaderProgramsEarth.get(currentShaderID), _textureJupiter);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 9.45f);
        drawMesh(_saturn, _shaderProgramsEarth.get(currentShaderID), _textureSaturn);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 4.08f);
        drawMesh(_uranus, _shaderProgramsEarth.get(currentShaderID), _textureUranus);

        glUniform1f(GL20.glGetUniformLocation(_shaderProgramsEarth.get(currentShaderID), "planetScale"), 3.88f);
        drawMesh(_neptune, _shaderProgramsEarth.get(currentShaderID), _textureNeptune);

        _camera.setCameraViewAndProjectionIntoShader(_shaderProgramsEarth.get(currentShaderID));
        drawMesh(_stars, _shaderProgramsEarth.get(currentShaderID), _textureStars, true);
    }

    public int loadTexture(String filePath) {
        int textureID;

        // Použij MemoryStack pro bezpečné řízení paměti
        try (var stack = stackPush()) {
            int[] width = new int[1];
            int[] height = new int[1];
            int[] channels = new int[1];

            // Načti obrázek pomocí STBImage
            ByteBuffer imageData = STBImage.stbi_load(filePath, width, height, channels, 4);
            if (imageData == null) {
                throw new RuntimeException(
                        "Nepodařilo se načíst obrázek: " + filePath + "\n" + STBImage.stbi_failure_reason());
            }

            // Vytvoř OpenGL texturu
            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            // Nahraj obrazová data do textury
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width[0], height[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, imageData);

            // Generuj Mipmapy
            glGenerateMipmap(GL_TEXTURE_2D);

            // Nastav parametry textury
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            // Uvolni paměť načteného obrázku
            STBImage.stbi_image_free(imageData);
        }

        return textureID;
    }

    private void drawMesh(Mesh mesh, int shaderProgramID, int texture, boolean isSun) {
        drawMesh(mesh, shaderProgramID, texture, false, isSun);
    }

    private void drawMesh(Mesh mesh, int shaderProgramID, int texture) {
        drawMesh(mesh, shaderProgramID, texture, false, false);
    }

    private void drawMesh(Mesh mesh, int shaderProgramID, int texture, boolean triangleStrip, boolean isSun) {

        if (isSun) {
            glUniform1i(glGetUniformLocation(shaderProgramID, "isSun"), 1);
        } else {
            glUniform1i(glGetUniformLocation(shaderProgramID, "isSun"), 0);
        }

        // Nastavení tessellace
        glUniform1i(glGetUniformLocation(shaderProgramID, "levelOfTessellation"), _levelOfTessellation);
        glUniform1i(glGetUniformLocation(shaderProgramID, "useAutoLODTessellation"), _useAutoLODTessellation ? 1 : 0);

        // Poslání textury do shaderu

        glUniform1i(glGetUniformLocation(shaderProgramID, "textureSampler"), 0); // Textura na jednotce 0
        if (_useTexture) {
            glUniform1i(glGetUniformLocation(shaderProgramID, "useTexture"), 1); // UseTexture
        } else {
            glUniform1i(glGetUniformLocation(shaderProgramID, "useTexture"), 0); // UseTexture
        }

        glActiveTexture(GL_TEXTURE0); // Aktivace texturové jednotky 0
        glBindTexture(GL_TEXTURE_2D, texture); // Vázání textury

        // Světlo
        if (_depthRendering) {
            glUniform1i(GL20.glGetUniformLocation(shaderProgramID, "depthRendering"), 1);
        } else {
            glUniform1i(GL20.glGetUniformLocation(shaderProgramID, "depthRendering"), 0);
        }

        // Matrix4f lightProjection = new Matrix4f().perspective((float)
        // Math.toRadians(70), (float) 1024 / 1024, 0.1f, 100.0f);
        if (_shaderMode == 6) {
            _lightDir = _light2.getRotation().normalize();
            glUniform3f(GL20.glGetUniformLocation(shaderProgramID, "lightDir"), _lightDir.x, _lightDir.y, _lightDir.z);
            glUniform1f(GL20.glGetUniformLocation(shaderProgramID, "cutOff"), _cutOff);
        }

        // pro _orthoLight
        if (_shaderMode == 4) {
            Matrix4f lightProjection = new Matrix4f().ortho(-100.0f, 100.0f, -100.0f, 100.0f, 0.1f, 300.0f);
            Matrix4f lightView = new Matrix4f().lookAt(_light.getTranslation(), new Vector3f(0.0f),
                    new Vector3f(0.0f, 1.0f, 0.0f));
            Matrix4f lightSpaceMatrix = lightProjection.mul(lightView);
            float[] matrixData = new float[16];
            lightSpaceMatrix.get(matrixData);
            int lightSpaceMatrixUniformLocation = GL20.glGetUniformLocation(shaderProgramID, "lightSpaceMatrix");
            glUniformMatrix4fv(lightSpaceMatrixUniformLocation, false, matrixData);
        } else {
            // Vytvoření perspektivní projekční matice
            Matrix4f lightProjection = new Matrix4f().perspective(
                    (float) Math.toRadians(140.0f), // Široké zorné pole
                    1.0f, // Poměr stran, 1.0f (čtvercová mapa stínů)
                    0.5f, // Blízká rovina ořezu
                    300.0f // Vzdálená rovina ořezu
            );

            // Vytvoření matice pohledu s orientací dolů
            Vector3f vectorDownFromLight = new Vector3f(
                    _light.getTranslation().x,
                    -10.0f, // Cíl přímo pod světlem
                    _light.getTranslation().z);

            Matrix4f lightView = new Matrix4f().lookAt(
                    _light.getTranslation(), // Pozice světla
                    vectorDownFromLight, // Cíl světla (dolů)
                    new Vector3f(0.0f, 0.0f, -1.0f) // "Up vector" kolmo k pohledu dolů
            );

            // Spojení projekce a pohledu
            Matrix4f lightSpaceMatrix = lightProjection.mul(lightView);

            // Zaslání matice na GPU
            float[] matrixData = new float[16];
            lightSpaceMatrix.get(matrixData);
            int lightSpaceMatrixUniformLocation = GL20.glGetUniformLocation(shaderProgramID, "lightSpaceMatrix");
            glUniformMatrix4fv(lightSpaceMatrixUniformLocation, false, matrixData);

        }

        glActiveTexture(GL_TEXTURE1);
        glBindTexture(GL_TEXTURE_2D, _depthMapFBO);

        glUniform1i(glGetUniformLocation(shaderProgramID, "shadowMap"), 1);

        if (_drawLines) {
            mesh.draw(GL_LINE, shaderProgramID);
        } // Pro vykreslení hran
        else {
            mesh.draw(GL_FILL, shaderProgramID);
        } // Pro vykreslení bodů
    }

    // Nastaví key callbacky
    private void setupKeyCallback() {
        glfwSetKeyCallback(_window, new GLFWKeyCallbackI() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, true); // Zavře okno
                    _restart = false;
                } else if (key == GLFW_KEY_R && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, true);
                    _restart = true; // Restart aplikace
                }

                if (key == GLFW_KEY_T && action == GLFW_PRESS) {
                    _useTexture = !_useTexture;
                    glfwSetWindowTitle(_window, "Textures: " + ( _useTexture ? "ON" : "OFF") + "(use T to switch)");
                }
                if (key == GLFW_KEY_Q && action == GLFW_PRESS) {
                    if (_useAutoLODTessellation){
                        return;
                    }
                    if (_levelOfTessellation == 1) {
                        return;
                    }
                    _levelOfTessellation -= 1;
                    glfwSetWindowTitle(_window, "Level of tessellation: " + _levelOfTessellation + " (use Q or E keys to adjust level of tessellation)");
                }
                if (key == GLFW_KEY_E && action == GLFW_PRESS) {
                    if (_useAutoLODTessellation){
                        return;
                    }
                    _levelOfTessellation += 1;
                    glfwSetWindowTitle(_window, "Level of tessellation: " + _levelOfTessellation + " (use Q or E keys to adjust level of tessellation)");
                }
                if (key == GLFW_KEY_X && action == GLFW_PRESS) {
                    changeShaderMode(+1);
                }
                if (key == GLFW_KEY_Z && action == GLFW_PRESS) {
                    changeShaderMode(-1);
                }
                if (key == GLFW_KEY_F && action == GLFW_PRESS) {
                    _drawLines = !_drawLines;
                    glfwSetWindowTitle(_window, "Polygon draw mode: " +( _drawLines ? "LINES" : "FILL") + " (use F to switch)");
                }
                if (key == GLFW_KEY_G && action == GLFW_PRESS) {
                    _useAutoLODTessellation = !_useAutoLODTessellation;
                    glfwSetWindowTitle(_window, "Auto LOD Tessellation: " + (_useAutoLODTessellation ? "ON" : "OFF") + " (use G to switch), (use Q or E keys to adjust level of tessellation)");
                } else {
                }
                if (key == GLFW_KEY_KP_ADD && action == GLFW_PRESS) {
                    _baseSpeed *= 2;
                    glfwSetWindowTitle(_window, "Simulation speed: " + _baseSpeed + " (use + or - adjust speed)");
                }
                if (key == GLFW_KEY_KP_SUBTRACT && action == GLFW_PRESS) {
                    _baseSpeed /= 2;
                    glfwSetWindowTitle(_window, "Simulation speed: " + _baseSpeed + " (use + or - adjust speed)");
                }
            }
        });
    }

    private void changeShaderMode(int how) {
        int newMode = _shaderMode + how;
        if (newMode < 0 || newMode > _shaderModeMax) {
            return;
        }
        _shaderMode += how;
        String shaderMode;
        switch (_shaderMode) {
            case 0:
                shaderMode = "Light";
                break;
            case 1:
                shaderMode = "Default";
                break;
            case 2:
                shaderMode = "xyzColor";
                break;
            case 3:
                shaderMode = "normalView";
                break;
            case 4:
                shaderMode = "depthView";
                break;
            default:
                shaderMode = "error";
                break;
        }

        glfwSetWindowTitle(_window, "Shader mode: " + shaderMode + " (use X and Z keys to change mode)");
    }

    // Vytvoří a vrátí shader program z shader souborů
    private int createShaderProgram(String vertShaderName, String tessellationShaderName, String fragShaderName)
            throws IOException {
        int vertexShader = loadShaderFromPath("shaders/" + vertShaderName + ".vert", GL_VERTEX_SHADER);
        int tesselationShaderTesc = tessellationShaderName != null
                ? loadShaderFromPath("shaders/" + tessellationShaderName + ".tesc", GL_TESS_CONTROL_SHADER)
                : 0;
        int tesselationShaderTese = tessellationShaderName != null
                ? loadShaderFromPath("shaders/" + tessellationShaderName + ".tese", GL_TESS_EVALUATION_SHADER)
                : 0;
        int fragmentShader = loadShaderFromPath("shaders/" + fragShaderName + ".frag", GL_FRAGMENT_SHADER);

        int shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        if (tessellationShaderName != null) {
            glAttachShader(shaderProgram, tesselationShaderTesc);
            glAttachShader(shaderProgram, tesselationShaderTese);
        }
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        glDeleteShader(vertexShader);
        if (tessellationShaderName != null) {
            glDeleteShader(tesselationShaderTesc);
            glDeleteShader(tesselationShaderTese);
        }
        glDeleteShader(fragmentShader);

        return shaderProgram;
    }

    private int createShaderProgram(String shaderName) throws IOException {
        return createShaderProgram(shaderName, null, shaderName);
    }

    private int createShaderProgram(String vertShaderName, String fragShaderName) throws IOException {
        return createShaderProgram(vertShaderName, null, fragShaderName);
    }

    // Načtení shaderu ze souboru
    private int loadShaderFromPath(String filePath, int type) throws IOException {
        // Čte a ukládá zdrojový kód souboru shaderu řádek po řádku
        StringBuilder shaderSource = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        }

        // Nastaví do shaderu zdrojový kód a zkompiluje ho
        int shader = glCreateShader(type);
        glShaderSource(shader, shaderSource);
        glCompileShader(shader);

        // Exception při chybné kompilaci
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Failed to compile shader: " + glGetShaderInfoLog(shader));
        }

        // Vrací ID shaderu
        return shader;
    }

}
