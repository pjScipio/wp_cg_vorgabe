/**
 * Diese Datei ist Teil des Vorgabeframeworks zur Lehrveranstaltung Einführung in die Computergrafik
 * an der Hochschule für Angewandte Wissenschaften Hamburg von Prof. Philipp Jenke (Informatik)
 */

package wpcg.base.ui;

import com.jme3.asset.AssetManager;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.shadow.DirectionalLightShadowRenderer;
import wpcg.base.Observable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This scene class is used to represent the scene conent.
 */
public abstract class Scene3D extends Observable {

  public static final String MSG_SET_CAMERA_CONTROLLER = "MSG_SET_CAMERA_CONTROLLER";

  /**
   * This list of tasks is scheduled to be invoked by the JME thread.
   */
  private List<Runnable> runLaterTasks;

  public Scene3D() {
    runLaterTasks = new ArrayList<>();
  }

  /**
   * The init method is called once at the beginning of the runtime.
   */
  public abstract void init(AssetManager assetManager, Node rootNode, AbstractCameraController cameraController);

  /**
   * The update method is used to update the simulation state.
   */
  public abstract void update(float time);

  /**
   * This method is called once if the scene is rerendered.
   */
  public abstract void render();

  /**
   * The light setup is done in this method. It is called once at the beginning.
   */
  public void setupLights(AssetManager assetManager, Node rootNode,
                          ViewPort viewPort) {
    // Sun
    DirectionalLight sun = new DirectionalLight();
    sun.setColor(new ColorRGBA(1f, 1f, 1f, 1));
    sun.setDirection(new Vector3f(0.5f, -1, -0.5f));
    rootNode.addLight(sun);

    PointLight pointLight = new PointLight();
    pointLight.setPosition(new Vector3f(0, 1, 0));
    pointLight.setColor(ColorRGBA.Yellow);
    rootNode.addLight(pointLight);

  }

  /**
   * Return the (descriptive) title of the scene.
   */
  public abstract String getTitle();

  /**
   * Returns the user interface for the scene
   *
   * @return null, if the scene has no user interface
   */
  public abstract JPanel getUI();

  /**
   * Enqueue a task to the list which will be processed when the jMonkey Thread is active.
   */
  protected void runLater(Runnable task) {
    runLaterTasks.add(task);
  }

  public synchronized void invokeRunlaterTasks() {
    runLaterTasks.forEach(task -> task.run());
    runLaterTasks.clear();
  }

  /**
   * Provide a new camera controller. This method is only called
   */
  public ObserverCameraController getCameraController(Camera cam) {
    return null;
  }

  /**
   * Return true if the scene whants to provide a new camera controller via  getCameraController().
   */
  public boolean hasNewCameraController() {
    return false;
  }

  /**
   * Handle a picking event using the given ray.
   */
  public void handlePicking(Ray pickingRay) {
    // Default: ignore
  }

  /**
   * Load an animated character node.
   *
   * @return Scene graph node with the animated character
   */
  protected Node loadCharacter(AssetManager assetManager, Node rootNode,
                               String gltfFilename) {
    Node node = (Node) assetManager.loadModel(gltfFilename);
    node = (Node) node.getChild("knight");
    node.setShadowMode(RenderQueue.ShadowMode.Cast);
    rootNode.attachChild(node);
    return node;
  }

}
