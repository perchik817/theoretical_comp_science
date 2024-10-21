package fsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NFA {
    private Set<String> states;
    private Set<String> alphabet;
    private Map<String, Map<String, Set<String>>> transitions;
    private String startState;
    private Set<String> acceptStates;

    public NFA(Set<String> states, Set<String> alphabet, String startState, Set<String> acceptStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.transitions = new HashMap<>();
    }

    public void addTransition(String fromState, String input, String toState) {
        transitions.putIfAbsent(fromState, new HashMap<>());
        transitions.get(fromState).putIfAbsent(input, new HashSet<>());
        transitions.get(fromState).get(input).add(toState);
    }

    public void displayNFA() {
        System.out.println("States: " + states);
        System.out.println("Alphabet: " + alphabet);
        System.out.println("Transitions: ");
        for (String state : transitions.keySet()) {
            System.out.println("From State: " + state);
            for (String input : transitions.get(state).keySet()) {
                System.out.println("  On Input '" + input + "' -> " + transitions.get(state).get(input));
            }
        }
        System.out.println("Start State: " + startState);
        System.out.println("Accept States: " + acceptStates);
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Map<String, Map<String, Set<String>>> getTransitions() {
        return transitions;
    }

    public String getStartState() {
        return startState;
    }

    public Set<String> getAcceptStates() {
        return acceptStates;
    }
}

