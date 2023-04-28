package Network.RMI.messages;

public enum MessageType {
    //Lobby
    LOGIN, LOGIN_REPLY, N_PLAYER_REQUEST, N_PLAYER_REPLY,
    LOBBY,
    WAITING_FOR_OTHER_PLAYERS,
    GAME_STARTING,

    //Game starting
    //Picking randomly the first player
    PICK_FIRST_PLAYER,
    //Picking the tiles
    PICK_TILES,
    CHOOSE_COLUMN_SHELF,
    COLUMN_SHELF_AVAILABLE,
    //Show match info
    MATCH_INFO,
    //Someone was disconnected
    //The game will finish
    DISCONNECTION,

    //Someone completed the shelf
    ENDGAME_STARTED,
    //The game has ended
    GAME_ENDED,
    //Message to the player that won the game
    WIN_MESSAGE,
    //Message to the other players
    LOSE_MESSAGE
}
