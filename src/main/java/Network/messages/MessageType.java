package Network.messages;

/**
 * The MessageType enum represents different types of messages used in the application.
 * Each message type corresponds to a specific event or action.
 */
public enum MessageType {
    GAME_STARTING,       // Indicates that the game is starting
    SOMETHINGCHANGED,    // Indicates that something has changed
    GAME_ENDING,         // Indicates that the game is ending
    LOBBYCREATED,        // Indicates that a lobby has been created
    LOBBYCLOSED,         // Indicates that a lobby has been closed
    DISCONNECT           // Indicates a disconnection event
}
