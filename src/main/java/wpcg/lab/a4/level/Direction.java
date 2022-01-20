/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg.lab.a4.level;

import com.jme3.math.Vector2f;

/**
 * Enumeration type for the six directions of the cell edges.
 */
public enum Direction {

  UHR_0, UHR_2, UHR_4, UHR_6, UHR_8, UHR_10;

  /**
   * Return the opposite direction of the direction.
   */
  public Direction getGegenueber() {
    return values()[(ordinal() + 3) % 6];
  }

  /**
   * Compute and return a 2D vector pointing towards the direction (cell edge).
   */
  public Vector2f getOrientation() {
    float angle = 0;
    switch (this) {
      case UHR_0:
        angle = 0;
        break;
      case UHR_2:
        angle = -60 / 180.0f * (float) Math.PI;
        break;
      case UHR_4:
        angle = -120 / 180.0f * (float) Math.PI;
        break;
      case UHR_6:
        angle = -180 / 180.0f * (float) Math.PI;
        break;
      case UHR_8:
        angle = -240 / 180.0f * (float) Math.PI;
        break;
      case UHR_10:
        angle = -300 / 180.0f * (float) Math.PI;
        break;
      default:
        throw new IllegalArgumentException("Should not happen");
    }
    return new Vector2f(-(float) Math.sin(angle), (float) -Math.cos(angle));
  }
}
