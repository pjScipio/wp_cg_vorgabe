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

/**
 * A half edge has references to the next edge within the current facet, the
 * opposite edge, its start vertex and the facet it belongs to.
 *
 * @author Philipp Jenke
 */
public class HalfEdge {

    /**
     * Reference to the next edge in the mesh.
     */
    private HalfEdge next;

    /**
     * Reference to the opposite edge in the mesh.
     */
    private HalfEdge opposite;

    /**
     * Start vertex of the half edge.
     */
    private HalfEdgeVertex startVertex;

    /**
     * The half edge belongs to this facet.
     */
    private HalfEdgeTriangle facet;

    public HalfEdge() {
        this.next = null;
        this.opposite = null;
        this.startVertex = null;
        this.facet = null;
    }

    public HalfEdge(HalfEdgeVertex v) {
        this.next = null;
        this.opposite = null;
        this.startVertex = v;
        this.facet = null;
    }

    public HalfEdge getNext() {
        return next;
    }

    public void setNext(HalfEdge next) {
        this.next = next;
    }

    public HalfEdge getOpposite() {
        return opposite;
    }

    public void setOpposite(HalfEdge opposite) {
        this.opposite = opposite;
    }

    public HalfEdgeVertex getStartVertex() {
        return startVertex;
    }

    public void setStartVertex(HalfEdgeVertex startVertex) {
        this.startVertex = startVertex;
    }

    public HalfEdgeTriangle getFacet() {
        return facet;
    }

    public void setFacet(HalfEdgeTriangle facet) {
        this.facet = facet;
    }

    @Override
    public String toString() {
        return "Half Edge";
    }

    /**
     * Return the end vertex of a half edge (not directly saved.
     *
     * @return End index of the half edge, null on error.
     */
    public HalfEdgeVertex getEndVertex() {
        if (getNext() == null) {
            throw new IllegalArgumentException();
        }
        return getNext().getStartVertex();
    }

    public boolean isBoundary() {
        return opposite == null;
    }
}
