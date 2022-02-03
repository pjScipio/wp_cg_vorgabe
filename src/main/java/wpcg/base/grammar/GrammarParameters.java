package wpcg.base.grammar;

import base.Parameters;
import com.google.common.base.Preconditions;
import misc.AssetPath;
import misc.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Represents the parameters of an application using grammars.
 * T is used as the symbol type (right-hand-side of a rule).
 */
public class GrammarParameters<T> extends Parameters {

  /**
   * Parsed grammar
   */
  protected Grammar<T> grammar;

  /**
   * Parser used to convert from text -> grammar.
   */
  protected GrammarParser<T> parser;

  public GrammarParameters(Grammar<T> grammar, GrammarParser<T> parser) {
    Preconditions.checkNotNull(parser);
    Preconditions.checkNotNull(grammar);
    this.grammar = grammar;
    this.parser = parser;
  }

  /**
   * Read grammar from a grammar text file.
   */
  public void readGrammarFromFile(String grammarFilename) throws GrammarException {
    Preconditions.checkNotNull(grammar);
    String assetPath = AssetPath.getInstance().getPathToAsset(grammarFilename);
    if (assetPath == null) {
      Logger.getInstance().error("Failed to read grammar from " + grammarFilename);
      return;
    }
    try (FileReader reader = new FileReader(assetPath)) {
      BufferedReader br = new BufferedReader(reader);
      String text = "";
      String line;
      while ((line = br.readLine()) != null) {
        text += line + "\n";
      }
      readGrammarFromString(text);
      notifyAllObservers();
      System.out.println("Successfully read grammar from " + grammarFilename);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Read grammar from a string.
   */
  public void readGrammarFromString(String grammarText) throws GrammarException {
    parser.parse(grammar, grammarText);
    grammar.derive();
    notifyAllObservers();
  }

  public String getGrammarText() {
    return grammar.toString();
  }

  public Grammar<T> getGrammar() {
    return grammar;
  }
}
