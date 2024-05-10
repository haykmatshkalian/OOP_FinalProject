package SetGameFinal;

import SetGameFinal.console.SetGameConsole;
import SetGameFinal.ui.PromptInfo;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            SetGameConsole setGame = new SetGameConsole();
            setGame.startGame();
        } else if (args[0].equals("-ui")) {
            PromptInfo game = new PromptInfo();
            game.setVisible(true);
        }
    }
}
