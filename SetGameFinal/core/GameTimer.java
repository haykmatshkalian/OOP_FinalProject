package SetGameFinal.core;

import javax.swing.*;
import java.awt.event.*;

public class GameTimer {
    private Timer timer;
    private int secondsLeft;
    private boolean isRunning;

    /**
     * Represents a game timer that counts down from a specified number of seconds.
     */
    public GameTimer(int seconds) {
        secondsLeft = seconds;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    if (secondsLeft >= 0) {
                        updateImportantTimes();
                        secondsLeft--;
                    } else {
                        stopTimer();
                        System.out.println("Time is up!");
                    }
                }
            }
        });
    }

    /**
     * Constructs a GameTimer object with the specified number of seconds.
     *
     * @param seconds The number of seconds for the timer.
     */
    private void updateImportantTimes() {
        if (secondsLeft == 60 || secondsLeft == 30 || secondsLeft == 10 || secondsLeft == 5) {
            System.out.println("Time left: " + secondsLeft + " seconds");
        }
    }

    /**
     * Starts the timer if it's not already running.
     */
    public void startTimer() {
        if (!isRunning) {
            isRunning = true;
            timer.start();
        }
    }

    /**
     * Stops the timer if it's running.
     */
    public void stopTimer() {
        if (isRunning) {
            isRunning = false;
            timer.stop();
            System.out.println("Timer stopped.");
        }
    }

    /**
     * Gets the number of seconds left on the timer.
     *
     * @return The number of seconds left.
     */
    public int getSecondsLeft() {
        return secondsLeft;
    }

    /**
     * Adds additional seconds to the timer.
     *
     * @param additionalSeconds The number of seconds to add.
     */
    public void addTime(int additionalSeconds) {
        secondsLeft += additionalSeconds;
        System.out.println("Added " + additionalSeconds + " seconds. New time left: " + secondsLeft + " seconds");
    }
}
