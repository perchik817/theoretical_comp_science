package tahirova_ain1;

import tahirova_ain1.automata.DeterministicFiniteAutomaton;
import tahirova_ain1.service.FiniteAutomataService;
import tahirova_ain1.automata.FiniteAutomaton;
import tahirova_ain1.grammar.Grammar;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите множество нетерминалов (через запятую):");
        Set<String> nonTerminals = new HashSet<>(Arrays.asList(scanner.nextLine().trim().split("\\s*,\\s*")));

        System.out.println("Введите множество терминалов (через запятую):");
        Set<String> terminals = new HashSet<>(Arrays.asList(scanner.nextLine().trim().split("\\s*,\\s*")));

        System.out.println("Введите начальный символ:");
        String startSymbol = scanner.nextLine().trim();

        System.out.println("Введите правила продукции (в формате: A -> aB, одно на строку, пустая строка для завершения):");
        Map<String, List<String>> productions = new HashMap<>();

        while (true) {
            String production = scanner.nextLine().trim();
            if (production.isEmpty()) {
                break;
            }

            // Разделение левой и правой части продукции
            String[] parts = production.split("->");
            if (parts.length != 2) {
                System.out.println("Ошибка ввода! Правило должно быть в формате: A -> aB");
                continue;
            }

            String left = parts[0].trim();
            String[] rightParts = parts[1].trim().split("\\|");

            productions.putIfAbsent(left, new ArrayList<>());
            for (String right : rightParts) {
                productions.get(left).add(right.trim());
            }
        }
        Grammar grammar = new Grammar(nonTerminals, terminals, productions, startSymbol);

        if (grammar.isRegular()) {
            System.out.println("Грамматика является регулярной.");

            FiniteAutomataService finiteAutomataService = new FiniteAutomataService();
            FiniteAutomaton nfa = finiteAutomataService.buildFiniteAutomaton(grammar);
            nfa.displayAutomaton();
            System.out.println("------------Transition table------------");
            nfa.printTransitionTable();

            // Преобразование НКА в ДКА
            DeterministicFiniteAutomaton dfa = finiteAutomataService.toDeterministic(nfa);
            dfa.displayAutomaton();

            //new AutomatonGraph().createGraph();

        } else {
            System.out.println("Грамматика не является регулярной.");
        }
    }
}
/*
X,Y,Z,V

(,),y,z,v

X->(Y|ε
Y->yY|zY|zZ
Z->zZ|vZ|vV
V->vV|)

X
 */