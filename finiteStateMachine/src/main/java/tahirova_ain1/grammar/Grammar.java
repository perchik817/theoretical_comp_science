package tahirova_ain1.grammar;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Grammar {
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private Map<String, List<String>> productions;
    private String startSymbol;

    public Grammar(Set<String> nonTerminals, Set<String> terminals, Map<String, List<String>> productions, String startSymbol) {
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productions = productions;
        this.startSymbol = startSymbol;
    }

    public boolean isRegular() {
        for (String left : productions.keySet()) {
            if (!nonTerminals.contains(left) || left.length() != 1) {
                return false;
            }
        }

        for (Map.Entry<String, List<String>> entry : productions.entrySet()) {
            for (String right : entry.getValue()) {
                if (right.equals("ε")) {
                    continue;
                }
                if (right.length() > 2) {
                    return false;
                }
                if (right.length() == 2) {
                    char firstChar = right.charAt(0);
                    char secondChar = right.charAt(1);
                    if (!terminals.contains(String.valueOf(firstChar)) || !nonTerminals.contains(String.valueOf(secondChar))) {
                        return false;
                    }
                }
                if (right.length() == 1 && !terminals.contains(right)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Set<String> getNonTerminals() {
        return nonTerminals;
    }

    public Set<String> getTerminals() {
        return terminals;
    }

    public Map<String, List<String>> getProductions() {
        return productions;
    }

    public String getStartSymbol() {
        return startSymbol;
    }
}



/*public class Grammar {
    private String input;

    public Grammar(String input) {
        this.input = input;
    }

    public boolean isRegular() {
        String[] productions = input.split("\n");
        // Регулярное выражение для проверки правил
        String regex = "[A-Z]→\\((([A-Z]|ε)\\|?)|\\)";

        for (String production : productions) {
            Matcher matcher = Pattern.compile(regex).matcher(production);
            if (!matcher.find()) {
                return false; // Если не соответствует, грамматика не регулярная
            }
        }
        return true; // Если все правила прошли проверку
    }
}*/