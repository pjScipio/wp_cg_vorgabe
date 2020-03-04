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


import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

/**
 * A facet has a reference to one of its half edges. This datastructure
 * represents a general mesh (triangle, quad, ...). However, we only use
 * triangle meshes here.
 *
 * @author Philipp Jenke
 */
public class HalfEdgeTriangle {

    /**
     * One of the half edges around the facet.
     */
    private HalfEdge halfEdge;

    private Vector3f normal;

    private ColorRGBA color;

    public HalfEdgeTriangle() {
        halfEdge = null;
        normal = new Vector3f(0, 1, 0);
        color = ColorRGBA.Gray;
    }

    public HalfEdgeTriangle(HalfEdgeTriangle triangle) {
        HalfEdgeTriangle t = new HalfEdgeTriangle();
        t.halfEdge = halfEdge;
    }

    public HalfEdge getHalfEdge() {
        return halfEdge;
    }

    public void setHalfEdge(HalfEdge halfEdge) {
        this.halfEdge = halfEdge;
    }

    @Override
    public String toString() {
        return "HalfEdgeTriangle";
    }

    /**
     * Compute the area of the facet. Area of the facet.
     *
     * @return Area of the triangle.
     */
    public double getArea() {
        Vector3f v0 = halfEdge.getStartVertex().getPosition();
        Vector3f v1 = halfEdge.getNext().getStartVertex().getPosition();
        Vector3f v2 = halfEdge.getNext().getNext().getStartVertex().getPosition();
        return v1.subtract(v0).cross(v2.subtract(v0)).length() / 2.0;
    }

    /**
     * Compute the centroid (center of mass) of the triangle.
     *
     * @return Centroid of the triangle.
     */
    public Vector3f getCentroid() {
        Vector3f v0 = halfEdge.getStartVertex().getPosition();
        Vector3f v1 = halfEdge.getNext().getStartVertex().getPosition();
        Vector3f v2 = halfEdge.getNext().getNext().getStartVertex().getPosition();
        return (v0.add(v1).add(v2)).mult(1.0f / 3.0f);
    }

    public void setNormal(Vector3f normal) {
        this.normal = normal;
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    public ColorRGBA getColor() {
        return color;
    }
}
