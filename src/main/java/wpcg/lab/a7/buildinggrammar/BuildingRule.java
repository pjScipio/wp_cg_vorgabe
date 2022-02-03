/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg.lab.a7.buildinggrammar;

import wpcg.base.grammar.Rule;
import wpcg.base.grammar.Symbol;

import java.util.Arrays;
import java.util.List;

/**
 * A rule provides the successors for a symbol und can provide a weight if there
 * are multiple rules for a symbol
 * in the grammar.
 */
public class BuildingRule implements Rule {

  /**
   * Symbol the rule can be applied for.
   */
  public Symbol symbol;

  /***
   * List of successor symbols.
   */
  public List<Symbol> successors;

  /**
   * Rule weight (if there are multiple rules for a symbol in the grammar).
   */
  public float weight;

  public BuildingRule(Symbol symbol, List<Symbol> successors, float weight) {
    this.symbol = symbol;
    this.successors = successors;
    this.weight = weight;
  }

  @Override
  public List<Symbol> getPred() {
    return Arrays.asList(symbol);
  }
}