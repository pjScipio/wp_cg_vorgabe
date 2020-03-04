/**
 * Diese Datei gehört zum Android/Java Framework zur Veranstaltung "Computergrafik für
 * Augmented Reality" von Prof. Dr. Philipp Jenke an der Hochschule für Angewandte
 * Wissenschaften (HAW) Hamburg. Weder Teile der Software noch das Framework als Ganzes dürfen
 * ohne die Einwilligung von Philipp Jenke außerhalb von Forschungs- und Lehrprojekten an der HAW
 * Hamburg verwendet werden.
 * <p>
 * This file is part of the Android/Java framework for the course "Computer graphics for augmented
 * reality" by Prof. Dr. Philipp Jenke at the University of Applied (UAS) Sciences Hamburg. Neither
 * parts of the framework nor the complete framework may be used outside of research or student
 * projects at the UAS Hamburg.
 */
package wpcg.a3_subdivision.halfedge;

import com.jme3.bounding.BoundingBox;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import wpcg.base.mesh.Triangle;
import wpcg.base.mesh.TriangleMesh;
import wpcg.base.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A triangle mesh a a list of triangles, a list of half edges and a list of
 * vertices
 *
 * @author Philipp Jenke
 */
public class HalfEdgeTriangleMesh {

    private List<HalfEdgeTriangle> triangles;

    private List<HalfEdgeVertex> vertices;

    private List<HalfEdge> halfEdges;

    /**
     * This is a cache data structure used in the split() routine: remember for each half edge (and its opposite)
     * the vertex created in the middle.
     */
    private Map<HalfEdge, HalfEdgeVertex> cacheSplitHe2VertexMap;

    /**
     * This is a cache data structure used in the split() routine: remember of each newly created Vertex the half edge
     * which created it.
     */
    private Map<HalfEdgeVertex, HalfEdge> cacheSplitVertex2HeMap;

    // --- CONSTRUCTION/TRANSFORMATION ACCESS -------------------

    public HalfEdgeTriangleMesh() {
        triangles = new ArrayList<HalfEdgeTriangle>();
        vertices = new ArrayList<HalfEdgeVertex>();
        halfEdges = new ArrayList<HalfEdge>();
        cacheSplitHe2VertexMap = null;
        cacheSplitVertex2HeMap = null;
    }

    public static HalfEdgeTriangleMesh from(TriangleMesh mesh) {
        HalfEdgeTriangleMesh heMesh = new HalfEdgeTriangleMesh();
        for (int i = 0; i < mesh.getNumberOfVertices(); i++) {
            heMesh.addVertex(mesh.getVertex(i).getPosition());
        }
        for (int i = 0; i < mesh.getNumberOfTriangles(); i++) {
            Triangle t = mesh.getTriangle(i);
            int index = heMesh.addTriangle(t.getA(), t.getB(), t.getC());
            HalfEdgeTriangle halfEdgeTriangle = heMesh.getTriangle(index);
            halfEdgeTriangle.setColor(t.getColor());
        }
        heMesh.connectHalfEdges();
        heMesh.computeTriangleNormals();
        Logger.getInstance().debug("Created half edge triangle mesh from mesh with " + mesh.getNumberOfTriangles()
                + " triangles.");
        return heMesh;
    }

    /**
     * Creates a triangle mesh from a half edge triangle mesh.
     * <p>
     * Attention: the resulting mesh is a triangle soup. If the vertices shall be reconnected, call
     * TriangleMeshTools.mergeVertices(mesh);
     */
    public TriangleMesh toMesh() {
        TriangleMesh mesh = new TriangleMesh();
        for (HalfEdgeTriangle t : triangles) {
            HalfEdge he = t.getHalfEdge();
            int index = mesh.addVertex(he.getStartVertex().getPosition());
            he = he.getNext();
            mesh.addVertex(he.getStartVertex().getPosition());
            he = he.getNext();
            mesh.addVertex(he.getStartVertex().getPosition());
            int iIndex = mesh.addTriangle(index, index + 1, index + 2);
            Triangle triangle = mesh.getTriangle(iIndex);
            triangle.setColor(t.getColor());
        }
        //TriangleMeshTools.mergeVertices(mesh);
        mesh.computeTriangleNormals();
        Logger.getInstance().debug("Created triangle mesh from half edge mesh with " + this.getNumberOfTriangles()
                + " triangles.");
        return mesh;
    }

