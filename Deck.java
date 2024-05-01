import java.util.Random;

public class Deck {
    private static final int DECK_SIZE = 81;
    private final Card[] deck;

    public Deck(){
        deck = generateDeck();
    }
    private Card[] generateDeck() {
        Card[] cards = new Card[DECK_SIZE];
        Card.Shape[] shapes = {Card.Shape.DIAMOND, Card.Shape.OVAL, Card.Shape.SQUIGGLES};
        Card.Color[] colors = {Card.Color.RED, Card.Color.GREEN, Card.Color.PURPLE};
        Card.Shading[] shadings = {Card.Shading.HOLLOW, Card.Shading.SHADED, Card.Shading.FILLED};
        Card.Number[] numbers = {Card.Number.ONE, Card.Number.TWO, Card.Number.THREE};

        int index = 0;
        for (Card.Shape shape : shapes) {
            for (Card.Color color : colors) {
                for (Card.Shading shade : shadings) {
                    for (Card.Number number : numbers) {
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
    public Card[] getDeck() {
        return deck;
    }
    public static int getDeckSize() {
        return DECK_SIZE;
    }
}
