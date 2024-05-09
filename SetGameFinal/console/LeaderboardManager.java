package SetGameFinal.console;

import java.io.*;
import java.util.HashMap;

public class LeaderboardManager {
    private static final String LEADERBOARD_FILE = "leaderboard.txt";
    private HashMap<String, Integer> leaderboard;

    public LeaderboardManager() {
        leaderboard = new HashMap<>();
        loadLeaderboard();
    }

    private void loadLeaderboard() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LEADERBOARD_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String nickname = parts[0];
                int score = Integer.parseInt(parts[1]);
                leaderboard.put(nickname, score);
            }
        } catch (IOException e) {
            System.out.println("Error reading leaderboard file: " + e.getMessage());
        }
    }

    public void updateLeaderboard(String nickname, int score) {
        if (leaderboard.containsKey(nickname)) {
            int currentScore = leaderboard.get(nickname);
            leaderboard.put(nickname, currentScore + score);
        } else {
            leaderboard.put(nickname, score);
        }
        saveLeaderboard();
    }

    private void saveLeaderboard() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LEADERBOARD_FILE))) {
            for (String nickname : leaderboard.keySet()) {
                writer.write(nickname + "," + leaderboard.get(nickname) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to leaderboard file: " + e.getMessage());
        }
    }
}