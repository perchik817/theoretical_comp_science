package tahirova_ain1.grammar;

public class ProductionRule {
    private final String nonTerminal;
    private final String production;

    public ProductionRule(String nonTerminal, String production) {
        this.nonTerminal = nonTerminal;
        this.production = production;
    }

    public boolean isRegular() {
        // A production is regular if it is in the form of A -> aB or A -> a
        return production.matches("[a-zA-Z](\\w)?|ε");
    }

    public String getNonTerminal() {
        return nonTerminal;
    }

    public String getProduction() {
        return production;
    }
}
