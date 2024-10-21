package fsm;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class AutomatonVisualizer {
    public static void visualizeDFA(DFA dfa) {
        System.setProperty("org.graphstream.ui", "swing"); // Добавляем эту строку для настройки дисплея

        Graph graph = new SingleGraph("DFA");

        for (String state : dfa.getStates()) {
            graph.addNode(state).setAttribute("ui.label", state);
        }

        for (String state : dfa.getTransitions().keySet()) {
            for (String symbol : dfa.getTransitions().get(state).keySet()) {
                String nextState = dfa.getTransitions().get(state).get(symbol);
                graph.addEdge(state + symbol + nextState, state, nextState, true)
                        .setAttribute("ui.label", symbol);
            }
        }

        graph.display();
    }
}
