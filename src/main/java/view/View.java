package view;

import Network.GameChat.GameMessage;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.util.List;

public interface View {
    //Showing info


    /**
     * Displays the match information, including the copy of the game board, common goals,
     * the player's shelf, and the player's personal goal.
     *
     * @param copy        a copy of the game board to display.
     * @param commonGoals the list of common goals to display.
     * @param myShelf     the player's shelf to display.
     * @param pg          the player's personal goal to display.
     */
    void showMatchInfo(Tile[][] copy, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg);


    /**
     * Displays the new message or notification.
     */
    void shownewMex();


    /**
     * Displays the game chat messages.
     *
     * @param listToDisplay the list of game messages to display in the chat.
     */
    public void showGameChat(List<GameMessage> listToDisplay);



    /**
     * Prints the dashboard using the provided copy of the game board.
     *
     * @param copy the copy of the game board to print the dashboard from.
     */
    public void printDashboard(Tile[][] copy);



    /**
     * Prints the personal goal information.
     *
     * @param pg the personal goal to print the information from.
     */
    public void printPersonalGoal(PersonalGoal pg);



    /**
     * Prints the common goal information.
     *
     * @param commonGoals the list of common goals to print the information from.
     */
    void printCommonGoal(List<CommonGoals> commonGoals);



    /**
     * Prints the shelf information.
     *
     * @param myShelf the shelf to print the information from.
     */
    public void printShelf(Tile[][] myShelf);



    /**
     * Initializes the game.
     */
    public void init();



    /**
     * Initializes a new game.
     */
    public void initGame();



    /**
     * Ends the game and displays the outcome.
     *
     * @param esito the outcome of the game.
     */
    public void endGame(String esito);

}
