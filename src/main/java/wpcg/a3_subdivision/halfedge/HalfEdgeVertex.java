/**
 * Prof. Philipp Jenke
 * Hochschule für Angewandte Wissenschaften (HAW), Hamburg
 * <p>
 * Base framework for "WP Computergrafik".
 */
package wpcg.a3_subdivision.halfedge;

import com.jme3.math.Vector3f;

/**
 * Representation of a vertex.
 *
 * @author Philipp Jenke
 */
public class HalfEdgeVertex  {

    /**
     * Reference to one of the outgoing half edges.
     */
    private HalfEdge halfEgde = null;

    /**
     * Position.
     */
    private Vector3f pos;

    /**
     * Constructor.
     *
     * @param position Initial value for position.
     */
    public HalfEdgeVertex(Vector3f position) {
        this.pos = position;
    }


    public HalfEdge getHalfEdge() {
        return halfEgde;
    }

    public void setHalfEgde(HalfEdge halfEgde) {
        this.halfEgde = halfEgde;
    }

    @Override
    public String toString() {
        return "HalfEdgeVertex";
    }

    public Vector3f getPosition() {
        return pos;
    }
}