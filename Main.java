import java.util.Scanner;
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to SET game");
        SetGameConsole game = new SetGameConsole();
        String mode = null;

        while (mode == null) {
            System.out.println("Select game mode (Singleplayer,Multiplayer): ");
            String input = sc.nextLine();

            try {
                mode = checkGameMode(input);
                if (mode.equalsIgnoreCase("Singleplayer")) {
                    game.playConsoleSinglePlayer();
                } else if (mode.equalsIgnoreCase("Multiplayer")) {
                    game.playConsoleMultiPlayer();
                }
            } catch (InvalidGameModeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    private static String checkGameMode(String mode) throws InvalidGameModeException {
        if (mode.equalsIgnoreCase("Singleplayer") || mode.equalsIgnoreCase("Multiplayer")) {
            return mode;
        } else {
            throw new InvalidGameModeException("Invalid game mode. Please choose either 'Singleplayer' or 'Multiplayer'.");
        }
    }
}
