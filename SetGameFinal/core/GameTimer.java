package SetGameFinal.core;

import javax.swing.*;
import java.awt.event.*;

public class GameTimer {
    private Timer timer;
    private int secondsLeft;
    private boolean isRunning;

    public GameTimer(int seconds) {
        secondsLeft = seconds;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    if (secondsLeft >= 0) {
                        if (secondsLeft == 60) {
                            System.out.println("Time left: " + secondsLeft + " seconds");
                        }
                        if (secondsLeft == 30) {
                            System.out.println("Time left: " + secondsLeft + " seconds");
                        }
                        if (secondsLeft == 10) {
                            System.out.println("Time left: " + secondsLeft + " seconds");
                        }
                        if (secondsLeft == 5) {
                            System.out.println("Time left: " + secondsLeft + " seconds");
                        }

                        secondsLeft--;

                    } else {
                        stopTimer();
                        System.out.println("Time is up!");
                    }
                }
            }
        });
    }

    public void startTimer() {
        if (!isRunning) {
            isRunning = true;
            timer.start();
        }
    }
    public void stopTimer() {
        if (isRunning) {
            isRunning = false;
            timer.stop();
            System.out.println("Timer stopped.");
        }
    }
    public int getSecondsLeft() {
        return secondsLeft;
    }
    public void addTime(int additionalSeconds) {
        secondsLeft += additionalSeconds;
        System.out.println("Added " + additionalSeconds + " seconds. New time left: " + secondsLeft + " seconds");
    }
}
