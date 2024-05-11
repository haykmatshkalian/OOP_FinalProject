package SetGameFinal.core;

/**
 * Represents a player in the Set game.
 */
public class Player {
    private int score;
    private int hintCount;
    private final GameTimer playerTimer;
    private final String nickname;

    /**
     * Constructs a player with default values.
     */
    public Player(){
        score = 0;
        hintCount = 0;
        nickname = null;
        playerTimer = new GameTimer(0);
    }

    /**
     * Constructs a player with specified values.
     *
     * @param seconds The initial time in seconds for the player's timer.
     * @param hintCount The initial hint count for the player.
     * @param nickname The nickname of the player.
     */
    public Player(int seconds,int hintCount,String nickname){
        this.score = 0;
        this.playerTimer = new GameTimer(seconds);
        this.hintCount = hintCount;
        this.nickname = nickname;
    }

    /**
     * Constructs a player by copying another player's attributes.
     *
     * @param that The player to copy from.
     */
    public Player(Player that){
        this.hintCount = that.hintCount;
        this.nickname = that.nickname;
        this.playerTimer = that.playerTimer;
    }

    /**
     * Gets the player's score.
     *
     * @return The score of the player.
     */
    public int getScore(){
        return score;
    }

    /**
     * Gets the remaining hint count of the player.
     *
     * @return The hint count of the player.
     */
    public int getHintCount() {
        return hintCount;
    }

    /**
     * Gets the nickname of the player.
     *
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Gets the timer of the player.
     *
     * @return The timer of the player.
     */
    public GameTimer getPlayerTimer() {
        return playerTimer;
    }

    /**
     * Increases the player's score by one.
     */
    public void addScore(){
        score++;
    }

    /**
     * Sets the hint count of the player.
     *
     * @param hint The hint count to set.
     */
    public void setHintCount(int hint) {
        this.hintCount = hint;
    }
}
