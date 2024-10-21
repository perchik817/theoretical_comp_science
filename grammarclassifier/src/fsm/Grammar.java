package fsm;
import java.util.*;

class Grammar {
    private Set<String> nonTerminals;
    private Set<String> terminals;
    private Map<String, List<String>> productionRules;
    private String startSymbol;

    public Grammar(Set<String> nonTerminals, Set<String> terminals, Map<String, List<String>> productionRules, String startSymbol) {
        this.nonTerminals = nonTerminals;
        this.terminals = terminals;
        this.productionRules = productionRules;
        this.startSymbol = startSymbol;
    }

    // Проверка на регулярность
    public boolean isRegularGrammar() {
        for (Map.Entry<String, List<String>> entry : productionRules.entrySet()) {
            for (String production : entry.getValue()) {
                if (!isValidRegularProduction(production)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidRegularProduction(String production) {
        // Регулярное правило может быть в виде "aB" или просто "a" или "ε"
        if (production.equals("ε")) {
            return true;
        }
        if (production.length() == 1 && terminals.contains(production)) {
            return true;
        }
        if (production.length() == 2 && terminals.contains(Character.toString(production.charAt(0)))
                && nonTerminals.contains(Character.toString(production.charAt(1)))) {
            return true;
        }
        return false;
    }

    public void displayGrammar() {
        System.out.println("Non-terminals: " + nonTerminals);
        System.out.println("Terminals: " + terminals);
        System.out.println("Production Rules: ");
        for (String key : productionRules.keySet()) {
            System.out.println(key + " -> " + productionRules.get(key));
        }
        System.out.println("Start Symbol: " + startSymbol);
    }
}

