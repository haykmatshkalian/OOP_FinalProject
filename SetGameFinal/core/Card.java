package SetGameFinal.core;

/**
 * The Card class represents a single card in the Set game.
 * Each card has a shape, color, shading, and number.
 */
public class Card {
    public enum Shape {
        OVAL,
        DIAMOND,
        SQUIGGLES
    }
    public enum Color {
        RED,
        GREEN,
        PURPLE
    }
    public enum Shading {
        HOLLOW,
        SHADED,
        FILLED
    }

    public enum Number {
        ONE,
        TWO,
        THREE
    }

    private Shape shape;
    private Color color;
    private Shading shading;
    private Number number;

    /**
     * Constructs a Card object with the specified attributes.
     *
     * @param shape   The shape of the card.
     * @param color   The color of the card.
     * @param shading The shading of the card.
     * @param number  The number of shapes on the card.
     */
    public Card(Shape shape, Color color, Shading shading, Number number) {
        this.shape = shape;
        this.color = color;
        this.shading = shading;
        this.number = number;
    }

    /**
     * Gets the shape of the card.
     *
     * @return The shape enum representing the shape of the card.
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Gets the color of the card.
     *
     * @return The color enum representing the color of the card.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Gets the shading of the card.
     *
     * @return The shading enum representing the shading of the card.
     */
    public Shading getFill() {
        return shading;
    }


    /**
     * Gets the number of shapes on the card.
     *
     * @return The number enum representing the number of shapes on the card.
     */
    public Number getNumber() {
        return number;
    }

    /**
     * Returns a string representation of the card.
     *
     * @return A string representation of the card in a format suitable for display.
     */
    @Override
    public String toString() {
        String[] shapes = {"⬭", "♢", "~"};
        String[] colors = {"\uD83D\uDD34", "\uD83D\uDFE2", "\uD83D\uDFE3"};
        String[] shadings = {"◻", "▨","◼"};
        String[] numbers = {"1\uFE0F⃣", "2\uFE0F⃣", "3\uFE0F⃣"};

        String shape = shapes[this.shape.ordinal()];
        String color = colors[this.color.ordinal()];
        String shading = shadings[this.shading.ordinal()];
        String number = numbers[this.number.ordinal()];
        return "[" + shape + ":" + color + ":" + shading + ":" + number + "]";
    }
}
