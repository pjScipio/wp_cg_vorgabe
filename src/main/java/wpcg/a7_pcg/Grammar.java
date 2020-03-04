package wpcg.a7_pcg;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grammar {
    private Map<String, List<Rule>> rules;

    public Grammar() {
        rules = new HashMap<>();
        Locale.setDefault(Locale.US);
    }

    protected Map<String, List<Rule>> getRules() {
        return rules;
    }

    protected boolean isDeterministicSymbolRule(String symbol) {
        return rules.get(symbol) != null && rules.get(symbol).size() == 1;
    }

    protected boolean isTerminal(String symbol) {
        return rules.get(symbol) == null;
    }

    public void parse(String grammarFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(grammarFile)));
            String line;
            while ((line = reader.readLine()) != null) {
                addRule(new Rule(line));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addRule(Rule rule) {
        if (rules.get(rule.predecessor) == null) {
            List<Rule> rules4pred = new ArrayList<>();
            rules.put(rule.predecessor, rules4pred);
        }
        rules.get(rule.predecessor).add(rule);
    }
}
