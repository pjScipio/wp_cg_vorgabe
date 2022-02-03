package wpcg.lab.a7.buildinggrammar;

import wpcg.base.grammar.Grammar;
import wpcg.base.grammar.GrammarException;
import wpcg.base.grammar.GrammarParser;
import wpcg.base.grammar.Symbol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BuildingsParser implements GrammarParser<Symbol> {
  @Override
  public void parse(Grammar<Symbol> grammar, String grammarText) throws GrammarException {
    try (Reader reader = new StringReader(grammarText)) {
      BufferedReader br = new BufferedReader(reader);
      String line;
      grammar.clearRules();
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.startsWith("#")) {
          continue;
        }
        parseLine(grammar, line.trim());
      }
    } catch (IOException e) {
      throw new GrammarException("Failed to parseFile rule file.");
    }
  }

  private void parseLine(Grammar<Symbol> grammar, String line) {
    String regex = "(?<pred>.*)\\s*-->\\s*(?<hds>.*)\\s*";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);
    if (matcher.matches()) {
      Symbol symbol = new Symbol(matcher.group("pred").trim());
      String rhs = matcher.group("hds").trim();
      String[] rhsTokes = rhs.split(":");
      List<Symbol> successors;
      float weight;
      if (rhsTokes.length == 1) {
        successors = Arrays.asList(rhs.trim().split("\\s"))
                .stream().map(s -> new Symbol(s)).collect(Collectors.toList());
        weight = 1;
      } else {
        successors = Arrays.asList(rhsTokes[0].trim().split("\\s"))
                .stream().map(s -> new Symbol(s)).collect(Collectors.toList());
        weight = Float.valueOf(rhsTokes[1].trim());
      }
      BuildingRule rule = new BuildingRule(symbol, successors, weight);
      grammar.addRule(rule);
    } else {
      throw new IllegalArgumentException("Cannot parse rool from line " + line);
    }
  }
}
