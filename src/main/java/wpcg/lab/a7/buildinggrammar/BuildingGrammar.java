/**
 * This file is part of the computer graphics project of the computer graphics group led by
 * Prof. Dr. Philipp Jenke at the University of Applied Sciences (HAW) in Hamburg.
 */

package wpcg.lab.a7.buildinggrammar;

import wpcg.base.grammar.Grammar;
import wpcg.base.grammar.GrammarException;
import wpcg.base.grammar.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Representation of the building grammar.
 */
public class BuildingGrammar extends Grammar<Symbol> {

  private Map<Symbol, BuildingRule> ruleMap;

  public BuildingGrammar() {
    Locale.setDefault(Locale.US);
    ruleMap = new HashMap<>();
  }

  /**
   * Returns true if the rule is deterministic (only one rule for the symbol).
   */
  protected boolean isDeterministicSymbolRule(Symbol symbol) {
    return getRulesForSymbol(symbol) != null && getRulesForSymbol(symbol).size() == 1;
  }

  /**
   * Return all rules that can be applied to the given symbol
   */
  protected List<BuildingRule> getRulesForSymbol(Symbol symbol) {
    return rules
            .stream()
            .filter(r -> r.getPred().size() == 1 && r.getPred().get(0).equals(symbol))
            .map(r -> (BuildingRule) r)
            .collect(Collectors.toList());
  }

  /**
   * Returns trie if the symbol is a terminal symbol (no rule available).
   */
  protected boolean isTerminal(Symbol symbol) {
    var rules = getRulesForSymbol(symbol);
    return rules == null || rules.size() == 0;
  }


  @Override
  public List<Symbol> derive(Symbol symbol) throws GrammarException {
    return null;
  }
}
