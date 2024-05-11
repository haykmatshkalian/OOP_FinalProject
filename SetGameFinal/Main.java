package SetGameFinal;

import SetGameFinal.console.SetGameConsole;
import SetGameFinal.ui.PromptInfo;

/**
 * The Main class serves as the entry point for the Set Game application.
 * It provides functionality for launching the game either through a console interface
 * or a graphical user interface (UI), depending on the command-line arguments passed.
 */
public class Main {
    /**
     * The main method of the application. It checks the command-line arguments
     * and launches the appropriate interface for the Set Game.
     *
     * @param args An array of command-line arguments. If no arguments are provided,
     *             the game is launched via console interface. If the argument "-ui"
     *             is provided, the game is launched via a graphical user interface.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            // Launches the game through the console interface
            SetGameConsole setGame = new SetGameConsole();
            setGame.startGame();
        } else if (args[0].equals("-ui")) {
            // Launches the game through the graphical user interface
            PromptInfo game = new PromptInfo();
            game.setVisible(true);
        }
    }
}
