package fsm;

import java.util.*;

public class DFA {
    private Set<String> states;
    private Set<String> alphabet;
    private Map<String, Map<String, String>> transitions;
    private String startState;
    private Set<String> acceptStates;

    public DFA(Set<String> states, Set<String> alphabet, Map<String, Map<String, String>> transitions, String startState, Set<String> acceptStates) {
        this.states = states;
        this.alphabet = alphabet;
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.transitions = new HashMap<>();
    }

    public void addTransition(String fromState, String input, String toState) {
        transitions.putIfAbsent(fromState, new HashMap<>());
        transitions.get(fromState).put(input, toState);
    }

    public void displayDFA() {
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

    public static DFA convertNFAtoDFA(NFA nfa) {
        Set<String> dfaStates = new HashSet<>();
        Set<String> dfaAcceptStates = new HashSet<>();
        Map<Set<String>, String> stateMapping = new HashMap<>(); // Mapping from NFA states set to DFA state
        Queue<Set<String>> queue = new LinkedList<>();
        Map<String, Map<String, String>> dfaTransitions = new HashMap<>();

        // Начальное состояние ДКА - это начальное состояние НКА
        Set<String> startSet = new HashSet<>();
        startSet.add(nfa.getStartState());
        queue.add(startSet);
        stateMapping.put(startSet, startSet.toString());
        dfaStates.add(startSet.toString());

        while (!queue.isEmpty()) {
            Set<String> currentSet = queue.poll();
            String currentDFAState = stateMapping.get(currentSet);
            Map<String, String> currentTransitions = new HashMap<>();

            for (String symbol : nfa.getAlphabet()) {
                Set<String> newSet = new HashSet<>();
                for (String state : currentSet) {
                    Map<String, Set<String>> stateTransitions = nfa.getTransitions().get(state);
                    if (stateTransitions != null && stateTransitions.containsKey(symbol)) {
                        newSet.addAll(stateTransitions.get(symbol));
                    }
                }

                if (!newSet.isEmpty()) {
                    if (!stateMapping.containsKey(newSet)) {
                        stateMapping.put(newSet, newSet.toString());
                        queue.add(newSet);
                        dfaStates.add(newSet.toString());

                        // Проверяем, является ли новое состояние принимающим
                        for (String state : newSet) {
                            if (nfa.getAcceptStates().contains(state)) {
                                dfaAcceptStates.add(newSet.toString());
                                break;
                            }
                        }
                    }
                    currentTransitions.put(symbol, newSet.toString());
                }
            }
            dfaTransitions.put(currentDFAState, currentTransitions);
        }

        // Создаем новый DFA на основе полученных данных
        return new DFA(dfaStates, nfa.getAlphabet(), dfaTransitions, stateMapping.get(startSet), dfaAcceptStates);
    }

    public Set<String> getStates() {
        return states;
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public Map<String, Map<String, String>> getTransitions() {
        return transitions;
    }

    public String getStartState() {
        return startState;
    }

    public Set<String> getAcceptStates() {
        return acceptStates;
    }
}

