package SetGameFinal.console;

import SetGameFinal.core.*;
import java.util.*;

/**
 * Manages the console version of the Set game, including singleplayer and multiplayer modes,
 * player interactions, game setup, and leaderboard management.
 */
public class SetGameConsole{
    private final Board setGame = new Board(new Deck());
    private final Scanner sc = new Scanner(System.in);
    private final ArrayList<Player> players = new ArrayList<>();
    private final LeaderboardManager leaderboardManager;
    private boolean rightSet = true;
    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD
    }
    private static int difficultyTime = 0;
    private static int hintCount = 0;

    /**
     * Constructs a new game console. Initializes the game board and input scanner, sets up the leaderboard manager.
     */
    public SetGameConsole() {
        leaderboardManager = new LeaderboardManager();
    }

    /**
     * Starts the game by displaying a welcome message and prompting the user to select a game mode.
     */
    public void startGame() {
        System.out.println("Welcome to SET game");
        String mode = null;

        while (mode == null) {
            System.out.println("Select game mode (Singleplayer,Multiplayer): ");
            String input = sc.nextLine();

            try {
                mode = checkGameMode(input);
                if (mode.equalsIgnoreCase("Singleplayer")) {
                    playConsoleSinglePlayer();
                } else if (mode.equalsIgnoreCase("Multiplayer")) {
                    playConsoleMultiPlayer();
                }
            } catch (InvalidGameModeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Manages the single player game mode.
     * Sets up a single player game including player creation and the game loop.
     */
    public void playConsoleSinglePlayer() {
        System.out.println("Entering console version of Singleplayer...");
        System.out.println("Enter nickname: ");
        String nickname = sc.next();

        promptDifficultyLevel();
        setDifficultyLevel();
        Player singlePlayer = new Player(difficultyTime,hintCount,nickname);

        play(singlePlayer);
    }

    /**
     * Manages the multiplayer game mode.
     * Sets up multiplayer game, including player creation and the game loop.
     */
    public void playConsoleMultiPlayer() {
        System.out.println("Entering console version of Multiplayer...");
        play();
    }

    /**
     * Game loop for a single player.
     * Handles the gameplay logic, timer, and user inputs until the timer runs out.
     *
     * @param singlePlayer The player object representing the single player.
     */
    private void play(Player singlePlayer) {
        singlePlayer.getPlayerTimer().startTimer();
        while (singlePlayer.getPlayerTimer().getSecondsLeft() != 0) {
            displayCurrentState(singlePlayer);
            String input = sc.next();
            manageInput(input, singlePlayer);
        }
        if(singlePlayer.getPlayerTimer().getSecondsLeft() == 0){
            System.exit(0);
        }
    }

    /**
     * Game loop for multiplayer.
     * Manages turns, input, and timers for all players until no players remain.
     */
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

    /**
     * Adds players to the game based on user input.
     * Asks for the number of players and their nicknames, and initializes their objects.
     */
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

    /**
     * Displays the current state of the board and player information.
     *
     * @param player The current player whose turn it is.
     */
    private void displayCurrentState(Player player) {
        System.out.println("Current board:");
        setGame.printBoard();
        System.out.println("Player " + player.getNickname() + "'s turn:");
        System.out.println("(" + player.getHintCount() + " hints are left)");
        System.out.println("Enter indices of a cards forming a set(one by one or in a row) or type 'hint' for a hint, 'add' for additional row:");
    }

    /**
     * Manages the player's input during their turn.
     * Processes commands like hint requests, adding rows, or attempting to form a set.
     *
     * @param input The user's input command.
     * @param player The player who is currently taking their turn.
     */
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
                Integer[] indices = new Integer[3];
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
                System.out.println("Invalid input. Please enter three valid indices one by one or separated by spaces.");
                System.out.println();
            }
        }
    }

    /**
     * Sets the game difficulty level based on user input.
     * Adjusts the game timer and hint availability according to the selected difficulty.
     */
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

    /**
     * Prompts the user to select a difficulty level.
     */
    private void promptDifficultyLevel() {
        System.out.println("Select the difficulty level by number input:");
        System.out.println("(1) Easy \n(2) Medium \n(3) Hard");
    }

    /**
     * Provides a hint to the player if they have any remaining.
     * Reduces the hint count and shows the hint on the console.
     *
     * @param player The player requesting the hint.
     */
    private void provideHint(Player player) {
        if (player.getHintCount() > 0) {
            System.out.println("Hint: " + Arrays.toString(setGame.hint()));
            player.setHintCount(player.getHintCount() - 1);
        } else {
            System.out.println("No hints remaining.");
        }
    }

    /**
     * Removes a player from the game typically due to running out of time.
     *
     * @param index The index of the player in the player list to be removed.
     */
    private void removePlayer(int index) {
        players.remove(index);
    }

    /**
     * Updates game state when a player successfully finds a set.
     * Adds score, updates the leaderboard, and adds extra time to the player's timer.
     *
     * @param player The player who found the set.
     */
    private void successfulSet(Player player){
        player.addScore();
        player.getPlayerTimer().addTime(20);

        leaderboardManager.updateLeaderboard(player.getNickname(), player.getScore());
    }

    /**
     * Validates the selected game mode and throws an exception if invalid.
     *
     * @param mode The game mode entered by the user.
     * @return The validated game mode.
     * @throws InvalidGameModeException if the entered game mode is not recognized.
     */
    private static String checkGameMode(String mode) throws InvalidGameModeException {
        if (mode.equalsIgnoreCase("Singleplayer") || mode.equalsIgnoreCase("Multiplayer")) {
            return mode;
        } else {
            throw new InvalidGameModeException("Invalid game mode. Please choose either 'Singleplayer' or 'Multiplayer'.");
        }
    }
}
