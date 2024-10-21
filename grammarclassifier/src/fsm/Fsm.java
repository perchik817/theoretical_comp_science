//package fsm;
//
//
//import java.util.Map;
//import java.util.Set;
//
//public class Fsm {
//    private String alphabet;
//    private Set<String> states;
//    private String startState;
//    private Set<String> finiteStates;
//    private Map<String, Map<Character, String>> transitions;//переходы
//    private String currentState;
//
//    public Fsm(String alphabet, Set<String> states, String startState, Set<String> finiteStates, Map<String,
//            Map<Character, String>> transitions) {
//        this.alphabet = alphabet;
//        this.states = states;
//        this.startState = startState;
//        this.finiteStates = finiteStates;
//        this.transitions = transitions;
//        this.currentState = null;
//    }
//
//    public boolean isBelongToAlphabet(char symbol) {
//        return alphabet.contains(String.valueOf(symbol));
//    }
//
//    public void changeState(char symbol) {
//        if (transitions.containsKey(currentState) && transitions.get(currentState).containsKey(symbol)) {
//            currentState = transitions.get(currentState).get(symbol);
//        } else {
//            throw new RuntimeException("No transition from " + currentState + " by " + symbol);
//        }
//    }
//
//    public boolean test(String value) {
//        currentState = startState;
//        for (char symbol : value.toCharArray()) {
//            if (isBelongToAlphabet(symbol)) {
//                changeState(symbol);
//                try {
//                    changeState(symbol);
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        }
//
//        return finiteStates.contains(currentState);
//    }
//
//    public static void main(String[] args) {
//        // Пример использования
//        Set<String> states = Set.of("q0", "q1", "q2");
//        Set<String> finiteStates = Set.of("q2");
//        Map<String, Map<Character, String>> transitions = Map.of(
//                "q0", Map.of('a', "q1"),
//                "q1", Map.of('b', "q2"),
//                "q2", Map.of('a', "q1")
//        );
//
//        Fsm fsm = new Fsm("ab", states, "q0", finiteStates, transitions);
//
//        System.out.println(fsm.test("aba")); // true
//        System.out.println(fsm.test("ab"));  // true
//        System.out.println(fsm.test("abc")); // false
//    }
//}