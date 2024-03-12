public class Player {
    private String name; //final
    public boolean inJail = false;
    public static final String[] figures = {"Battleship", "Race Car", "Top Hat", "Cat", "Penguin", "Rubber Ducky", "Thimble", "Scottish Terrier"};
    public int turnsInJail = 0;
    private int position;
    private int personalMoney = 1500;

    

    public Player(String name){
        this.name = name;
        position = 0;
    }

    public int goTo(int position){
        int dice1;
        int dice2;
        // return position + dice1 + dice2;
        return 0;
    }


}
