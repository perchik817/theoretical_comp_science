package tahirova_ain1.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DeterministicFiniteAutomaton {
    private Set<String> states;
    private Set<String> alphabet;
    private String startState;
    private Set<String> finalStates;
    private Map<String, Map<String, String>> transitions; // Используем String для однозначных переходов

    public DeterministicFiniteAutomaton(Set<String> states, Set<String> alphabet, String startState) {
        this.states = new HashSet<>(states);
        this.alphabet = new HashSet<>(alphabet);
        this.startState = startState;
        this.finalStates = new HashSet<>();
        this.transitions = new HashMap<>();
    }

    public void addState(String state) {
        states.add(state);
    }

    public void addTransition(String fromState, String symbol, String toState) {
        transitions.putIfAbsent(fromState, new HashMap<>());
        transitions.get(fromState).put(symbol, toState);
    }

    public void addFinalState(String state) {
        finalStates.add(state);
    }

    public void displayAutomaton() {
        System.out.println("Deterministic Finite Automaton (DFA):");
        System.out.println("States: " + states);
        System.out.println("Alphabet: " + alphabet);
        System.out.println("Start State: " + startState);
        System.out.println("Final States: " + finalStates);
        System.out.println("Transitions:");
        for (Map.Entry<String, Map<String, String>> stateTransitions : transitions.entrySet()) {
            String state = stateTransitions.getKey();
            for (Map.Entry<String, String> transition : stateTransitions.getValue().entrySet()) {
                System.out.println(state + " --" + transition.getKey() + "--> " + transition.getValue());
            }
        }
    }
}
