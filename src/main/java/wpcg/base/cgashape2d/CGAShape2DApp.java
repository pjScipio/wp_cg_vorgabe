/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg.base.cgashape2d;

import misc.Logger;
import ui.GenericCGApplication;
import wpcg.base.cgashape2d.shapes.Shape2D;
import wpcg.base.grammar.GrammarEditor;
import wpcg.base.grammar.GrammarException;

/**
 * Sample application to use CGAShape2D.
 */
public class CGAShape2DApp extends GenericCGApplication {
  public CGAShape2DApp() {
    super("CGA Shape 2D");

    CGAShape2DParameters params = new CGAShape2DParameters();
    try {
      params.readGrammarFromFile("cgashape2d/basic.grammar");
    } catch (GrammarException e) {
      Logger.getInstance().error("Grammar error: " + e.getMessage());
    }
    GrammarEditor<Shape2D> grammarEditor = new GrammarEditor<Shape2D>(params, "cgashape2d");
    CGAShape2DTreeViewer shapeTreeViewer = new CGAShape2DTreeViewer(params);
    CGAShape2DViewer viewer = new CGAShape2DViewer(600, 600, params);

    addPanel(grammarEditor, "Grammar");
    addPanel(shapeTreeViewer, "Shape Tree");
    addScene2D(viewer);
  }

  public static void main(String[] args) {
    new CGAShape2DApp();
  }
}
