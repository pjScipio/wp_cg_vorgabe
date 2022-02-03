package wpcg.base.cgashape2d;

import wpcg.base.cgashape2d.grammar.CGAShape2DGrammar;
import wpcg.base.cgashape2d.grammar.CGAShape2DParser;
import wpcg.base.cgashape2d.shapes.Shape2D;
import wpcg.base.grammar.GrammarParameters;

/**
 * Parameters of a CGAShape2D grammar.
 */
public class CGAShape2DParameters extends GrammarParameters<Shape2D> {

  public CGAShape2DParameters() {
    super(new CGAShape2DGrammar(), new CGAShape2DParser());
  }
}
