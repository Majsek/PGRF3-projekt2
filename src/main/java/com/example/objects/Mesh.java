package com.example.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL40.GL_PATCHES;

import com.example.IBO;
import com.example.VBO;

public abstract class Mesh {
    private Map<Integer, Integer> _uniformModelMatrixLocations = new HashMap<>();

    private VBO _vbo;
    private IBO _ibo;

    private float[] _vertices;
    private int[] _indices;

    private Matrix4f _modelMatrix; // Modelová matice

    public Mesh(float[] vertices, int[] indices, List<Integer> shaderProgramIDs) {
        _vertices = vertices;
        _indices = indices;
        _vbo = new VBO(_vertices);
        _ibo = new IBO(_indices);

        // Inicializace modelové matice (jednotkové)
        _modelMatrix = new Matrix4f().identity();

        // Pro každý shader program získáme lokaci pro modelovou matici
        for (int shaderProgramID : shaderProgramIDs) {
            int modelMatrixLocation = GL20.glGetUniformLocation(shaderProgramID, "modelMatrix");
            _uniformModelMatrixLocations.put(shaderProgramID, modelMatrixLocation);
        }

    }

    public void draw(int drawMode, int shaderProgramID) {
        _vbo.bindVBOAndSetupAttributes();
        _ibo.bindIBO();

        // Pošleme modelovou matici do shaderu
        float[] matrixData = new float[16];
        _modelMatrix.get(matrixData); // Získáme hodnoty matice jako pole
        GL20.glUniformMatrix4fv(_uniformModelMatrixLocations.get(shaderProgramID), false, matrixData);
        // wireframe
        // glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glPolygonMode(GL_FRONT_AND_BACK, drawMode);
        // glDisable(GL_CULL_FACE);
        // //glEnable(GL_CULL_FACE);

        // glDrawElements(drawMode, _indices.length, GL_UNSIGNED_INT, 0);
        glDrawElements(GL_PATCHES, _indices.length, GL_UNSIGNED_INT, 0);
    }

    // translation
    public void translate(float x, float y, float z) {
        _modelMatrix.translate(x, y, z);
    }

    public void setTranslation(Vector3f translation) {
        _modelMatrix.m30(translation.x); // nastaví prvek [3][0]
        _modelMatrix.m31(translation.y); // nastaví prvek [3][1]
        _modelMatrix.m32(translation.z); // nastaví prvek [3][2]
    }

    // rotation
    public void rotate(float angle, float x, float y, float z) {
        _modelMatrix.rotate((float) Math.toRadians(angle), x, y, z);
    }

    // scale
    public void scale(float x, float y, float z) {
        _modelMatrix.scale(x, y, z);
    }

    public void setScale(float x, float y, float z) {
        _modelMatrix.m00(x); // nastaví prvek [0][0]
        _modelMatrix.m11(y); // nastaví prvek [1][1]
        _modelMatrix.m22(z); // nastaví prvek [2][2]
    }

    public void cleanup() {
        _vbo.delete();
        _ibo.delete();
    }

    public Vector3f getTranslation() {
        float x = _modelMatrix.m30(); // prvek [3][0]
        float y = _modelMatrix.m31(); // prvek [3][1]
        float z = _modelMatrix.m32(); // prvek [3][2]

        return new Vector3f(x, y, z);
    }

    public Vector3f getRotation() {
        float x = _modelMatrix.m10(); // prvek [1][0]
        float y = _modelMatrix.m11(); // prvek [1][1]
        float z = _modelMatrix.m12(); // prvek [1][2]

        return new Vector3f(x, y, z);
    }

    public void rotateAround(Vector3f center, Vector3f axis, float speed, float deltaTime) {
        // Vypočítáme úhel rotace za aktuální snímek
        float angle = deltaTime * speed;
    
        Matrix4f rotationMatrix = new Matrix4f()
            .translate(center) // Překlad do středu rotace
            .rotate((float) Math.toRadians(angle), axis) // Rotace kolem osy
            .translate(new Vector3f(center).negate()); // Překlad zpět
    
        // Kombinujeme výslednou rotaci s existující modelovou maticí
        _modelMatrix = rotationMatrix.mul(_modelMatrix);
    }

}
