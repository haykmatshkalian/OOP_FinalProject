import javax.swing.*;
import java.awt.event.*;

public class GameTimer {
    private Timer timer;
    private int secondsLeft;

    public GameTimer(int seconds) {
        secondsLeft = seconds;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (secondsLeft >= 0) {
                    if(secondsLeft <= 5 ) {
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }
                    if(secondsLeft == 60 ) {
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }
                    if(secondsLeft == 30 ) {
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }
                    if(secondsLeft == 10 ) {
                        System.out.println("Time left: " + secondsLeft + " seconds");
                    }

                    secondsLeft--;

                } else {
                    timer.stop();
                    System.out.println("Time is up!");
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
}