    // --- WRITING ACCESS -------------------

    public int addTriangle(int vertexIndex1, int vertexIndex2,
                           int vertexIndex3) {
        HalfEdge halfEdge1 = new HalfEdge();
        HalfEdge halfEdge2 = new HalfEdge();
        HalfEdge halfEdge3 = new HalfEdge();
        HalfEdgeTriangle facet = new HalfEdgeTriangle();
        halfEdge1.setNext(halfEdge2);
        halfEdge2.setNext(halfEdge3);
        halfEdge3.setNext(halfEdge1);
        halfEdge1.setStartVertex(vertices.get(vertexIndex1));
        halfEdge2.setStartVertex(vertices.get(vertexIndex2));
        halfEdge3.setStartVertex(vertices.get(vertexIndex3));
        halfEdge1.setFacet(facet);
        halfEdge2.setFacet(facet);
        halfEdge3.setFacet(facet);
        facet.setHalfEdge(halfEdge1);
        halfEdges.add(halfEdge1);
        halfEdges.add(halfEdge2);
        halfEdges.add(halfEdge3);
        triangles.add(facet);
        return triangles.size() - 1;
    }

    public void clear() {
        vertices.clear();
        triangles.clear();
        halfEdges.clear();
    }

    public int addVertex(HalfEdgeVertex v) {
        vertices.add(v);
        return vertices.size() - 1;
    }

    public int addVertex(Vector3f position) {
        return addVertex(new HalfEdgeVertex(position));
    }

    public void addHalfEdge(HalfEdge halfEdge) {
        halfEdges.add(halfEdge);
    }

    public void addTriangle(HalfEdgeTriangle t) {
        triangles.add(t);
    }

    // --- READING ACCESS -------------------

    public HalfEdgeVertex getVertex(int index) {
        return vertices.get(index);
    }

    public HalfEdgeTriangle getTriangle(int triangleIndex) {
        return triangles.get(triangleIndex);
    }

    public int getNumberOfTriangles() {
        return triangles.size();
    }

    public int getNumberOfVertices() {
        return vertices.size();
    }

    public int getNumberOfHalfEdges() {
        return halfEdges.size();
    }

    public HalfEdgeVertex getVertex(HalfEdgeTriangle triangle, int index) {
        HalfEdgeTriangle het = triangle;
        HalfEdge he = het.getHalfEdge();
        for (int i = 0; i < index; i++) {
            he = he.getNext();
        }
        return he.getStartVertex();
    }

    public HalfEdge getHalfEdge(int halfEdgeIndex) {
        return halfEdges.get(halfEdgeIndex);
    }

    public boolean hasBoudary() {
        for (HalfEdge he : halfEdges) {
            if (he.getOpposite() == null) {
                return true;
            }
        }
        return false;
    }

    // --- OPERATIONS -------------------

    /**
     * Finally, opposite half edges must be connected and each vertex must be
     * assigned a outgoing half edge.
     * <p>
     * Attention: O(n^2)!
     */
    public void connectHalfEdges() {
        // Set half edge for each vertex
        for (int halfEdgeIndex = 0; halfEdgeIndex < halfEdges
                .size(); halfEdgeIndex++) {
            HalfEdge halfEdge = halfEdges.get(halfEdgeIndex);
            halfEdge.getStartVertex().setHalfEgde(halfEdge);
        }

        // Connect opposite halfEdges
        for (int i = 0; i < getNumberOfHalfEdges(); i++) {
            HalfEdge halfEdge1 = getHalfEdge(i);
            if (halfEdge1.getOpposite() == null) {
                for (int j = i + 1; j < getNumberOfHalfEdges(); j++) {
                    HalfEdge halfEdge2 = getHalfEdge(j);
                    if (halfEdge1.getStartVertex() == halfEdge2.getEndVertex()
                            && halfEdge2.getStartVertex() == halfEdge1.getEndVertex()) {
                        halfEdge1.setOpposite(halfEdge2);
                        halfEdge2.setOpposite(halfEdge1);
                        break;
                    }
                }
            }
        }
        Logger.getInstance().debug("Successfully connected half edges.");
    }

