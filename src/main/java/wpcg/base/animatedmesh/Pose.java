package wpcg.base.animatedmesh;

import com.jme3.math.Matrix3f;
import com.jme3.math.Vector3f;

/**
 * The pose encodes the current position an rotation of the thing
 */
public class Pose {
  /*
   * Position in 3-space.
   */
  public Vector3f pos;

  /**
   * Rotation matrix
   */
  public Matrix3f rot;

  public Pose(Vector3f pos, Matrix3f rot) {
    this.pos = pos;
    this.rot = rot;
  }
}
