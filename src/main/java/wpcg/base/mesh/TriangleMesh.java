
package wpcg.base.mesh;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import wpcg.base.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a indexed vertex list triangle mesh.
 */
public class TriangleMesh {

    /**
     * Vertices.
     */
    private List<Vertex> vertices = new ArrayList<Vertex>();

    /**
     * Triangles.
     */
    private List<Triangle> triangles = new ArrayList<Triangle>();

    /**
     * Texture coordinates.
     */
    private List<Vector2f> textureCoordinates = new ArrayList<Vector2f>();

    /**
     * Texture object, leave null if no texture is used.
     */
    private String textureName = null;

    public TriangleMesh() {
    }

    public TriangleMesh(String textureName) {
        this.textureName = textureName;
    }

    /**
     * Copy constructor
     */
    public TriangleMesh(TriangleMesh mesh) {
        // Vertices
        for (int i = 0; i < mesh.getNumberOfVertices(); i++) {
            addVertex(new Vertex(mesh.getVertex(i)));
        }
        // Texture coordinates
        for (int i = 0; i < mesh.getNumberOfTextureCoordinates(); i++) {
            addTextureCoordinate(new Vector2f(mesh.getTextureCoordinate(i)));
        }
        // Triangles
        for (int i = 0; i < mesh.getNumberOfTriangles(); i++) {
            addTriangle(new Triangle(mesh.getTriangle(i)));
        }
        textureName = mesh.textureName;
    }

    public void clear() {
        vertices.clear();
        triangles.clear();
        textureCoordinates.clear();
    }

    public int addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3) {
        triangles.add(new Triangle(vertexIndex1, vertexIndex2, vertexIndex3));
        return triangles.size() - 1;
    }

    public void addTriangle(Triangle t) {
        triangles.add(t);
    }

    public int addVertex(Vector3f position) {
        vertices.add(new Vertex(position));
        return vertices.size() - 1;
    }

    public int addVertex(Vertex vertex) {
        vertices.add(vertex);
        return vertices.size() - 1;
    }

    public Vertex getVertex(int index) {
        return vertices.get(index);
    }

    public int getNumberOfTriangles() {
        return triangles.size();
    }

    public int getNumberOfVertices() {
        return vertices.size();
    }

    public Vertex getVertex(Triangle triangle, int index) {
        return vertices.get(triangle.getVertexIndex(index));
    }

    public Triangle getTriangle(int triangleIndex) {
        return triangles.get(triangleIndex);
    }

    public Vector2f getTextureCoordinate(int texCoordIndex) {
        return textureCoordinates.get(texCoordIndex);
    }

    public void computeTriangleNormals() {
        for (int triangleIndex = 0; triangleIndex < getNumberOfTriangles(); triangleIndex++) {
            Triangle t = triangles.get(triangleIndex);
            Vector3f a = vertices.get(t.getVertexIndex(0)).getPosition();
            Vector3f b = vertices.get(t.getVertexIndex(1)).getPosition();
            Vector3f c = vertices.get(t.getVertexIndex(2)).getPosition();
            Vector3f u = b.subtract(a);
            Vector3f v = c.subtract(a);
            Vector3f normal = u.cross(v);
            float norm = normal.length();
            if (norm > 1e-8) {
                normal = normal.mult(1.0f / norm);
            } else {
                Logger.getInstance().error("Invalid triangle - cannot compute normal.");
            }
            t.setNormal(normal);
        }
    }

    public int addTextureCoordinate(Vector2f t) {
        textureCoordinates.add(t);
        return textureCoordinates.size() - 1;
    }

    public String getTextureFilename() {
        return textureName;
    }

    public void addTriangle(int vertexIndex1, int vertexIndex2, int vertexIndex3, int texCoordIndex1,
                            int texCoordIndex2, int texCoordIndex3) {
        triangles.add(new Triangle(vertexIndex1, vertexIndex2, vertexIndex3,
                texCoordIndex1, texCoordIndex2, texCoordIndex3));
    }

    public int getNumberOfTextureCoordinates() {
        return textureCoordinates.size();
    }

    public boolean hasTexture() {
        return textureName != null;
    }

    public void setColor(ColorRGBA color) {
        for (Triangle triangle : triangles) {
            triangle.setColor(color);
        }
        for (Vertex vertex : vertices) {
            vertex.setColor(color);
        }
    }

    public void clearTriangles() {
        triangles.clear();
    }

    public void computeVertexNormals() {
        Map<Integer, Vector3f> normalsMap = new HashMap<>();
        for (int triIndex = 0; triIndex < triangles.size(); triIndex++) {
            for (int i = 0; i < 3; i++) {
                int vIndex = triangles.get(triIndex).getVertexIndex(i);
                Vector3f tNormal = triangles.get(triIndex).getNormal();
                if (Math.abs(tNormal.lengthSquared()) < 1e-5) {
                    Logger.getInstance().error("Invalid triangle normal.");
                }
                if (normalsMap.get(vIndex) == null) {
                    normalsMap.put(vIndex, tNormal);
                } else {
                    normalsMap.put(vIndex, normalsMap.get(vIndex).add(tNormal));
                }
            }
        }
        for (int vIndex = 0; vIndex < vertices.size(); vIndex++) {
            if (normalsMap.get(vIndex) != null) {
                vertices.get(vIndex).setNormal(normalsMap.get(vIndex));
                vertices.get(vIndex).getNormal().normalize();
            }
        }
        Logger.getInstance().debug("Successfully computed the vertex normals.");
    }

    public void setTextureName(String textureFilename) {
        this.textureName = textureFilename;
    }

    public void removeTriangle(int index) {
        triangles.remove(index);
    }



    public BoundingBox getBoundingBox() {
        Vector3f ll = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        Vector3f ur = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
        for (Vertex v : vertices) {
            for (int i = 0; i < 3; i++) {
                if (v.getPosition().get(i) < ll.get(i)) {
                    ll.set(i, v.getPosition().get(i));
                }
                if (v.getPosition().get(i) > ur.get(i)) {
                    ur.set(i, v.getPosition().get(i));
                }
            }
        }
        Vector3f extend = ur.subtract(ll);
        BoundingBox bbox = new BoundingBox(ur.add(ll).mult(0.5f), extend.x, extend.y, extend.z);
        return bbox;
    }
}
