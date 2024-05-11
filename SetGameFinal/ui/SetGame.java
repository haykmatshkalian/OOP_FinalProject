package SetGameFinal.ui;

import SetGameFinal.console.LeaderboardManager;
import SetGameFinal.core.Board;
import SetGameFinal.core.Card;
import SetGameFinal.core.Deck;
import SetGameFinal.core.Player;

import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

/**
 * This class represents the main user interface for the Set Game application.
 * It extends JFrame and provides methods to initialize the game, handle user interactions,
 * update the display, and manage game logic.
 */
public class SetGame extends JFrame {
    private JPanel cardPanel, sidePanel;
    private JButton[] cardButtons;
    private final Board board;
    private final Deck deck;
    private JLabel timeLabel, hintLabel, scoreLabel;
    private boolean addedColumn = true;
    private HashSet<Integer> selectedCards = new HashSet<>();
    private Player play;
    private Timer timer;
    public static Color backgroundColor = new Color(0xEADBC8);
    public static Color buttonColor = new Color(0xB5C18E);
    public static Color borderColor = new Color(0xC7B7A3);
    private final LeaderboardManager leaderboardManager;

    /**
     * Constructs a new SetGame object with the specified player.
     *
     * @param player The player object representing the current player.
     */
    public SetGame(Player player) {
        leaderboardManager = new LeaderboardManager();
        play = new Player(player);
        play.getPlayerTimer().startTimer();
        setupTimer();


        setTitle("SET Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(1000, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        deck = new Deck();
        board = new Board(deck);

        cardPanel = new JPanel(new GridLayout(0, 4));
        cardPanel.setPreferredSize(new Dimension(600,800));
        cardPanel.setBackground(backgroundColor);
        initializeCards();

        timeLabel = new JLabel("Time Remained: " + play.getPlayerTimer().getSecondsLeft() + " seconds");
        hintLabel = new JLabel("Hints Remained: " +  play.getHintCount());
        scoreLabel = new JLabel(play.getNickname() + "'s score: " + play.getScore());
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setPreferredSize(new Dimension(200, 600));
        sidePanel.setBackground(backgroundColor);
        sidePanel.add(timeLabel);
        sidePanel.add(hintLabel);
        sidePanel.add(scoreLabel);

        JButton addColumnButton = new JButton("Add Column");
        addColumnButton.addActionListener(this::addColumn);
        addColumnButton.setBackground(SetGame.buttonColor);

        JButton checkSetButton = new JButton("Find Set");
        checkSetButton.addActionListener(this::highlightSet);
        checkSetButton.setBackground(SetGame.buttonColor);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(backgroundColor);
        buttonPanel.add(addColumnButton);
        buttonPanel.add(checkSetButton);


        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(sidePanel, BorderLayout.EAST);
    }

    /**
     * Initializes the cards on the game board.
     */
    private void initializeCards() {
        cardButtons = new JButton[board.getBoard().length];
        for (int i = 0; i < board.getBoard().length; i++) {
            cardButtons[i] = createCardButton(i);
            cardButtons[i].setBorder(new LineBorder(borderColor, 1));
            cardPanel.add(cardButtons[i]);
        }
    }
    /**
     * Creates a JButton representing a card at the specified index.
     *
     * @param index The index of the card.
     * @return The JButton representing the card.
     */
    private JButton createCardButton(int index) {
        JButton button = new JButton();
        Card card = board.getBoard()[index];

        String imagePath = setCard(card);
        ImageIcon icon = new ImageIcon(imagePath);
        button.setIcon(icon);
        button.setBackground(Color.WHITE);
        button.setOpaque(false);

        button.addActionListener(e -> toggleCardSelection(index, button));
        return button;
    }

    /**
     * Toggles the selection of a card at the specified index.
     *
     * @param index  The index of the card to toggle.
     * @param button The JButton representing the card.
     */
    private void toggleCardSelection(int index, JButton button) {
        if (selectedCards.contains(index)) {
            selectedCards.remove(index);
            button.setBorder(new LineBorder(borderColor, 2));
        } else {
            selectedCards.add(index);
            button.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            if (selectedCards.size() == 3) {
                checkSet();
                button.setBorder(new LineBorder(borderColor, 1));
            }
        }
    }

    /**
     * Adds a new column to the game board.
     *
     * @param e The ActionEvent object representing the action.
     */
    private void addColumn(ActionEvent e) {
        if (addedColumn) {
            board.addRow();
            updateCardPanel();
            addedColumn = false;
        }else
            JOptionPane.showMessageDialog(null, "You have already added a column!", "Set Game", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Updates the display of the card panel.
     */
    private void updateCardPanel() {
        cardPanel.removeAll();
        cardPanel.setLayout(new GridLayout(0, (board.getBoard().length / 3)));
        cardButtons = new JButton[board.getBoard().length];
        for (int i = 0; i < board.getBoard().length; i++) {
            cardButtons[i] = createCardButton(i);
            cardButtons[i].setBorder(new LineBorder(borderColor, 1));
            cardPanel.add(cardButtons[i]);
        }
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    /**
     * Checks if the selected cards form a valid set and updates the game state accordingly.
     */
    private void checkSet() {
        if (selectedCards.size() == 3) {
            Integer[] indices = selectedCards.toArray(new Integer[0]);
            if (board.isSet(indices)) {
                if (!addedColumn)
                    addedColumn = true;
                for (int index : indices) {
                    cardButtons[index].setBorder(new LineBorder(borderColor, 1));
                }
                JOptionPane.showMessageDialog(null, "Valid Set!", "Set Game", JOptionPane.INFORMATION_MESSAGE);
                board.replaceCards(indices);
                updateCardPanel();
                selectedCards.clear();
                successfulSet(play);
                updateScoreLabel();
            } else {
                if (!addedColumn)
                    addedColumn = true;
                JOptionPane.showMessageDialog(null, "Invalid Set.", "Set Game", JOptionPane.ERROR_MESSAGE);
                for (int index : indices) {
                    cardButtons[index].setBorder(new LineBorder(borderColor, 1));
                }
                selectedCards.clear();
            }
        }
    }

    /**
     * Highlights a valid set of cards on the game board.
     *
     * @param e The ActionEvent object representing the action.
     */
    private void highlightSet(ActionEvent e) {
        provideHint(play);
    }

    /**
     * Updates the hint label to display the remaining number of hints.
     */
    private void updateHintLabel() {
        if (hintLabel != null) {
            hintLabel.setText("Hints Remained: " +  play.getHintCount());
        }
    }

    /**
     * Updates the time label to display the remaining time.
     */
    private void updateTimeLabel() {
        if (timeLabel != null) {
            timeLabel.setText("Time Remained: " + play.getPlayerTimer().getSecondsLeft() + " seconds");
        }
    }

    /**
     * Updates the score label to display the player's score.
     */
    private void updateScoreLabel() {
        if (scoreLabel != null) {
            scoreLabel.setText(play.getNickname() + "'s score: " + play.getScore());
        }
    }

    /**
     * Provides a hint to the player by highlighting a valid set of cards on the game board.
     *
     * @param play The Player object representing the current player.
     */
    public void provideHint(Player play){
        if (play.getHintCount() !=0){
            Card[] setCards = board.hint();
            if (setCards != null) {
                for (JButton btn : cardButtons) {
                    btn.setBorder(new LineBorder(borderColor, 1));
                }
                for (Card card : setCards) {
                    int index = Arrays.asList(board.getBoard()).indexOf(card);
                    cardButtons[index].setBorder(BorderFactory.createLineBorder(new Color(0x7ABA78), 3));
                }
            }
            play.setHintCount(play.getHintCount() - 1);
            updateHintLabel();
        }
        else {
            JOptionPane.showMessageDialog(null, "You ran out of hint's", "Set Game", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Processes a successful set by updating the player's score and timer, and updating the leaderboard.
     *
     * @param play The Player object representing the current player.
     */
    public void successfulSet(Player play){
        play.addScore();
        play.getPlayerTimer().addTime(20);

        leaderboardManager.updateLeaderboard(play.getNickname(), play.getScore());
    }

    /**
     * Sets up the timer to update the time label every second.
     */
    private void setupTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeLabel();
            }
        });
        timer.start();
    }

    /**
     * Maps a card object to its corresponding image file path.
     *
     * @param s The Card object to map.
     * @return The file path of the corresponding image.
     */
    public String setCard(Card s) {
        String imagePath = null;
        switch (s.toString()) {
            case "[♢:🔴:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_04.png";
                break;
            case "[♢:🔴:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_05.png";
                break;
            case "[♢:🔴:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_06.png";
                break;
            case "[♢:🔴:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_13.png";
                break;
            case "[♢:🔴:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_14.png";
                break;
            case "[♢:🔴:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_15.png";
                break;
            case "[♢:🔴:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_22.png";
                break;
            case "[♢:🔴:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_23.png";
                break;
            case "[♢:🔴:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_24.png";
                break;
            case "[♢:🟢:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_31.png";
                break;
            case "[♢:🟢:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_32.png";
                break;
            case "[♢:🟢:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_33.png";
                break;
            case "[♢:🟢:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_40.png";
                break;
            case "[♢:🟢:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_41.png";
                break;
            case "[♢:🟢:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_42.png";
                break;
            case "[♢:🟢:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_49.png";
                break;
            case "[♢:🟢:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_50.png";
                break;
            case "[♢:🟢:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_51.png";
                break;
            case "[♢:🟣:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_58.png";
                break;
            case "[♢:🟣:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_59.png";
                break;
            case "[♢:🟣:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_60.png";
                break;
            case "[♢:🟣:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_67.png";
                break;
            case "[♢:🟣:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_68.png";
                break;
            case "[♢:🟣:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_69.png";
                break;
            case "[♢:🟣:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_76.png";
                break;
            case "[♢:🟣:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_77.png";
                break;
            case "[♢:🟣:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_78.png";
                break;
            case "[⬭:🔴:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_07.png";
                break;
            case "[⬭:🔴:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_08.png";
                break;
            case "[⬭:🔴:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_09.png";
                break;
            case "[⬭:🔴:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_16.png";
                break;
            case "[⬭:🔴:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_17.png";
                break;
            case "[⬭:🔴:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_18.png";
                break;
            case "[⬭:🔴:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_25.png";
                break;
            case "[⬭:🔴:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_26.png";
                break;
            case "[⬭:🔴:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_27.png";
                break;
            case "[⬭:🟢:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_34.png";
                break;
            case "[⬭:🟢:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_35.png";
                break;
            case "[⬭:🟢:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_36.png";
                break;
            case "[⬭:🟢:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_43.png";
                break;
            case "[⬭:🟢:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_44.png";
                break;
            case "[⬭:🟢:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_45.png";
                break;
            case "[⬭:🟢:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_52.png";
                break;
            case "[⬭:🟢:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_53.png";
                break;
            case "[⬭:🟢:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_54.png";
                break;
            case "[⬭:🟣:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_61.png";
                break;
            case "[⬭:🟣:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_62.png";
                break;
            case "[⬭:🟣:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_63.png";
                break;
            case "[⬭:🟣:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_70.png";
                break;
            case "[⬭:🟣:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_71.png";
                break;
            case "[⬭:🟣:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_72.png";
                break;
            case "[⬭:🟣:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_79.png";
                break;
            case "[⬭:🟣:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_80.png";
                break;
            case "[⬭:🟣:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_81.png";
                break;
            case "[~:🔴:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_01.png";
                break;
            case "[~:🔴:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_02.png";
                break;
            case "[~:🔴:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_03.png";
                break;
            case "[~:🔴:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_10.png";
                break;
            case "[~:🔴:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_11.png";
                break;
            case "[~:🔴:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_12.png";
                break;
            case "[~:🔴:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_19.png";
                break;
            case "[~:🔴:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_20.png";
                break;
            case "[~:🔴:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_21.png";
                break;
            case "[~:🟢:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_28.png";
                break;
            case "[~:🟢:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_29.png";
                break;
            case "[~:🟢:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_30.png";
                break;
            case "[~:🟢:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_37.png";
                break;
            case "[~:🟢:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_38.png";
                break;
            case "[~:🟢:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_39.png";
                break;
            case "[~:🟢:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_46.png";
                break;
            case "[~:🟢:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_47.png";
                break;
            case "[~:🟢:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_48.png";
                break;
            case "[~:🟣:◻:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_55.png";
                break;
            case "[~:🟣:◻:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_56.png";
                break;
            case "[~:🟣:◻:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_57.png";
                break;
            case "[~:🟣:▨:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_64.png";
                break;
            case "[~:🟣:▨:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_65.png";
                break;
            case "[~:🟣:▨:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_66.png";
                break;
            case "[~:🟣:◼:1️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_73.png";
                break;
            case "[~:🟣:◼:2️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_74.png";
                break;
            case "[~:🟣:◼:3️⃣]":
                imagePath = "SetGameFinal/ui/cards/card_75.png";
                break;
        }
        return imagePath;
    }

    /**
     * The main method that starts the Set Game application by creating a new instance of SetGame.
     *
     * @param args The command-line arguments (not used).
     */
    public static void main(String[] args) {
            PromptInfo gameUI = new PromptInfo();
            gameUI.setVisible(true);
    }
}

