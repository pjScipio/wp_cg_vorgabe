/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
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
