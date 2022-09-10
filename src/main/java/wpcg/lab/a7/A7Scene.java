package wpcg.lab.a7;

import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import misc.Logger;
import ui.AbstractCameraController;
import ui.Scene3D;
import wpcg.base.grammar.GrammarException;
import wpcg.lab.a7.buildinggrammar.BuildingGrammar;
import wpcg.lab.a7.buildinggrammar.BuildingParameters;

public class A7Scene extends Scene3D {

  private AssetManager assetManager;
  private Node rootNode;

  @Override
  public void init(AssetManager assetManager, Node rootNode, AbstractCameraController cameraController) {
    this.assetManager = assetManager;
    this.rootNode = rootNode;
    cameraController.setup(new Vector3f(-7, 3, 7), new Vector3f(0, 0, 0),
            new Vector3f(0, 1, 0));

    // Init grammar
    BuildingGrammar grammar = new BuildingGrammar();
    BuildingParameters params = new BuildingParameters(grammar);
    try {
      params.readGrammarFromFile("buildings/building.grammar");
    } catch (GrammarException e) {
      Logger.getInstance().error("Grammar error: " + e.getMessage());
    }
  }

  @Override
  public void update(float time) {

  }

  @Override
  public void render() {

  }

  @Override
  public String getTitle() {
    return "Building Grammar";
  }

  /**
   * Lighting can be customized here
   */
  @Override
  public void setupLights(Node rootNode, ViewPort viewPort) {
    // Sun
    DirectionalLight sun = new DirectionalLight();
    sun.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 1));
    sun.setDirection(new Vector3f(0.25f, -1, 0.1f));
    rootNode.addLight(sun);

    // Some light everywhere
    AmbientLight ambientLight = new AmbientLight();
    ColorRGBA brightAmbientColor = new ColorRGBA(0.1f, 0.1f, 0.1f, 1);
    ambientLight.setColor(brightAmbientColor);
    rootNode.addLight(ambientLight);
  }
}
