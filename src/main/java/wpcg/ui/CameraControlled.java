package wpcg.ui;

/**
 * This interface us used to construct a loose coupling between the JME canvas and the Swing UI.
 */
public interface CameraControlled {

  /**
   * Zoom in/out
   */
  void zoom(float delta);

  /**
   * Rotate horizontally
   */
  void rotateHorizontal(float delta);

  /**
   * Rotate vertically
   */
  void rotateVertical(float delta);
}
