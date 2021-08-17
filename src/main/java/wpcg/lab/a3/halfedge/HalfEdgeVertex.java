/**
 * Diese Datei ist Teil des Vorgabeframeworks zur Lehrveranstaltung Einführung in die Computergrafik
 * an der Hochschule für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */

package wpcg.lab.a3.halfedge;

import com.jme3.math.Vector3f;

/**
 * Representation of a vertex.
 *
 * @author Philipp Jenke
 */
public class HalfEdgeVertex {

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

  @Override
  public String toString() {
    return "HalfEdgeVertex";
  }

  // +++ GETTER/SETTER ++++++++++++

  public HalfEdge getHalfEdge() {
    return halfEgde;
  }

  public void setHalfEgde(HalfEdge halfEgde) {
    this.halfEgde = halfEgde;
  }

  public Vector3f getPosition() {
    return pos;
  }
}