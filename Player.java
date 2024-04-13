public class Player {
    private int score;
    private int hintCount;
    public Player(){
        score = 0;
    }
    public Player(Player that){
        this.score = that.score;
        this.hintCount = that.hintCount;
    }
    public int getScore(){
        return score;
    }
    public void addScore(){
        score++;
    }
}
