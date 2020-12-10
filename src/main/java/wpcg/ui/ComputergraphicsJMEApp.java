/**
 * Diese Datei ist Teil der Vorgabe zur Lehrveranstaltung Einführung in die Computergrafik der Hochschule
 * für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */
package wpcg.ui;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import wpcg.base.CameraController;
import wpcg.base.Scene;

/**
 * This is the main application which is used for all exercises. Only adjust the
 * scene in the main method.
 */
public class ComputergraphicsJMEApp extends SimpleApplication implements CameraControlled {

  /**
   * Camera controller.
   */
  private CameraController cameraController;

  /**
   * Scene providing the content.
   */
  private final Scene scene;

  public ComputergraphicsJMEApp(Scene scene) {
    this.scene = scene;
  }

  @Override
  public void simpleInitApp() {
    // Input
    setupInput();

    // Debug
    setDisplayFps(false);
    setDisplayStatView(false);

    scene.setupLights(assetManager, rootNode, viewPort);
    scene.init(assetManager, rootNode, cameraController);
    viewPort.setBackgroundColor(ColorRGBA.White);
  }

  /**
   * Register the handlers for the mous and key input (also used for the camera
   * controller).
   */
  private void setupInput() {
    cam.setLocation(new Vector3f(-5, 5, 5));
    stateManager.detach(stateManager.getState(FlyCamAppState.class));
    cameraController = new CameraController(cam);
    cameraController.lookAt(new Vector3f(0, 1, 0),
            new Vector3f(0, 1, 0));
    viewPort.setBackgroundColor(ColorRGBA.LightGray);
  }

  @Override
  public void simpleUpdate(float tpf) {
    scene.invokeRunlaterTasks();
    scene.update(tpf);
  }

  @Override
  public void simpleRender(RenderManager rm) {
    scene.render();
  }

  @Override
  public void zoom(float delta) {
    cameraController.zoom(delta);
  }

  @Override
  public void rotateHorizontal(float delta) {
    cameraController.rotateAroundUp(delta);
  }

  @Override
  public void rotateVertical(float delta) {
    cameraController.rotateAroundLeft(delta);
  }
}