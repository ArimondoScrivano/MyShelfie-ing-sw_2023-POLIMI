package model;
import model.cgoal.*;

import java.io.Serializable;
import java.util.*;

public class Game implements Serializable {
    //Number of game
    private int id;
    private int numberOfPlayers;
    //Dashboard reference
    private final Dashboard dashboard;
    //Players
    private List<Player> players;
    private Player currentPlayer;
    //Common goals
    private List<CommonGoals> commonGoals;
    private List<Integer> pointsCommonGoal;
    //Token for the first player to complete the shelf
    private boolean endGame;


    /**
     * Constructs a new Game instance with the specified parameters.
     *
     * @param id              the number of the game.
     * @param dashboard       the reference to the dashboard.
     * @param pl              the list of players.
     * @param numberOfPlayers the number of players in the game.
     */
    public Game(int id, Dashboard dashboard, List<Player> pl, int numberOfPlayers){
        //Setting the number of the game
        this.id=id;
        //Setting the reference to the dashboard
        this.dashboard=dashboard;
        //Updating the List of players
        this.players = new ArrayList<>(pl);
        for(Player p : pl){
            System.out.println("Player in game: "+p.getName());
        }
        //Setting the commonGoals based on the number of players
        this.numberOfPlayers = numberOfPlayers;
        switch (this.numberOfPlayers) {
            case 2 -> {
                //Setting the points for the commonGoals
                this.pointsCommonGoal = Arrays.asList(4, 8);
                setCommonGoals();
            }
            case 3 -> {
                //Setting the points for the commonGoals
                this.pointsCommonGoal = Arrays.asList(4, 6, 8);
                setCommonGoals();
            }
            case 4 -> {
                //Setting the points for the commonGoals
                this.pointsCommonGoal = Arrays.asList(2, 4, 6, 8);
                setCommonGoals();
            }
        }

        //Game just started-->no endgame yet
        this.endGame=false;
    }


    /**
     * Updates the dashboard by setting the specified tiles at the given coordinates to BLANK.
     *
     * @param xCord the list of x-coordinates of the tiles to be updated.
     * @param yCord the list of y-coordinates of the tiles to be updated.
     * @throws Exception if there is an error during the update process or if the dashboard needs to be refilled.
     */
    public void updateDashboard(List<Integer> xCord,List<Integer> yCord ) throws Exception{
            getDashboard().updateDashboard(xCord, yCord);
    }


    /**
     * Adds the specified tiles to the given shelf at the specified column.
     *
     * @param shelfToModify the shelf to modify.
     * @param tilesPicked   the tiles to be added to the shelf.
     * @param column        the column at which the tiles should be added.
     */
    public void addTiles(Shelf shelfToModify, Tile[] tilesPicked, int column){
        shelfToModify.addTiles(tilesPicked,column);
    }

    /**
     * Returns the dashboard object associated with the game.
     *
     * @return the dashboard object.
     */
    public Dashboard getDashboard() {
        return this.dashboard;
    }

    /**
     * Returns the matrix representation of the dashboard.
     *
     * @return the matrix representation of the dashboard.
     */
    public Tile[][] getDashboardMatrix() {
        return dashboard.getTiles();
    }

    /**
     * Returns the list of players participating in the game.
     *
     * @return the list of players.
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the current player in the game.
     *
     * @return the current player.
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Sets the current player in the game.
     *
     * @param pl the player to set as the current player.
     */
    public void setCurrentPlayer(Player pl) {
        this.currentPlayer = pl;
    }

    /**
     * Returns the list of common goals in the game.
     *
     * @return the list of common goals.
     */
    public List<CommonGoals> getCommonGoals() {
        return this.commonGoals;
    }
    //Setting the common Goals for the game
    /**
     * Sets the common goals for the game.
     * Two random common goals are selected from the available options and assigned to the game.
     * The common goals determine the objectives that players aim to achieve during the game.
     */
    public void setCommonGoals(){
        //Two random numbers to get the 2 id of the common_goals of the game
        Random rand=new Random();
        //Random number between 0-11-->12 bound
        int id_1=rand.nextInt(12);
        int id_2=rand.nextInt(12);

        while(id_1==id_2){
            id_2=rand.nextInt(12);
        }
        //Adding the common Goal points
        List<Integer>commonGoalPoints = new ArrayList<>(pointsCommonGoal);
        //Setting the points

        //Setting the commonGoals array with the id of the two commonGoals
        List<CommonGoals> temporaryCommonGoals = Arrays.asList(
                new SixPairsEqualCommonGoals(commonGoalPoints),
                new diagonalEqualCommonGoals(commonGoalPoints),
                new CornersEqualsCommonGoals(commonGoalPoints),
                new fourRowsCommonGoals(commonGoalPoints),
                new FourVerticalCommonGoals(commonGoalPoints),
                new twoColumnsCommonGoals(commonGoalPoints),
                new subMatrix2CommonGoals(commonGoalPoints),
                new twoRowsAllDifferentCommonGoals(commonGoalPoints),
                new threeDisegualColumnsCommonGoals(commonGoalPoints),
                new equalXCommonGoals(commonGoalPoints),
                new eightEqualCommonGoals(commonGoalPoints),
                new fiveColumnsCommonGoals(commonGoalPoints)
        );

        this.commonGoals = new ArrayList<>();
        //Create the commonGoal list
        this.commonGoals.add(0, temporaryCommonGoals.get(id_1));
        this.commonGoals.add(1, temporaryCommonGoals.get(id_2));
    }

    /**
     * Retrieves the status of the end game flag.
     *
     * @return true if the game has ended, false otherwise.
     */
    public boolean getEndGame() {
        return endGame;
    }

    /**
     * Sets the end game flag to true.
     * This indicates that the game has ended.
     */
    public void setEndGameTrue() {
        this.endGame = true;
    }
}
