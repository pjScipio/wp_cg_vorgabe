package wpcg.lab.a7.buildinggrammar;

import wpcg.base.grammar.GrammarParameters;
import wpcg.base.grammar.Symbol;

/**
 * Parameters of a CGAShape2D grammar.
 */
public class BuildingParameters extends GrammarParameters<Symbol> {

  public BuildingParameters(BuilingGrammar grammar) {
    super(grammar, new BuildingsParser());
  }
}
