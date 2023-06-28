package model.cgoal;

import model.Tile;

import java.util.List;

/**
 * The CommonGoals interface represents the common goals in the game.
 * It defines the contract for implementing common goals.
 */
public interface  CommonGoals{

   /**
    * Retrieves the current point value.
    *
    * @return The current point value.
    */
    int getCurrent_point();

   /**
    * Checks the result of a condition based on the provided matrix of tiles.
    *
    * @param matrix The matrix of tiles to check.
    * @return The result of the condition. It can be 0 or the current point.
    */
    int Checker(Tile[][] matrix);

   /**
    * Retrieves the list of scores.
    *
    * @return The list of scores.
    */
    List<Integer> getScoreList();

   /**
    * Prints the layout of the game's visual representation.
    * This method prints a formatted layout of the game's visual representation
    * to the console, representing the structure or design of the game.
    */
    void printLayout();


   /**
    * Retrieves the ID of the Common Goal.
    *
    * @return The ID, type value of integer,of the Common Goals .
    */
    int getId();

}
