import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        SetGame setGame = new SetGame();
        Player player = new Player();
        Player player1 = new Player(player);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Current board:");
            setGame.printBoard();
            System.out.println("Enter indices of rows forming a set");
            String input = sc.nextLine();
            String[] indicesStr = input.split(" ");
            int[] indices = new int[3];
            try {
                for (int i = 0; i < 3; i++) {
                    indices[i] = Integer.parseInt(indicesStr[i]) - 1;
                }
                if (setGame.isSet(indices)) {
                    System.out.println("Valid set!");
                    player1.addScore();
                    System.out.println("Your score is: " + player1.getScore());
                    if (setGame.isDeckEmpty() && !setGame.hasSetOnBoard()) {
                        System.out.println("No more sets found. Game over.");
                        break;
                    }
                    setGame.replaceCards(indices);
                } else {
                    System.out.println("Not a valid set.");
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Invalid input. Please enter three valid indices separated by spaces.");
            }
        }
        sc.close();
    }
}
