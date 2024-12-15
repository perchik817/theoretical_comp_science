public class Transition {
    String currentState;
    char readSymbol;
    char writeSymbol;
    char moveDirection; // 'L' - влево, 'R' - вправо
    String nextState;

    Transition(String currentState, char readSymbol, char writeSymbol, char moveDirection, String nextState) {
        this.currentState = currentState;
        this.readSymbol = readSymbol;
        this.writeSymbol = writeSymbol;
        this.moveDirection = moveDirection;
        this.nextState = nextState;
    }
}
