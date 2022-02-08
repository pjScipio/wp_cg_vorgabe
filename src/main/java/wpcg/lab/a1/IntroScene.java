/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg.lab.a1;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.shader.VarType;
import ui.AbstractCameraController;
import ui.Scene3D;
import wpcg.base.animatedmesh.AnimatedMesh;
import wpcg.base.animatedmesh.AnimationControllerPath;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This introduction scene contains the content of the first exercise: a knight
 * that walks on a squared plane.
 */
public class IntroScene extends Scene3D {

  /**
   * The asset manager is used to read content (e.g. triangle meshes or texture)
   * from file to jMonkey.
   */
  private AssetManager assetManager;

  /**
   * This is the root node of the scene graph with all the scene content.
   */
  private Node rootNode;

  /**
   * A list of all animated entities
   */
  protected List<AnimatedMesh> animatedMeshList;

  public IntroScene() {
    assetManager = null;
    rootNode = null;
    animatedMeshList = new ArrayList<>();
  }

  @Override
  public void init(AssetManager assetManager, Node rootNode,
                   AbstractCameraController cameraController) {
    this.assetManager = assetManager;
    this.rootNode = rootNode;
    cameraController.setup(new Vector3f(-7, 7, -7),
            new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));

    // Add one knight
    addKnight();

    // Add a square to the scene graph
    createGround();
  }

  @Override
  public void update(float time) {
    // Update position
    for (AnimatedMesh animatedMesh : animatedMeshList) {
      animatedMesh.update(time);
    }
  }

  @Override
  public void render() {

  }

  @Override
  public String getTitle() {
    return "My 1st mesh";
  }

  /**
   * Add an additional Knight into the scene. The operation changes the scene graph, therefore it has to be scheduled
   * to be run by the JME thread.
   */
  protected void addKnight() {
    runLater(() -> {
      Node knightNode = loadCharacter(assetManager, rootNode,
              "models/knight.gltf");
      knightNode.setLocalScale(0.01f);
      AnimationControllerPath knightAnimationController =
              new AnimationControllerPath(Arrays.asList(
                      new Vector3f(6, 0, -6),
                      new Vector3f(6, 0, -3.5f),
                      new Vector3f(-3.5f, 0, 6),
                      new Vector3f(-6f, 0, 6f),
                      new Vector3f(-6f, 0, -6f)), 1.2f);
      animatedMeshList.add(new AnimatedMesh(knightNode,
              knightAnimationController));
    });
  }

  /**
   * Generate a ground mesh.
   */
  private void createGround() {
    float extend = 15;
    Box box = new Box(new Vector3f(-extend / 2, -0.05f, -extend / 2),
            new Vector3f(extend / 2, 0, extend / 2));
    Geometry quadGeometry = new Geometry("Ground", box);
    Material mat = new Material(assetManager,
            "Common/MatDefs/Light/Lighting.j3md");
    mat.setColor("Diffuse",
            new ColorRGBA(0.3f, 0.15f, 0.05f, 1));
    mat.setParam("UseMaterialColors", VarType.Boolean, true);
    quadGeometry.setMaterial(mat);
    rootNode.attachChild(quadGeometry);
    quadGeometry.setShadowMode(RenderQueue.ShadowMode.Receive);
  }

  /**
   * Return any of the knights.
   */
  protected Optional<Node> getAnyKnightNode() {
    return animatedMeshList.stream().map(a -> a.getNode()).findAny();
  }


  @Override
  public JPanel getUserInterface() {
    JPanel introUi = new JPanel();
    JButton buttonAddKnight = new JButton("Add knight");
    buttonAddKnight.addActionListener(e -> addKnight());
    introUi.add(buttonAddKnight);
    return introUi;
  }

  /**
   * Lighting can be customized here
   */
  @Override
  public void setupLights(Node rootNode, ViewPort viewPort) {
    // Sun
    DirectionalLight sun = new DirectionalLight();
    sun.setColor(new ColorRGBA(1, 1, 1, 1));
    sun.setDirection(new Vector3f(0.25f, -1, 0.1f));
    rootNode.addLight(sun);

    // Point light source in the center of the scene.
    PointLight light = new PointLight();
    light.setPosition(new Vector3f(0,1,0));
    light.setRadius(0);
    light.setColor(ColorRGBA.White.mult(2));
    rootNode.addLight(light);

    // Some light everywhere
    AmbientLight ambientLight = new AmbientLight();
    ColorRGBA brightAmbientColor = ColorRGBA.White;
    ambientLight.setColor(brightAmbientColor);
    rootNode.addLight(ambientLight);
  }
}
