package tahirova_ain1.service;

import tahirova_ain1.automata.DeterministicFiniteAutomaton;
import tahirova_ain1.automata.FiniteAutomaton;
import tahirova_ain1.grammar.Grammar;

import java.util.*;

public class FiniteAutomataService {
    public FiniteAutomaton buildFiniteAutomaton(Grammar grammar) {
        FiniteAutomaton automaton = new FiniteAutomaton(grammar.getNonTerminals(), grammar.getTerminals(),
                grammar.getStartSymbol());

        for (Map.Entry<String, List<String>> entry : grammar.getProductions().entrySet()) {
            String currentState = entry.getKey();

            for (String production : entry.getValue()) {
                if (production.equals("ε")) {
                    automaton.addFinalState(currentState);
                } else {
                    String terminal = String.valueOf(production.charAt(0));
                    String nextState = production.length() > 1 ? String.valueOf(production.charAt(1)) : "final";
                    if (production.length() == 1) {
                        automaton.addFinalState("ε");
                    }
                    automaton.addTransition(currentState, terminal, nextState);
                }
            }
        }

        return automaton;
    }

    public DeterministicFiniteAutomaton toDeterministic(FiniteAutomaton finiteAutomaton) {
        Set<Set<String>> dfaStates = new HashSet<>();
        Queue<Set<String>> unprocessedStates = new LinkedList<>();
        Map<Set<String>, String> stateNames = new HashMap<>();

        Set<String> startSet = new HashSet<>();
        startSet.add(finiteAutomaton.getStartState());
        unprocessedStates.add(startSet);
        dfaStates.add(startSet);
        stateNames.put(startSet, "{" + String.join(",", startSet) + "}");

        DeterministicFiniteAutomaton dfa = new DeterministicFiniteAutomaton(new HashSet<>(),
                finiteAutomaton.getAlphabet(),
                stateNames.get(startSet));

        while (!unprocessedStates.isEmpty()) {
            Set<String> currentSet = unprocessedStates.poll();
            String currentStateName = stateNames.get(currentSet);
            dfa.addState(currentStateName);

            for (String symbol : finiteAutomaton.getAlphabet()) {
                Set<String> newSet = new HashSet<>();
                for (String state : currentSet) {
                    Set<String> nextStates = finiteAutomaton.getTransitions().getOrDefault(state, new HashMap<>()).get(symbol);
                    if (nextStates != null) {
                        newSet.addAll(nextStates);
                    }
                }

                if (!newSet.isEmpty()) {
                    if (!dfaStates.contains(newSet)) {
                        dfaStates.add(newSet);
                        unprocessedStates.add(newSet);
                        stateNames.put(newSet, "{" + String.join(",", newSet) + "}");
                    }
                    dfa.addTransition(currentStateName, symbol, stateNames.get(newSet));
                }

                if (!Collections.disjoint(newSet, finiteAutomaton.getFinalStates())) {
                    dfa.addFinalState(stateNames.get(newSet));
                }
            }
        }

        return dfa;
    }
}
