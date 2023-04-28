package model;
import model.cgoal.*;

import java.io.Serializable;
import java.util.*;

public class Game extends Observable implements Serializable {
    //Number of game
    private int id;
    //Dashboard reference

    private int numberOfPlayers;
    private final Dashboard dashboard;
    //Players
    private List<Player> players;
    private Player currentPlayer;
    static int MAX_PLAYERS=4;
    //Common goals
    private List<CommonGoals> commonGoals;
    private List<Integer> pointsCommonGoal;
    private boolean endGame;

    public Game(int id, Dashboard dashboard, List<Player> pl, int numberOfPlayers){
        //Setting the number of the game
        this.id=id;
        //Setting the reference to the dashboard
        this.dashboard=dashboard;
        //Updating the List of players
        this.players = new ArrayList<>(pl);
        //Setting the first player
        this.currentPlayer = pl.get(0);
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

    public Dashboard getDashboard() {
        return this.dashboard;
    }
    public Tile[][] getDashboardMatrix(){
        return dashboard.getTilesCopy();
    }
    public List<Player> getPlayers(){
        List<Player> players_copy= new ArrayList<>();
        players_copy.addAll(this.players);
        return players_copy;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    //Setting the new currentPlayer at the "next iteration" of the match
    public void setCurrentPlayer(Player pl){
            this.currentPlayer=pl;
    }

    //Return the commonGoals array
    public List<CommonGoals> getCommonGoals() {
        return this.commonGoals;
    }

    //Setting the common Goals for the game
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

    //The endgame token is taken by the current player if his shelf is completed
    public void endGameToken(){
        if(currentPlayer.isShelfCompleted()){
            //The endgame token value is 1
            getCurrentPlayer().setPointsEndGame();
            endGame=true;
        }
    }

    //Get method for the endGame
    public boolean getEndGame(){
        return endGame;
    }
}
