import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TMService {

    private List<String[]> table = new ArrayList<>();


    public void tmWork(char[] tape, List<Transition> transitions){

        int headPosition = tape.length - 2; // Указатель на последнюю цифру
        String currentState = "q0";
        while (!currentState.equals("halt")) {
            char currentSymbol = tape[headPosition];
            boolean transitionFound = false;

            for (Transition transition : transitions) {
                if (transition.currentState.equals(currentState) && transition.readSymbol == currentSymbol) {
                    // Выполнение перехода
                    table.add(new String[]{currentState, String.valueOf(currentSymbol), String.valueOf(transition.writeSymbol), String.valueOf(transition.moveDirection), transition.nextState});
                    tape[headPosition] = transition.writeSymbol; // Запись символа
                    headPosition += (transition.moveDirection == 'R' ? 1 : -1); // Движение указателя
                    currentState = transition.nextState; // Переход в следующее состояние
                    transitionFound = true;
                    break;
                }
            }

            if (!transitionFound) {
                System.out.println("Ошибка: переход не найден!");
                return;
            }
        }
    }

    public void printResult(char[] tape){
        System.out.println("Результат работы машины Тьюринга:");
        for (char cell : tape) {
            if (cell != '_') { // Игнорирование символов границы
                System.out.print(cell);
            }
        }
    }

    public void printTable(){

        System.out.println("\nТаблица переходов:");
        for (String[] row : table) {
            System.out.printf("%-20s %-20s %-20s %-20s %-20s%n", row[0], row[1], row[2], row[3], row[4]);
        }
    }

    private List<Transition> transitionList(){
        List<Transition> transitions = new ArrayList<>();
        // Переходы для состояния q0 (уменьшение)
        transitions.add(new Transition("q0", '0', '9', 'L', "q1"));
        transitions.add(new Transition("q0", '1', '_', 'H', "halt")); // Удаление последней единицы
        transitions.add(new Transition("q0", '2', '1', 'H', "halt"));
        transitions.add(new Transition("q0", '3', '2', 'H', "halt"));
        transitions.add(new Transition("q0", '4', '3', 'H', "halt"));
        transitions.add(new Transition("q0", '5', '4', 'H', "halt"));
        transitions.add(new Transition("q0", '6', '5', 'H', "halt"));
        transitions.add(new Transition("q0", '7', '6', 'H', "halt"));
        transitions.add(new Transition("q0", '8', '7', 'H', "halt"));
        transitions.add(new Transition("q0", '9', '8', 'H', "halt"));

        // Переходы для состояния q1 (заимствование)
        transitions.add(new Transition("q1", '1', '0', 'L', "q1"));
        transitions.add(new Transition("q1", '0', '9', 'L', "q1"));
        transitions.add(new Transition("q1", '_', '_', 'R', "q2"));

        // Переходы для состояния q2 (завершение)
        transitions.add(new Transition("q2", '_', '_', 'H', "halt")); // Завершение на символе '_'
        transitions.add(new Transition("q2", '0', '_', 'H', "halt")); // Удаление ведущих нулей
        return transitions;
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите натуральное число для уменьшения: ");
        String input = scanner.nextLine();

        if (!input.matches("\\d+") || input.equals("0")) {
            System.out.println("Ошибка: введите натуральное число больше нуля.");
            System.exit(0);
        }
        char[] tape = ("_" + input + "_").toCharArray();
        List<Transition> transitions = transitionList();
        table.add(new String[]{"Текущее состояние", "Символ на ленте", "Новый символ", "Направление", "Следующее состояние"});
        tmWork(tape, transitions);
        printResult(tape);
        printTable();
    }
}
