package fsm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод состояний грамматики
        System.out.println("Введите множества состояний (через запятую): ");
        String[] stateArray = scanner.nextLine().split(",");
        Set<String> states = new HashSet<>();
        for (String state : stateArray) {
            states.add(state.trim());
        }

        // Ввод алфавита грамматики
        System.out.println("Введите символы алфавита (через запятую): ");
        String[] alphabetArray = scanner.nextLine().split(",");
        Set<String> alphabet = new HashSet<>();
        for (String symbol : alphabetArray) {
            alphabet.add(symbol.trim());
        }

        // Ввод начального состояния
        System.out.println("Введите начальное состояние: ");
        String startState = scanner.nextLine().trim();

        // Ввод принимающих состояний
        System.out.println("Введите принимающие состояния (через запятую): ");
        String[] acceptStatesArray = scanner.nextLine().split(",");
        Set<String> acceptStates = new HashSet<>();
        for (String acceptState : acceptStatesArray) {
            acceptStates.add(acceptState.trim());
        }

        // Ввод правил переходов
        Map<String, Map<String, String>> transitions = new HashMap<>();
        System.out.println("Введите правила переходов в формате 'ТекущееСостояние,Символ -> СледующееСостояние'. Введите 'стоп' для завершения ввода:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("стоп")) {
                break;
            }
            String[] parts = input.split("->");
            if (parts.length != 2) {
                System.out.println("Неверный формат. Попробуйте снова.");
                continue;
            }

            String[] stateAndSymbol = parts[0].trim().split(",");
            if (stateAndSymbol.length != 2) {
                System.out.println("Неверный формат. Попробуйте снова.");
                continue;
            }

            String currentState = stateAndSymbol[0].trim();
            String symbol = stateAndSymbol[1].trim();
            String nextState = parts[1].trim();

            transitions.putIfAbsent(currentState, new HashMap<>());
            transitions.get(currentState).put(symbol, nextState);
        }

        // Создание объекта DFA
        DFA dfa = new DFA(states, alphabet, transitions, startState, acceptStates);

        // Визуализация DFA
        AutomatonVisualizer.visualizeDFA(dfa);

        scanner.close();
    }
}
