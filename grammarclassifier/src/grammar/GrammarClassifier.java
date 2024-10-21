package grammar;

import java.util.ArrayList;
import java.util.Scanner;

public class GrammarClassifier {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> rules = new ArrayList<>();

        System.out.println("Введите правила грамматики (введите пустую строку для завершения ввода):");

        // Ввод правил грамматики
        while (true) {
            String rule = scanner.nextLine();
            if (rule.isEmpty()) {
                break;
            }
            rules.add(rule);
        }

        // Определение типа грамматики
        int grammarType = determineGrammarType(rules);

        // Вывод результата
        switch (grammarType) {
            case 3:
                System.out.println("Грамматика является регулярной (Тип 3).");
                break;
            case 2:
                System.out.println("Грамматика является контекстно-свободной (Тип 2).");
                break;
            case 1:
                System.out.println("Грамматика является контекстно-зависимой (Тип 1).");
                break;
            case 0:
                System.out.println("Грамматика является рекурсивно-неограниченной (Тип 0).");
                break;
            default:
                System.out.println("Не удалось определить тип грамматики.");
        }
    }

    public static int determineGrammarType(ArrayList<String> rules) {
        boolean isType3 = true;  // Регулярная грамматика
        boolean isType2 = true;  // Контекстно-свободная грамматика
        boolean isType1 = true;  // Контекстно-зависимая грамматика

        for (String rule : rules) {
            String[] parts = rule.split("->");
            if (parts.length != 2) {
                System.out.println("Неправильный формат правила: " + rule);
                return -1;
            }

            String left = parts[0].trim();
            String right = parts[1].trim();

            // Проверка на регулярную грамматику (Тип 3)
            if (left.length() != 1 || !Character.isUpperCase(left.charAt(0))) {
                isType3 = false;  // Нетерминал слева должен быть один
            }
            if (right.length() > 2 || (right.length() == 2 && !Character.isLowerCase(right.charAt(1)))) {
                isType3 = false;  // Тип 3: A -> aB или A -> a
            }

            // Проверка на контекстно-свободную грамматику (Тип 2)
            if (left.length() != 1 || !Character.isUpperCase(left.charAt(0))) {
                isType2 = false;  // Слева должен быть один нетерминал
            } else {
                // Проверяем, что правые части могут содержать любые терминалы и нетерминалы
                for (char ch : right.toCharArray()) {
                    if (!Character.isLowerCase(ch) && !Character.isUpperCase(ch) && ch != 'ε' && ch != '.') {
                        isType2 = false; // Правые части должны содержать только терминалы и нетерминалы
                    }
                }
            }

            // Проверка на контекстно-зависимую грамматику (Тип 1)
            if (right.length() < left.length() && !right.equals("ε")) {
                isType1 = false;  // Длина правой части должна быть >= длины левой части (кроме ε)
            }

            // Проверка на наличие пустых правил
            if (right.equals("ε")) {
                if (left.length() != 1) {
                    isType1 = false;  // Пустые правила допустимы только для Типа 0
                }
                isType2 = false; // Пустые правила недопустимы для контекстно-свободной грамматики
            }
        }

        // Итоговая классификация
        if (isType3) return 3;
        if (isType2) return 2;
        if (isType1) return 1;
        return 0;  // Если не соответствует более строгим типам, то это грамматика типа 0
    }

}

    /*
S -> aSBC
S -> abc
bC -> bc
    Грамматика является контекстно-зависимой (Тип 1).

    S->aaCFD
    AD->D
    F->AFB
    F->AB
    Cb->bC
    AB->bBA
    CB->C
    Ab->bA
    bCD->ε

    Грамматика является рекурсивно-неограниченной (Тип 0).


S -> AB.B
A -> +
A -> -
B -> bB
B -> ε
b -> 0
b -> 1
b -> 2
b -> 3
b -> 4
b -> 5
b -> 6
b -> 7
b -> 8
b -> 9
    */