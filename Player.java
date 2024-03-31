public class Player {
    private int score;
    public Player(){
        score = 0;
    }
    public Player(Player that){
        this.score = that.score;
    }
    public int getScore(){
        return score;
    }
    public void addScore(){
        score++;
    }
}
