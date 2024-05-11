package SetGameFinal.core;

import java.util.Random;

/**
 * The Deck class represents a deck of cards used in the Set game.
 * It contains methods for generating a shuffled deck, checking if the deck is empty,
 * and providing access to cards in the deck.
 */
public class Deck {

    public static final int DECK_SIZE = 81;
    private final Card[] deck;
    private int copyIndex;

    /**
     * Constructs a Deck object and initializes the deck.
     */
    public Deck(){
        deck = generateDeck();
        copyIndex = 0;
    }

    /**
     * Generates and shuffles the deck of cards.
     * @return Shuffled deck of cards.
     */
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
                        ;
                    }
                }
            }
        }

        shuffleDeck(cards);
        return cards;
    }

    /**
     * Shuffles the deck of cards using the Fisher-Yates algorithm.
     * @param cards The array of cards to be shuffled.
     */
    private void shuffleDeck(Card[] cards) {
        Random rnd = new Random();
        for (int i = 0; i < DECK_SIZE; i++) {
            int r = rnd.nextInt(DECK_SIZE);
            Card transfer = cards[i];
            cards[i] = cards[r];
            cards[r] = transfer;
        }
    }

    /**
     * Checks if the deck is empty.
     * @return True if the deck is empty, false otherwise.
     */
    public boolean isDeckEmpty() {
        return copyIndex >= deck.length;
    }

    /**
     * Gets the current index in the deck.
     * @return Current index in the deck.
     */
    public int getDeckIndex() {
        return copyIndex;
    }

    /**
     * Increments the current index and returns its previous value.
     * @return Previous value of the current index.
     */
    public int addCopyIndex() {
        return copyIndex++;
    }

    /**
     * Gets the deck of cards.
     * @return Array of cards representing the deck.
     */
    public Card[] getDeck() {
        return deck;
    }
}
