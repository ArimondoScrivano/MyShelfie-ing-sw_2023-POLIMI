package view;

import java.util.List;

//USER INPUT
public interface UI {


    /**
     * Displays the points and personal goal points for a player.
     *
     * @param myPoint    the total points of the player.
     * @param myPGpoints the personal goal points of the player.
     */
    public void displayPoints(int myPoint, int myPGpoints);



    /**
     * Asks the player to input a column number.
     *
     * @return the column number inputted by the player.
     */
    public int askColumn();



    /**
     * Asks the player to pick a specified number of tiles.
     *
     * @param numberOfTile the number of tiles to be picked by the player.
     * @return a list of integers representing the tiles picked by the player.
     */
    public List<Integer> askTilesToPick(int numberOfTile);



    /**
     * Asks the player to input the number of tiles.
     *
     * @return the number of tiles inputted by the player.
     */
    public int askNumberOfTiles();



    //public List<String> askNewChatMessage(List<String> playersName, String myplayername);


    /**
     * Asks the user to input the number of players.
     *
     * @return the number of players inputted by the user.
     */
    public int askNumberOfPlayers();



    /**
     * Asks the player if they want to start a new game.
     *
     * @return true if the player wants to start a new game, false otherwise.
     */
    public boolean askNewGame();



    /**
     * Asks the player to input their nickname.
     *
     * @return the nickname inputted by the player.
     */
    public String askNickname();



    /**
     * Asks the player to choose a connection option.
     *
     * @return the connection option chosen by the player.
     */
    public int askConnection();

    /**
     * Gets the state of the clicked status.
     *
     * @return The state of the clicked status.
     */
    public boolean getClicked();

    /**
     * Asks the user to input an IP address.
     *
     * @return The IP address provided by the user.
     */
    public String askIP();

    /**
     * Prompts the user to press any key.
     *
     * @return The result indicating if any key is pressed.
     */
    public boolean pressAnyKey();

}
