package Network.GameChat;

import java.io.Serializable;

/**
 * The GameMessage class represents a serializable message used for communication between game components.
 * It encapsulates information related to game actions, events, or updates.
 */
public class GameMessage implements Serializable {
    private final String myGameMessage;
    private final String Name_Creator;
    private final String receiver;

    /**
     * Constructs a GameMessage object.
     *
     * @param myGameMessage The game message.
     * @param Name_Creator  The name of the message creator.
     * @param receiver      The receiver of the message.
     */
    public GameMessage(String myGameMessage, String Name_Creator, String receiver) {
        this.myGameMessage = myGameMessage;
        this.Name_Creator = Name_Creator;
        this.receiver = receiver;
    }

    /**
     * Gets the receiver of the message.
     *
     * @return The receiver of the message.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Gets the game message.
     *
     * @return The game message.
     */
    public String getMyGameMessage() {
        return myGameMessage;
    }

    /**
     * Gets the name of the message creator.
     *
     * @return The name of the message creator.
     */
    public String getName_Creator() {
        return Name_Creator;
    }
}


