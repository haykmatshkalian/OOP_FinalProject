package SetGameFinal.core;

import java.util.Arrays;

/**
 * The Board class represents the game board containing cards.
 * It manages card placement, checking for sets, and replacing cards.
 */
public class Board {
    private static final int BOARD_SIZE = 12;
    private Card[] board;
    private boolean extendedBoard = false;
    private final Deck deckClass;

    /**
     * Constructs a Board object with the specified deck.
     *
     * @param deck The deck from which cards will be drawn.
     */
    public Board(Deck deck) {
        this.deckClass = deck;
        board = new Card[BOARD_SIZE];
        setCards();
    }

    /**
     * Initializes the board with cards from the deck.
     */
    private void setCards() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = deckClass.getDeck()[deckClass.addCopyIndex()];
        }
    }

    /**
     * Checks if the selected indices form a valid set.
     *
     * @param indices An array of indices representing selected cards.
     * @return True if the selected cards form a set, false otherwise.
     */
    public boolean isSet(Integer[] indices) {
        if (indices.length != 3) {
            return false;
        }
        Card card1 = board[indices[0]];
        Card card2 = board[indices[1]];
        Card card3 = board[indices[2]];
        return (allSameOrDifferent(card1.getShape(), card2.getShape(), card3.getShape()) &&
                allSameOrDifferent(card1.getColor(), card2.getColor(), card3.getColor()) &&
                allSameOrDifferent(card1.getFill(), card2.getFill(), card3.getFill()) &&
                allSameOrDifferent(card1.getNumber(), card2.getNumber(), card3.getNumber()));
    }

    private boolean allSameOrDifferent(Enum<?> str1, Enum<?> str2, Enum<?> str3) {
        return (str1.equals(str2) && str2.equals(str3)) || (!str1.equals(str2) && !str2.equals(str3) && !str1.equals(str3));
    }

    /**
     * Replaces selected cards on the board with new cards from the deck.
     *
     * @param indices An array of indices representing selected cards to be replaced.
     */
    public void replaceCards(Integer[] indices) {
        if (!extendedBoard) {
            replaceCardsStandard(indices);
        } else {
            replaceCardsExtended(indices);
        }
    }

    private void replaceCardsStandard(Integer[] indices) {
        if (!extendedBoard) {
            for (int index : indices) {
                if (!deckClass.isDeckEmpty()) {
                    board[index] = deckClass.getDeck()[deckClass.addCopyIndex()];
                }
            }
        }
    }

    /**
     * Replaces selected cards on the board with new cards from the deck, in case of an extended board.
     *
     * @param indices An array of indices representing selected cards to be replaced.
     */
    public void replaceCardsExtended(Integer[] indices) {
        for (int index : indices) {
            board[index] = null;
        }
        int indexBoard = 0;
        Card[] overStandard = new Card[3];
        for (int i = board.length - 3; i < board.length; i++) {
            if (board[i] != null) {
                overStandard[indexBoard] = board[i];
                indexBoard++;
            }
        }
        removeRow(overStandard);
        if(deckClass.getDeckIndex() > Deck.DECK_SIZE - 3) {
            for (int index : indices) {
                board[index] = null;
            }
            removeRow();
        }
    }

    /**
     * Provides a hint by finding a valid set on the board.
     *
     * @return An array of Card objects representing a valid set, or null if no set is found.
     */
    public Card[] hint() {
        for (int i = 0; i < board.length - 2; i++) {
            for (int j = i + 1; j < board.length - 1; j++) {
                for (int k = j + 1; k < board.length; k++) {
                    if (isSet(new Integer[]{i, j, k})) {
                        return new Card[]{board[i],board[j],board[k]};
                    }
                }
            }
        }
        return null;
    }

    /**
     * Prints the current state of the board to the console.
     */
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println((i + 1) + ": " + board[i]);
        }
    }

    /**
     * Adds a row of three cards to the board, extending its size if possible.
     */
    public void addRow() {
        if (deckClass.getDeckIndex() + 3 <= Deck.DECK_SIZE) {
            Card[] newBoard = new Card[board.length + 3];
            System.arraycopy(board, 0, newBoard, 0, board.length);

            for (int i = board.length; i < newBoard.length; i++) {
                if (deckClass.getDeckIndex() < Deck.DECK_SIZE) {
                    newBoard[i] = deckClass.getDeck()[deckClass.addCopyIndex()];
                }
            }
            board = newBoard;
            extendedBoard = true;
        } else {
            System.out.println("Deck size limit reached.");
        }
    }

    /**
     * Removes a row of three cards from the board, reducing its size.
     *
     * @param overStandard An array of cards representing the extra cards added to the board.
     */
    private void removeRow(Card[] overStandard) {
        if (extendedBoard) {
            Card[] newBoard = new Card[board.length - 3];
            int indexNewArr = 0;
            for (int i = 0; i < newBoard.length; i++){
                if (board[i] == null){
                    newBoard[i] = overStandard[indexNewArr];
                    indexNewArr++;
                } else {
                    newBoard[i] = board[i];
                }
            }
            board = newBoard;
            if (board.length == 12){
                extendedBoard = false;
            }
        }
    }

    /**
     * Removes a row of three cards from the board, reducing its size.
     */
    private void removeRow() {
        Card[] newBoard = new Card[board.length - 3];
        int indexTemp = 0;
        for (Card elements : board){
            if (elements != null){
                newBoard[indexTemp++] = elements;
            }
        }
        board = newBoard;
    }

    /**
     * Gets the current state of the extended board.
     *
     * @return True if the board is extended, false otherwise.
     */
    public boolean getExtendedBoard(){
        return extendedBoard;
    }


    /**
     * Gets a copy of the board array.
     *
     * @return A copy of the array of Card objects representing the board.
     */
    public Card[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }
}
