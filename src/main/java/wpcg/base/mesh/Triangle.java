package wpcg.base.mesh;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import wpcg.base.Logger;

/**
 * Representation of a triangle consisting of three indices. The indices
 * reference vertices in the vertex list in a triangle mesh.
 *
 * @author Philipp Jenke
 */
public class Triangle {

    /**
     * Indices of the vertices.
     */
    private int[] vertexIndices = {-1, -1, -1};

    /**
     * Triangle color in RGBA format.
     */
    protected ColorRGBA color = new ColorRGBA(0.5f, 0.5f, 0.5f, 1);

    /**
     * Facet normal
     */
    protected Vector3f normal = new Vector3f(0, 1, 0);

    /**
     * Indices of the texture coordinates.
     */
    protected int[] texCoordIndices = {-1, -1, -1};

    public Triangle() {
    }

    public Triangle(int a, int b, int c, int tA, int tB, int tC, Vector3f normal, ColorRGBA color) {
        vertexIndices[0] = a;
        vertexIndices[1] = b;
        vertexIndices[2] = c;

        if (a == b || b == c || a == c) {
            Logger.getInstance().error("Invalid triangle generated.");
        }

        texCoordIndices[0] = tA;
        texCoordIndices[1] = tB;
        texCoordIndices[2] = tC;
        this.color = new ColorRGBA(color);
        this.normal = new Vector3f(normal);
    }

    public Triangle(int a, int b, int c) {
        this(a, b, c,
                -1, -1, -1,
                new Vector3f(1, 0, 0),
                new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
    }

    public Triangle(int a, int b, int c, int tA, int tB, int tC) {
        this(a, b, c,
                tA, tB, tC,
                new Vector3f(1, 0, 0),
                new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
    }


    public Triangle(Triangle triangle) {
        this(triangle.vertexIndices[0], triangle.vertexIndices[1], triangle.vertexIndices[2],
                triangle.texCoordIndices[0], triangle.texCoordIndices[1], triangle.texCoordIndices[2],
                triangle.normal, triangle.color);
    }

    /**
     * Compute and return the area of the triangle.
     */
    public static double getArea(Vector3f a, Vector3f b, Vector3f c) {
        return b.subtract(a).cross(c.subtract(a)).length();
    }

    public ColorRGBA getColor() {
        return color;
    }

    /**
     * Color must be a 4D vector in RGBA format.
     */
    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    public void setNormal(Vector3f normal) {
        this.normal = new Vector3f(normal);
    }

    public Vector3f getNormal() {
        return normal;
    }

    /**
     * i must be in 0, 1, 2
     */
    public int getTexCoordIndex(int i) {
        return texCoordIndices[i];
    }

    /**
     * Add an offset to all texture coordinates.
     */
    public void addTexCoordOffset(int offset) {
        for (int i = 0; i < 3; i++) {
            texCoordIndices[i] += offset;
        }
    }

    /**
     * Set the three texture coordinates at the three triangle corners.
     */
    public void setTextureCoordinates(int texCoordIndex1, int texCoordIndex2, int texCoordIndex3) {
        this.texCoordIndices[0] = texCoordIndex1;
        this.texCoordIndices[1] = texCoordIndex2;
        this.texCoordIndices[2] = texCoordIndex3;
    }

    @Override
    public String toString() {
        return String.format("Triangle");
    }

    public int getVertexIndex(int index) {
        return vertexIndices[index];
    }

    /**
     * Add an offset to all vertex indices.
     */
    public void addVertexIndexOffset(int offset) {
        for (int i = 0; i < 3; i++) {
            vertexIndices[i] += offset;
        }
    }

    @Override
    public Triangle clone() {
        Triangle triangle = new Triangle();
        triangle.setVertexIndices(vertexIndices[0], vertexIndices[1], vertexIndices[2]);
        triangle.setTextureCoordinates(texCoordIndices[0], texCoordIndices[1], texCoordIndices[2]);
        triangle.setColor(color);
        triangle.setNormal(normal);
        return triangle;
    }

    public void setVertexIndices(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
        this.vertexIndices[0] = vertexIndex1;
        this.vertexIndices[1] = vertexIndex2;
        this.vertexIndices[2] = vertexIndex3;

    }

    public int getTextureCoordinate(int vertexInTriangleIndex) {
        return texCoordIndices[vertexInTriangleIndex];
    }

    public int get(int vertexInTriangleIndex) {
        return vertexIndices[vertexInTriangleIndex];
    }

    public int getOther(int a, int b) {
        if (!contains(a) || !contains(b)) {
            throw new IllegalArgumentException("Invalid indices.");
        }

        for (int i = 0; i < 3; i++) {
            if (vertexIndices[i] != a && vertexIndices[i] != b) {
                return vertexIndices[i];
            }
        }

        throw new IllegalArgumentException("Invalid indices.");
    }

    public boolean contains(int index) {
        for (int i = 0; i < 3; i++) {
            if (vertexIndices[i] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the triangle has a duplicated vertex index.
     */
    public boolean isDegenerated() {
        return vertexIndices[0] == vertexIndices[1] ||
                vertexIndices[1] == vertexIndices[2] ||
                vertexIndices[2] == vertexIndices[0];
    }

    /**
     * Replace vertex index iReplace by iKeep.
     */
    public void replaceVertexIndex(int iKeep, int iReplace) {
        for (int i = 0; i < 3; i++) {
            if (vertexIndices[i] == iReplace) {
                vertexIndices[i] = iKeep;
            }
        }
    }

    public int getA() {
        return vertexIndices[0];
    }

    public int getB() {
        return vertexIndices[1];
    }

    public int getC() {
        return vertexIndices[2];
    }
}
