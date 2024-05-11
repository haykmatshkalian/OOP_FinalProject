package SetGameFinal.core;

public class InvalidGameModeException extends Exception {
    public InvalidGameModeException(){
        super("Invalid Game Mod");
    }
    public InvalidGameModeException(String message) {
        super(message);
    }
}
