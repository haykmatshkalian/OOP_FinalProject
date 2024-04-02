public class Card {
    private String shape;
    private String color;
    private String shading;
    private String number;

    public Card(String shape, String color, String shading, String number) {
        this.shape = shape;
        this.color = color;
        this.shading = shading;
        this.number = number;
    }
    public String getShape() {
        return shape;
    }
    public String getColor() {
        return color;
    }
    public String getFill() {
        return shading;
    }
    public String getNumber() {
        return number;
    }
    public String toString() {
        return "[" + shape + ":" + color + ":" + shading + ":" + number + "]";
    }
}

