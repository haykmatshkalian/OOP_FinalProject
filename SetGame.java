import java.util.Random;
public class SetGame {
    private final int BOARD_SIZE = 12;
    private final int DECK_SIZE = 81;
    private Card[] deck;
    private Card[] board;
    private int copyIndex;

    public SetGame() {
        deck = generateDeck();
        board = new Card[BOARD_SIZE];
        copyIndex = 0;
        setCards();
    }
    private Card[] generateDeck() {
        Card[] cards = new Card[DECK_SIZE];
        String[] shapes = {"♢", "⬭", "~"};
        String[] colors = {"\uD83D\uDD34", "\uD83D\uDFE2", "\uD83D\uDFE3"};
        String[] shadings = {"◼", "▨", "◻"};
        String[] numbers = {"1\uFE0F⃣", "2\uFE0F⃣", "3\uFE0F⃣"};

        int index = 0;
        for (String shape : shapes) {
            for (String color : colors) {
                for (String shade : shadings) {
                    for (String number : numbers) {
                        cards[index++] = new Card(shape, color, shade, number);
                    }
                }
            }
        }
        shuffleDeck(cards);
        return cards;
    }
    private void shuffleDeck(Card[] cards) {
        Random rnd = new Random();
        for (int i = 0; i < DECK_SIZE; i++) {
            int r = rnd.nextInt(DECK_SIZE);
            Card transfer = cards[i];
            cards[i] = cards[r];
            cards[r] = transfer;
        }
    }
    private void setCards() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            board[i] = deck[copyIndex++];
        }
    }
    public boolean isSet(int[] indices) {
        if(indices.length != 3) {
            return false;
        }
        Card card1 = board[indices[0]];
        Card card2 = board[indices[1]];
        Card card3 = board[indices[2]];
        return (allSameOrDifferent(card1.getShape(), card2.getShape(), card3.getShape()) &&
                allSameOrDifferent(card1.getColor(), card2.getColor(), card3.getColor()) &&
                allSameOrDifferent(card1.getFill(), card2.getFill(), card3.getFill()) &&
                allSameOrDifferent(card1.getNumber(),card2.getNumber(), card3.getNumber()));
    }
    private boolean allSameOrDifferent(String str1, String str2, String str3) {
        return (str1.equals(str2) && str2.equals(str3)) || (!str1.equals(str2) && !str2.equals(str3) && !str1.equals(str3));
    }
    public void replaceCards(int[] indices) {
        for (int index : indices) {
            if (!isDeckEmpty()) {
                board[index] = deck[copyIndex++];
            }
        }
    }
    public boolean isDeckEmpty() {
        return copyIndex >= deck.length;
    }
    public boolean hasSetOnBoard() {
        for (int i = 0; i < board.length - 2; i++) {
            for (int j = i + 1; j < board.length - 1; j++) {
                for (int k = j + 1; k < board.length; k++) {
                    if (isSet(new int[]{i, j, k})) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            System.out.println((i + 1) + ": " + board[i]);
        }
    }
}
