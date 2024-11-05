package tahirova_ain1;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import java.util.HashMap;
import java.util.Map;

public class Moore {
    // Состояния автомата
    static String[] states = {"S0", "S1", "S2", "S3"};

    // Выходные сигналы для каждого состояния
    static Map<String, String> outputSignals = new HashMap<>();

    static {
        outputSignals.put("S0", "W0");
        outputSignals.put("S1", "W1");
        outputSignals.put("S2", "W2");
        outputSignals.put("S3", "W0");
    }

    // Определение таблицы переходов: из какого состояния в какое состояние переходить при каждом входном сигнале
    static Map<String, Map<String, String>> transitionTable = new HashMap<>();

    static {
        transitionTable.put("S0", Map.of("p1", "S0", "p2", "S1"));
        transitionTable.put("S1", Map.of("p1", "S2", "p2", "S3"));
        transitionTable.put("S2", Map.of("p1", "S3", "p2", "S2"));
        transitionTable.put("S3", Map.of("p1", "S3", "p2", "S0"));
    }

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        // Визуализация графа переходов
        Graph graph = new SingleGraph("Moore Machine Transition Graph");

        // Добавляем вершины с метками выходных сигналов
        for (String state : states) {
            Node node = graph.addNode(state);
            node.setAttribute("ui.label", state + " / " + outputSignals.get(state));
        }

        // Добавляем рёбра (переходы) с метками входных сигналов
        for (String state : transitionTable.keySet()) {
            Map<String, String> transitions = transitionTable.get(state);
            for (String input : transitions.keySet()) {
                String nextState = transitions.get(input);
                String edgeId = state + "-" + input + "-" + nextState;
                graph.addEdge(edgeId, state, nextState, true)
                        .setAttribute("ui.label", input);
            }
        }

        // Стилизация графа
        graph.setAttribute("ui.stylesheet", "node { fill-color: lightblue; size: 30px; } "
                + "edge { text-size: 16; text-color: black; }");
        graph.display();

        // Вывод таблицы переходов
        printTransitionTable();
    }

    // Метод для форматированного вывода таблицы переходов
    // Метод для форматированного вывода таблицы переходов с выходными сигналами
    private static void printTransitionTable() {
        System.out.println("Таблица переходов и выходов автомата Мура:");
        System.out.println("+-------------+-------------+-------------+-------------+");
        System.out.println("| Выход       | Текущее     | Вход p1     | Вход p2     |");
        System.out.println("+-------------+-------------+-------------+-------------+");

        for (String state : states) {
            String output = outputSignals.get(state);  // Получаем выходной сигнал для текущего состояния
            String p1Transition = transitionTable.get(state).get("p1");  // Переход при входе p1
            String p2Transition = transitionTable.get(state).get("p2");  // Переход при входе p2

            // Форматированный вывод строки таблицы
            System.out.printf("| %-11s | %-11s | %-11s | %-11s |\n", output, state, p1Transition, p2Transition);
        }

        System.out.println("+-------------+-------------+-------------+-------------+");
    }

}
