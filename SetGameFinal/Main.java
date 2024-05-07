package SetGameFinal;

import SetGameFinal.console.SetGameConsole;
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            SetGameConsole setGame = new SetGameConsole();
            setGame.startGame();
        }
    }
}
