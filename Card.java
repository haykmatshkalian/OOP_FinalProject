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

    public Card(Shape shape, Color color, Shading shading, Number number) {
        this.shape = shape;
        this.color = color;
        this.shading = shading;
        this.number = number;
    }
    public Shape getShape() {
        return shape;
    }
    public Color getColor() {
        return color;
    }
    public Shading getFill() {
        return shading;
    }
    public Number getNumber() {
        return number;
    }
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