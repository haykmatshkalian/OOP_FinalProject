package SetGameFinal.core;

public class Player {
    private int score;
    private int hintCount;
    private final GameTimer playerTimer;
    private final String nickname;
    public Player(){
        score = 0;
        hintCount = 0;
        nickname = null;
        playerTimer = new GameTimer(0);
    }
    public Player(int seconds,int hintCount,String nickname){
        this.score = 0;
        this.playerTimer = new GameTimer(seconds);
        this.hintCount = hintCount;
        this.nickname = nickname;
    }
    public Player(Player that){
        this.score = that.score;
        this.hintCount = that.hintCount;
        this.nickname = that.nickname;
        this.playerTimer = that.playerTimer;
    }
    public int getScore(){
        return score;
    }
    public int getHintCount() {
        return hintCount;
    }
    public String getNickname() {
        return nickname;
    }
    public GameTimer getPlayerTimer() {
        return playerTimer;
    }
    public void addScore(){
        score++;
    }
    public void setHintCount(int hint) {
        this.hintCount = hint;
    }
}
