package wpcg.lab.a7;

import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import misc.Logger;
import ui.AbstractCameraController;
import ui.Scene3D;
import wpcg.base.grammar.GrammarException;
import wpcg.lab.a7.buildinggrammar.BuildingParameters;
import wpcg.lab.solution.a7.GrammarSolution;

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
    GrammarSolution grammar = new GrammarSolution();
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
}
