package SetGameFinal.core;

import java.util.Arrays;

public class Board {
    private static final int BOARD_SIZE = 12;
    private Card[] board;
    private boolean extendedBoard = false;
    private final Deck deckClass;


    public Board(Deck deck) {
        this.deckClass = deck;
        board = new Card[BOARD_SIZE];
        setCards();
    }

    private void setCards() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = deckClass.getDeck()[deckClass.addCopyIndex()];
        }
    }

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

        if(deckClass.getDeckIndex() > Deck.DECK_SIZE - 3)

        {
            for (int index : indices) {
                board[index] = null;
            }
            removeRow();
        }
    }
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

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println((i + 1) + ": " + board[i]);
        }
    }

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
    public boolean getExtendedBoard(){
        return extendedBoard;
    }
    public Card[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }
}
