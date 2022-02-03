package wpcg.base.grammar;

/**
 * Indicator that something went wrong with using the grammar.
 */
public class GrammarException extends Exception {
  public GrammarException(String message) {
    super(message);
  }
}
