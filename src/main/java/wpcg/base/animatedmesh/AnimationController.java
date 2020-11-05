/**
 * Diese Datei ist Teil der Vorgabe zur Lehrveranstaltung Einführung in die Computergrafik der Hochschule
 * für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */

package wpcg.base.animatedmesh;

import com.jme3.math.Vector3f;

/**
 * An entity is used to define the pose of a thing in a 3D world. It can be
 * changed over time by calling move().
 */
public abstract class AnimationController {


  /**
   * Update the pose.
   */
  public abstract void move(float time);

  /**
   * Get the current pose.
   */
  public abstract Pose getPose();

  /**
   * Return the current Orientation.
   */
  public Vector3f getDirection() {
    return getPose().rot.getColumn(2);
  }
}
