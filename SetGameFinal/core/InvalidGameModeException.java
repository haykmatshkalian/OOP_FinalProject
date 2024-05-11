package SetGameFinal.core;

/**
 * An exception thrown when an invalid game mode is specified.
 */
public class InvalidGameModeException extends Exception {

    /**
     * Constructs a new InvalidGameModeException with a default message.
     */
    public InvalidGameModeException(){
        super("Invalid Game Mod");
    }
    /**
     * Constructs a new InvalidGameModeException with a specified message.
     *
     * @param message The detail message.
     */
    public InvalidGameModeException(String message) {
        super(message);
    }
}
