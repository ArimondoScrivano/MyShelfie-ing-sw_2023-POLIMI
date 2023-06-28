package view;

import java.util.List;

//USER INPUT
/**
 * This interface represents the user interface for user input and output.
 */
public interface UI {


    /**
     * Displays the points and personal goal points for a player.
     *
     * @param myPoint    the total points of the player.
     * @param myPGpoints the personal goal points of the player.
     */
    void displayPoints(int myPoint, int myPGpoints);



    /**
     * Asks the player to input a column number.
     *
     * @return the column number inputted by the player.
     */
    int askColumn();



    /**
     * Asks the player to pick a specified number of tiles.
     *
     * @param numberOfTile the number of tiles to be picked by the player.
     * @return a list of integers representing the tiles picked by the player.
     */
    List<Integer> askTilesToPick(int numberOfTile);



    /**
     * Asks the player to input the number of tiles.
     *
     * @return the number of tiles inputted by the player.
     */
    int askNumberOfTiles();



    //public List<String> askNewChatMessage(List<String> playersName, String myplayername);


    /**
     * Asks the user to input the number of players.
     *
     * @return the number of players inputted by the user.
     */
    int askNumberOfPlayers();



    /**
     * Asks the player if they want to start a new game.
     *
     * @return true if the player wants to start a new game, false otherwise.
     */
    boolean askNewGame();



    /**
     * Asks the player to input their nickname.
     *
     * @return the nickname inputted by the player.
     */
    String askNickname();



    /**
     * Asks the player to choose a connection option.
     *
     * @return the connection option chosen by the player.
     */
    int askConnection();

    /**
     * Gets the state of the clicked status.
     *
     * @return The state of the clicked status.
     */
    boolean getClicked();

    /**
     * Asks the user to input an IP address.
     *
     * @return The IP address provided by the user.
     */
    String askIP();

    /**
     * Prompts the user to press any key.
     *
     * @return The result indicating if any key is pressed.
     */
    boolean pressAnyKey();

}
