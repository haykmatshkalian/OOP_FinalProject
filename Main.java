import java.util.Scanner;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    private Timer timer;
    private int secondsLeft;

    public Main(int seconds) {
        secondsLeft = seconds;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondsLeft >= 0) {
                    if(secondsLeft <= 5 ) {
//                        secondsLeft --;
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }
                    if(secondsLeft == 60 ) {
//                        secondsLeft --;
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }
                    if(secondsLeft == 30 ) {
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }
                    if(secondsLeft == 10 ) {
//                        secondsLeft --;
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }

                    secondsLeft--;

                } else {
                    timer.stop();
                    System.out.println("Game over!");
                    System.exit(0);
                }
            }
        });
    }

    public void startTimer() {
        timer.start();
    }

    public void addTime(int additionalSeconds) {
        secondsLeft += additionalSeconds;
        System.out.println("Added " + additionalSeconds + " seconds. New time left: " + secondsLeft + " seconds");
    }


    public static void main(String[] args) {
        Main game = new Main(60); // 60 seconds timer
        game.startTimer();

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
                    game.addTime(10);
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
    }
}
