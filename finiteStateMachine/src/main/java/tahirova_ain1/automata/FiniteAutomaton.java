package tahirova_ain1.automata;

import java.util.*;

public class FiniteAutomaton {
    private Set<String> states;
    private Set<String> alphabet;
    private String startState;
    private Set<String> finalStates;
    private Map<String, Map<String, Set<String>>> transitions;

    public FiniteAutomaton(Set<String> states, Set<String> alphabet, String startState) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();
    }

    public void addTransition(String fromState, String symbol, String toState) {
        transitions.putIfAbsent(fromState, new HashMap<>());
        transitions.get(fromState).putIfAbsent(symbol, new HashSet<>());
        transitions.get(fromState).get(symbol).add(toState);
    }

    public void addFinalState(String state) {
        finalStates.add(state);
    }

    public void displayAutomaton() {
        System.out.println("Finite Automaton:");
        System.out.println("States: " + states);
        System.out.println("Alphabet: " + alphabet);
        System.out.println("Start State: " + startState);
        System.out.println("Final States: " + finalStates);
        System.out.println("Transitions:");
        for (Map.Entry<String, Map<String, Set<String>>> stateTransitions : transitions.entrySet()) {
            String state = stateTransitions.getKey();
            for (Map.Entry<String, Set<String>> transition : stateTransitions.getValue().entrySet()) {
                String targetStates = transition.getValue().toString();
                if (targetStates.contains("final")) {
                    targetStates = targetStates.replace("final", "final");
                }
                System.out.println(state + " --" + transition.getKey() + "--> " + targetStates);
            }
        }
    }

    public void printTransitionTable() {
        System.out.printf("%-10s", "States");
        for (String symbol : alphabet) {
            System.out.printf("%-10s", symbol);
        }
        System.out.println();

        for (String state : states) {
            System.out.printf("%-10s", state);
            Map<String, Set<String>> stateTransitions = transitions.get(state);
            for (String symbol : alphabet) {
                // Получаем следующее состояние для данного символа
                Set<String> nextStates = stateTransitions.getOrDefault(symbol, new HashSet<>());
                // Если нет переходов, выводим пустое значение
                System.out.printf("%-10s", nextStates.isEmpty() ? "∅" : String.join(", ", nextStates));
            }
            System.out.println();
        }
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public String getStartState() {
        return startState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public Map<String, Map<String, Set<String>>> getTransitions() {
        return transitions;
    }
}
