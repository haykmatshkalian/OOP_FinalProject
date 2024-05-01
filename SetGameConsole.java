import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SetGameConsole{
    private final Board setGame = new Board(new Deck());
    private final Scanner sc = new Scanner(System.in);
    private final ArrayList<Player> players = new ArrayList<>();
    private boolean rightSet = true;
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
    private static int difficultyTime = 0;
    private static int hintCount = 0;

    public void playConsoleSinglePlayer() {
        System.out.println("Entering console version of Singleplayer...");
        System.out.println("Enter nickname: ");
        String nickname = sc.next();

        promptDifficultyLevel();
        setDifficultyLevel();
        Player singlePlayer = new Player(difficultyTime,hintCount,nickname);

        play(singlePlayer);
    }

    public void playConsoleMultiPlayer() {
        System.out.println("Entering console version of Multiplayer...");
        play();
    }
    private void play(Player singlePlayer) {
        singlePlayer.getPlayerTimer().startTimer();
        while (singlePlayer.getPlayerTimer().getSecondsLeft() != 0) {
            displayCurrentState(singlePlayer);
            String input = sc.next();
            manageInput(input, singlePlayer);
        }
        System.exit(0);
    }

    private void play() {
        addPlayers();
        int turn = 0;

        while (!players.isEmpty()) {
            if (turn >= players.size()) {
                turn = 0;
            }
            Player currentPlayer = players.get(turn);
            GameTimer currentTimer = currentPlayer.getPlayerTimer();

            currentTimer.startTimer();
            displayCurrentState(currentPlayer);
            String input = sc.next();
            manageInput(input, currentPlayer);
            if (currentTimer.getSecondsLeft() == 0) {
                System.out.println("Time's up for " + currentPlayer.getNickname() + "!");
                removePlayer(turn);
            } else {
                if (!rightSet && !input.equalsIgnoreCase("add") && !input.equalsIgnoreCase("hint") && !input.replaceAll("\\s", "").isEmpty()) {
                    turn = (turn + 1) % players.size();
                    currentTimer.stopTimer();
                    rightSet = true;
                }
            }
        }
    }

    private void addPlayers() {
        promptDifficultyLevel();
        setDifficultyLevel();
        int num = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter number of players: ");
                num = sc.nextInt();
                sc.nextLine();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
                sc.next();
            }
        }
        for (int i = 0; i < num; i++) {
            System.out.print("Enter player " + (i + 1) + "'s nickname: ");
            String nickname = sc.next();
            players.add(new Player(difficultyTime, hintCount, nickname));
        }
    }

    private void displayCurrentState(Player player) {
        System.out.println("Current board:");
        setGame.printBoard();
        System.out.println("Player " + player.getNickname() + "'s turn:");
        System.out.println("(" + player.getHintCount() + " hints are left)");
        System.out.println("Enter indices of a cards forming a set(one by one or in a row) or type 'hint' for a hint, 'add' for additional row:");
    }

    private void manageInput(String input, Player player) {

        if (input.equalsIgnoreCase("hint")) {
            if (setGame.hint() == null)
                System.out.println("No available set, you may add a row using 'add' (" + player.getHintCount() + " hints are left)");
            else
                provideHint(player);
        } else if (input.equalsIgnoreCase("add")) {
            setGame.addRow();
        } else {
            try {
                int[] indices = new int[3];
                indices[0] = Integer.parseInt(input) - 1;
                for (int i = 1; i < 3; i++) {
                    System.out.println("Enter " + (i + 1) + " index of set: ");
                    indices[i] = sc.nextInt() - 1;
                }
                if (setGame.isSet(indices)) {
                    System.out.println("Valid set!");
                    successfulSet(player);
                    System.out.println("Player " + player.getNickname() + "'s score is: " + player.getScore());
                    setGame.replaceCards(indices);
                } else {
                    System.out.println("Not a valid set.");
                    rightSet = false;
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException | InputMismatchException e) {
                System.out.println("Invalid input. Please enter three valid indices separated by spaces.");
            }
        }
    }

    private void setDifficultyLevel() {
        while (true) {
            if (sc.hasNextInt()) {
                int difficulty = sc.nextInt();

                if (difficulty >= 1 && difficulty <= 3) {
                    switch (Difficulty.values()[difficulty - 1]) {
                        case EASY:
                            difficultyTime = 60;
                            hintCount = 8;
                            System.out.println("Difficulty set to EASY.");
                            return;
                        case MEDIUM:
                            difficultyTime = 45;
                            hintCount = 7;
                            System.out.println("Difficulty set to MEDIUM.");
                            return;
                        case HARD:
                            difficultyTime = 30;
                            hintCount = 6;
                            System.out.println("Difficulty set to HARD.");
                            return;
                    }
                } else {
                    System.out.println("Invalid input: Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input: Please enter a numeric value.");
                sc.next();
            }
        }
    }

    private void promptDifficultyLevel() {
        System.out.println("Select the difficulty level by number input:");
        System.out.println("(1) Easy \n(2) Medium \n(3) Hard");
    }
    private void provideHint(Player player) {
        if (player.getHintCount() > 0) {
            System.out.println("Hint: " + Arrays.toString(setGame.hint()));
            player.setHintCount(player.getHintCount() - 1);
        } else {
            System.out.println("No hints remaining.");
        }
    }
    private void removePlayer(int index) {
        players.remove(index);
    }

    private void successfulSet(Player play){
        play.addScore();
        play.getPlayerTimer().addTime(20);
    }
}