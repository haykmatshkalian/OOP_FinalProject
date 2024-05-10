package SetGameFinal.ui;

import SetGameFinal.console.SetGameConsole;
import SetGameFinal.core.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromptInfo extends JFrame {
    private final JTextField nicknameField;
    private int difficultyTime;
    private int hintCount;
    public PromptInfo() {
        setTitle("SET Game");

        setSize(300, 150);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel nicknamePanel = new JPanel(new GridBagLayout());
        nicknamePanel.setBackground(SetGame.backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nicknameLabel = new JLabel("Nickname:");
        nicknameField = new JTextField(10);
        nicknamePanel.add(nicknameLabel, gbc);
        nicknamePanel.add(nicknameField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(SetGame.backgroundColor);
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        easyButton.setBackground(SetGame.buttonColor);
        mediumButton.setBackground(SetGame.buttonColor);
        hardButton.setBackground(SetGame.buttonColor);

        easyButton.addActionListener(new DifficultyButtonListener(SetGameConsole.Difficulty.EASY));
        mediumButton.addActionListener(new DifficultyButtonListener(SetGameConsole.Difficulty.MEDIUM));
        hardButton.addActionListener(new DifficultyButtonListener(SetGameConsole.Difficulty.HARD));

        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);

        add(nicknamePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class DifficultyButtonListener implements ActionListener {
        private final SetGameConsole.Difficulty difficulty;

        public DifficultyButtonListener(SetGameConsole.Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setDifficultyLevel(difficulty);
            Player singlePlayerGUI = new Player(difficultyTime,hintCount,nicknameField.getText());
            SetGame gameUI = new SetGame(singlePlayerGUI);
            gameUI.setVisible(true);
            dispose();
        }
    }

    public void setDifficultyLevel(SetGameConsole.Difficulty dif) {
        switch (dif) {
            case EASY:
                difficultyTime = 60;
                hintCount = 8;
                return;
            case MEDIUM:
                difficultyTime = 45;
                hintCount = 7;
                return;
            case HARD:
                difficultyTime = 30;
                hintCount = 6;
                return;
        }
    }
}