    public void computeTriangleNormals() {
        for (int triangleIndex =
             0; triangleIndex < getNumberOfTriangles(); triangleIndex++) {
            HalfEdgeTriangle facet = triangles.get(triangleIndex);
            HalfEdge he = facet.getHalfEdge();
            HalfEdgeVertex v1 = he.getStartVertex();
            HalfEdgeVertex v2 = he.getNext().getStartVertex();
            HalfEdgeVertex v3 = he.getNext().getNext().getStartVertex();
            Vector3f u = v2.getPosition().subtract(v1.getPosition());
            Vector3f v = v3.getPosition().subtract(v1.getPosition());
            Vector3f normal = u.cross(v);
            normal = normal.normalize();
            facet.setNormal(normal);
        }
        Logger.getInstance().debug("Successfully computed face normals.");
    }

    public BoundingBox getBoundingBox() {
        Vector3f ll = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
        Vector3f ur = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
        for (HalfEdgeVertex v : vertices) {
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


    /**
     * Split each triangle into four sub-triangles.
     * <p>
     * The olf old half edges and facets are kept and should be replaced later.
     */
    public Map<HalfEdgeVertex, HalfEdge> split() {
        // Create 4 triangles for each old triangle
        int oldNumTriangles = getNumberOfTriangles();
        cacheSplitHe2VertexMap = new HashMap<>();
        cacheSplitVertex2HeMap = new HashMap<>();
        for (int i = 0; i < oldNumTriangles; i++) {
            HalfEdgeTriangle triangle = triangles.get(i);
            HalfEdge he = triangle.getHalfEdge();
            HalfEdge heNext = he.getNext();
            HalfEdge heNextNext = heNext.getNext();
            HalfEdgeVertex va = he.getStartVertex();
            HalfEdgeVertex vb = heNext.getStartVertex();
            HalfEdgeVertex vc = heNextNext.getStartVertex();
            HalfEdgeVertex va_ = createVertexHalfEdgeSplit(he);
            HalfEdgeVertex vb_ = createVertexHalfEdgeSplit(heNext);
            HalfEdgeVertex vc_ = createVertexHalfEdgeSplit(heNextNext);
            createHalfEdgeTriangle(va, va_, vc_, triangle.getColor());
            createHalfEdgeTriangle(vb, vb_, va_, triangle.getColor());
            createHalfEdgeTriangle(vc, vc_, vb_, triangle.getColor());
            createHalfEdgeTriangle(va_, vb_, vc_, triangle.getColor());
        }
        connectHalfEdges();

        // Reset cache data structure
        cacheSplitHe2VertexMap = null;
        return cacheSplitVertex2HeMap;
    }

    /**
     * Create a new facet for the given vertices.
     */
    private void createHalfEdgeTriangle(HalfEdgeVertex a, HalfEdgeVertex b, HalfEdgeVertex c, ColorRGBA color) {
        HalfEdge he1 = new HalfEdge(a);
        HalfEdge he2 = new HalfEdge(b);
        HalfEdge he3 = new HalfEdge(c);
        a.setHalfEgde(he1);
        b.setHalfEgde(he2);
        c.setHalfEgde(he3);
        HalfEdgeTriangle triangle = new HalfEdgeTriangle();
        triangle.setColor(color);
        triangle.setHalfEdge(he1);
        he1.setNext(he2);
        he2.setNext(he3);
        he3.setNext(he1);
        he1.setFacet(triangle);
        he2.setFacet(triangle);
        he3.setFacet(triangle);
        halfEdges.add(he1);
        halfEdges.add(he2);
        halfEdges.add(he3);
        triangles.add(triangle);
    }

    /**
     * Create a new half edge vertex in the middle of the given half edge.
     */
    private HalfEdgeVertex createVertexHalfEdgeSplit(HalfEdge he) {

        HalfEdgeVertex v = cacheSplitHe2VertexMap.get(he);
        if (v != null) {
            return v;
        }

        HalfEdgeVertex va = he.getStartVertex();
        HalfEdgeVertex vb = he.getEndVertex();
        Vector3f pos = va.getPosition().add(vb.getPosition()).mult(0.5f);
        v = vertices.get(addVertex(pos));
        cacheSplitHe2VertexMap.put(he, v);
        cacheSplitVertex2HeMap.put(v, he);
        if (he.getOpposite() != null) {
            cacheSplitHe2VertexMap.put(he.getOpposite(), v);
        }
        return v;
    }

    public void removeTriangle(int index) {
        triangles.remove(index);
    }

    public void removeHalfEdge(int index) {
        halfEdges.remove(index);
    }
}
