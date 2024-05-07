package SetGameFinal.ui;

import SetGameFinal.core.Board;
import SetGameFinal.core.Card;
import SetGameFinal.core.Deck;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class SetGame extends JFrame {
    private JPanel cardPanel;
    private JButton[] cardButtons;
    private Board board;
    private Deck deck;
    private Set<Integer> selectedCards = new HashSet<>();

    public SetGame() {
        setTitle("SET Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 800);

        deck = new Deck();
        board = new Board(deck);

        cardPanel = new JPanel(new GridLayout(0, 4));
        initializeCards();

        JButton addColumnButton = new JButton("Add Column");
        addColumnButton.addActionListener(this::addColumn);

        JButton checkSetButton = new JButton("Find Set");
        checkSetButton.addActionListener(this::highlightSet);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addColumnButton);
        buttonPanel.add(checkSetButton);

        cardPanel.setPreferredSize(new Dimension(800,800));
        add(cardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void initializeCards() {
        cardButtons = new JButton[board.getBoard().length];
        for (int i = 0; i < board.getBoard().length; i++) {
            cardButtons[i] = createCardButton(i);
            cardPanel.add(cardButtons[i]);
        }
    }

    private JButton createCardButton(int index) {
        JButton button = new JButton(board.getBoard()[index].toString());
        button.addActionListener(e -> toggleCardSelection(index, button));
        return button;
    }

    private void toggleCardSelection(int index, JButton button) {
        if (selectedCards.contains(index)) {
            selectedCards.remove(index);
            button.setBorder(null);
        } else {
            selectedCards.add(index);
            button.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
            if (selectedCards.size() == 3) {
                checkSet();
            }
        }
    }

    private void addColumn(ActionEvent e) {
        board.addRow();
        updateCardPanel();
    }

    private void updateCardPanel() {
        cardPanel.removeAll();
        cardPanel.setLayout(new GridLayout(0, (board.getBoard().length / 3)));

        cardButtons = new JButton[board.getBoard().length];
        for (int i = 0; i < board.getBoard().length; i++) {
            cardButtons[i] = createCardButton(i);
            cardPanel.add(cardButtons[i]);
        }

        cardPanel.revalidate();
        cardPanel.repaint();
    }
    private void resetCardBorders() {
        for (JButton button : cardButtons) {
            button.setBorder(null);
        }
    }
    private void checkSet() {
        if (selectedCards.size() == 3) {
            Integer[] indices = selectedCards.toArray(new Integer[0]);
            if (board.isSet(indices)) {
                if(board.getExtendedBoard())
                    board.replaceCardsExtended(indices);
                    updateCardPanel();
                JOptionPane.showMessageDialog(null, "Valid Set!", "Set Game", JOptionPane.INFORMATION_MESSAGE);
                board.replaceCards(indices);
                selectedCards.clear();
                updateCardButtons();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Set.", "Set Game", JOptionPane.ERROR_MESSAGE);
                resetCardBorders();
            }
        }
    }
    private void updateCardButtons() {
        for (int i = 0; i < cardButtons.length; i++) {
            cardButtons[i].setText(board.getBoard()[i].toString());
            cardButtons[i].setBorder(null);
        }
    }
    private void highlightSet(ActionEvent e) {
        Card[] setCards = board.hint();
        if (setCards != null) {
            for (JButton btn : cardButtons) {
                btn.setBorder(null);
            }
            for (Card card : setCards) {
                int index = Arrays.asList(board.getBoard()).indexOf(card);
                cardButtons[index].setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
            }
        }
    }

    public static void main(String[] args) {
            SetGame gameUI = new SetGame();
            gameUI.setVisible(true);
    }
}

