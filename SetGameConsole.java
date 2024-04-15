import java.util.Arrays;
import java.util.Scanner;

public class SetGameConsole {
    private final Board setGame = new Board(new Deck());
    private final Player player = new Player();
    private final Scanner sc = new Scanner(System.in);
    private GameTimer gameTimer = new GameTimer(60); // 60 seconds timer

    public void playConsole() {
        gameTimer.startTimer();
        Player player1 = new Player(player);
        while (true) {
            System.out.println("Current board:");
            setGame.printBoard();
            System.out.println("Enter indices of rows forming a set");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("hint")) {
                System.out.println(Arrays.toString(setGame.hint()));
            }
            if (input.equalsIgnoreCase("add")) {
                setGame.addRow();
            }
            String[] indicesStr = input.split(" ");
            int[] indices = new int[3];
            if (textInput(indicesStr[0])) {
                try {
                    for (int i = 0; i < 3; i++) {
                        indices[i] = Integer.parseInt(indicesStr[i]) - 1;
                    }
                    if (setGame.isSet(indices)) {
                        System.out.println("Valid set!");
                        successfulSet(player1);
                        System.out.println("Your score is: " + player1.getScore());
                        setGame.replaceCards(indices);
                    } else {
                        System.out.println("Not a valid set.");
                    }
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    System.out.println("Invalid input. Please enter three valid indices separated by spaces.");
                }
            }
        }
    }
    private boolean textInput(String input){
        return !input.equalsIgnoreCase("add") && !input.equalsIgnoreCase("hint");
    }
    private void successfulSet(Player play){
        play.addScore();
        gameTimer.addTime(10);
    }
    private void setDifficultyLevel(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                gameTimer = new GameTimer(30);
                break;
            case "medium":
                gameTimer = new GameTimer(45);
                break;
            case "hard":
                gameTimer = new GameTimer(60);
                break;
            default:
                System.out.println("Invalid difficulty level. Using default time limit.");
                gameTimer = new GameTimer(60); // Default to hard if input is invalid
        }
    }

    public static void main(String[] args) {
        SetGameConsole setGameConsole = new SetGameConsole();
        setGameConsole.promptDifficultyLevel(); // Prompt user to select the difficulty level
        setGameConsole.playConsole();
    }

    private void promptDifficultyLevel() {
        System.out.println("Welcome to Set Game! Select the difficulty level (easy, medium, hard):");
        String input = sc.nextLine();
        setDifficultyLevel(input);
    }
}
