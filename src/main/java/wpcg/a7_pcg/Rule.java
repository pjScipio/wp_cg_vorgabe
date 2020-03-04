package wpcg.a7_pcg;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rule {
    public String predecessor;
    public List<String> successors;
    public double weight;

    public Rule(String line) {
        String regex = "(?<pred>.*)\\s*-->\\s*(?<hds>.*)\\s*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            predecessor = matcher.group("pred").trim();
            String rhs = matcher.group("hds").trim();
            String[] rhsTokes = rhs.split(":");
            if (rhsTokes.length == 1) {
                successors = Arrays.asList(rhs.trim().split("\\s"));
                weight = 1;
            } else {
                successors = Arrays.asList(rhsTokes[0].trim().split("\\s"));
                weight = Double.valueOf(rhsTokes[1].trim());
            }
        } else {
            throw new IllegalArgumentException("Cannot parse rool from line " + line);
        }
    }
}