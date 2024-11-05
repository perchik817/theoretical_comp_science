package tahirova_ain1;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

public class FiniteAutomaton {
    private static final String[] STATES = {"S0", "S1", "S2"};
    private static final String[] INPUTS = {"x0", "x1", "x2", "x3", "x4"};
    private static final String[] OUTPUTS = {"y0", "y1", "y2"};

    //[текущее состояние][входной сигнал] -> следующее состояние
    private static final int[][] TRANSITION_TABLE = {
            {1, 2, 0, 0, 1}, // S0
            {0, 2, 1, 2, 0}, // S1
            {1, 2, 1, 2, 0}  // S2
    };

    //[состояние] -> выходной сигнал
    private static final int[] OUTPUT_TABLE = {0, 1, 2}; // y0 для S0, y1 для S1, y2 для S2

    public static void main(String[] args) {
        System.out.println("Таблица переходов:");
        printTransitionTable();
        System.out.println("\nТаблица выходов:");
        printOutputTable();
        System.out.println("\nМатрица соединений:");
        printConnectionMatrix();

        displayGraph();
    }

    private static void printTransitionTable() {
        System.out.print("Текущее состояние\t");
        for (String input : INPUTS) {
            System.out.printf("%-16s", input);
        }
        System.out.println();

        for (int currentState = 0; currentState < STATES.length; currentState++) {
            System.out.printf("%-20s", STATES[currentState]);
            for (int input = 0; input < INPUTS.length; input++) {
                int nextState = TRANSITION_TABLE[currentState][input];
                System.out.printf("%-16s", STATES[nextState]);
            }
            System.out.println();
        }
    }

    private static void printOutputTable() {
        System.out.printf("%-16s%-10s%n", "Состояние", "Выход");
        for (int currentState = 0; currentState < STATES.length; currentState++) {
            String outputSignal = OUTPUTS[OUTPUT_TABLE[currentState]];
            System.out.printf("%-16s%-10s%n", STATES[currentState], outputSignal);
        }
    }

    private static void printConnectionMatrix() {
        System.out.print("       ");
        for (String input : INPUTS) {
            System.out.printf("%-15s", input);
        }
        System.out.println();

        for (int currentState = 0; currentState < STATES.length; currentState++) {
            System.out.printf("%-5s", STATES[currentState]);

            for (int input = 0; input < INPUTS.length; input++) {
                int nextState = TRANSITION_TABLE[currentState][input];
                String outputSignal = OUTPUTS[OUTPUT_TABLE[nextState]];

                System.out.printf("(%d / %s, %s)  ", nextState, INPUTS[input], outputSignal);
            }
            System.out.println();
        }
    }

    private static void displayGraph() {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("FiniteAutomaton", false, true);

        for (int i = 0; i < STATES.length; i++) {
            graph.addNode(STATES[i]).setAttribute("ui.label", STATES[i] + " / " + OUTPUTS[i]);
            if (i == 0) {
                graph.getNode(STATES[i]).setAttribute("ui.class", "initial");
            }
        }

        int initialOffset = 10;
        int horizontalIncrement = 15;
        int verticalIncrement = 5;

        for (int currentState = 0; currentState < STATES.length; currentState++) {
            for (int input = 0; input < INPUTS.length; input++) {
                int nextState = TRANSITION_TABLE[currentState][input];
                String fromState = STATES[currentState];
                String toState = STATES[nextState];
                String edgeId = fromState + "-" + INPUTS[input] + "-" + currentState + "-" + input;

                if (graph.getEdge(edgeId) == null) {
                    Edge edge = graph.addEdge(edgeId, fromState, toState, true);
                    if (edge != null) {
                        String label = INPUTS[input] + " / " + OUTPUTS[OUTPUT_TABLE[nextState]];
                        edge.setAttribute("ui.label", label);

                        // Alternate label position between above and below, with increasing offset
                        if ((currentState + input) % 2 == 0) {
                            edge.setAttribute("ui.style", "text-alignment: above; text-offset: " + initialOffset + "px, " + verticalIncrement + "px;");
                        } else {
                            edge.setAttribute("ui.style", "text-alignment: under; text-offset: " + (initialOffset + horizontalIncrement) + "px, -" + verticalIncrement + "px;");
                        }

                        initialOffset += horizontalIncrement;
                        verticalIncrement += 5;
                    }
                }
            }
        }

        graph.setAttribute("ui.stylesheet",
                "node { " +
                        "fill-color: lightblue;" +
                        "size: 40px;" +
                        "text-size: 16;" +
                        "text-color: black;" +
                        "text-background-mode: plain;" +
                        "text-padding: 10;" +
                        "}" +
                        "node.initial { " +
                        "fill-color: lightgreen;" +
                        "}" +
                        "edge {" +
                        "text-size: 14;" +
                        "text-color: black;" +
                        "arrow-size: 12px, 8px;" +
                        "text-offset: 15, 25;" +
                        "text-background-mode: plain;" +
                        "text-padding: 5;" +
                        "}");

        graph.display().enableAutoLayout();
    }
}
