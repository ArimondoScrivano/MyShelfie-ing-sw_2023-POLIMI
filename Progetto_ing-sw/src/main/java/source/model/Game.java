package source.model;
import source.model.cgoal.*;
import java.util.*;

public class Game extends Observable {
    //Number of game
    private int id;
    private final Dashboard dashboard;
    private Map<Integer, List<Player>> players;
    private CommonGoals[] commonGoals;
    private List<Integer> pointsCommonGoal_1;
    private List<Integer> pointsCommonGoal_2;
    private Player currentPlayer;
    private int availableTiles;
    private boolean endGame;

    public Game(int id, Dashboard dashboard, List<Player> pl, CommonGoals[] commonGoals, int availableTiles, boolean endGame){
        //Setting the number of the game
        this.id=id;
        //Setting the reference to the dashboard
        this.dashboard=dashboard;
        //Updating the Map of players
        this.players = new HashMap<Integer, List<Player>>();
        //Insert the list of the first players to play
        players.put(id, pl);
        //Finding the first element of the map on a key
        setCurrentPlayer(players.get(id).get(0));
        //Setting the commonGoals based on the number of players
        int numberOfPlayers = players.get(id).size();
        switch(numberOfPlayers){
            case 2:
                setCommonGoals(commonGoals);
                //Setting the points for the commonGoals
                pointsCommonGoal_1.add(0, 4);
                pointsCommonGoal_1.add(1, 8);
                //2nd commonGoal
                pointsCommonGoal_2.add(0, 4);
                pointsCommonGoal_2.add(1, 8);
            case 3:
                setCommonGoals(commonGoals);
                //Setting the points for the commonGoals
                pointsCommonGoal_1.add(0, 4);
                pointsCommonGoal_1.add(1, 6);
                pointsCommonGoal_1.add(2, 8);
                //2nd commonGoal
                pointsCommonGoal_2.add(0, 4);
                pointsCommonGoal_2.add(1, 6);
                pointsCommonGoal_2.add(2, 8);
            case 4:
                setCommonGoals(commonGoals);
                pointsCommonGoal_1 = new ArrayList<>();
                //Setting the points for the commonGoals
                pointsCommonGoal_1.add(0, 2);
                pointsCommonGoal_1.add(1, 4);
                pointsCommonGoal_1.add(2, 6);
                pointsCommonGoal_1.add(3, 8);
                //2nd commonGoal
                pointsCommonGoal_2 = new ArrayList<>();
                pointsCommonGoal_2.add(0, 2);
                pointsCommonGoal_2.add(1, 4);
                pointsCommonGoal_2.add(2, 6);
                pointsCommonGoal_2.add(3, 8);

        }

        //Game just started-->no endgame yet
        this.endGame=false;

    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }
    //Setting the new currentPlayer
    public void setCurrentPlayer(Player pl){
        this.currentPlayer=pl;
    }

    //Return the commonGoals array
    public CommonGoals[] getCommonGoals() {
        return this.commonGoals;
    }

    public Integer getCommonGoalsPoints(CommonGoals goal, int idCommonGoal){
        if(idCommonGoal==1){
            return this.pointsCommonGoal_1.lastIndexOf(pointsCommonGoal_1);
        }
        return this.pointsCommonGoal_2.lastIndexOf(pointsCommonGoal_1);
    }

    //TODO: lista con associazione temporanea
    public void setCommonGoals(CommonGoals[] commonGoals){
        //Two random numbers to get the 2 id of the common_goals of the game
        Random rand=new Random();
        //Random number between 0+1-11+1
        int id_1=rand.nextInt(12)+1;
        int id_2;
        do {
            id_2 = rand.nextInt(12) + 1;
        }while(id_2==id_1);
        //Setting the commonGoals array with the id of the two commonGoals
        switch(id_1){
            case 1:
                commonGoals[0] = new SixPairsEqualCommonGoals();
            case 2:
                commonGoals[0] = new diagonalEqualCommonGoals();
            case 3:
                commonGoals[0] = new CornersEqualsCommonGoals();
            case 4:
                commonGoals[0] = new fourRowsCommonGoals();
            case 5:
                commonGoals[0] = new FourVerticalCommonGoals();
            case 6:
                commonGoals[0] = new twoColumnsCommonGoals();
            case 7:
                commonGoals[0] = new subMatrix2CommonGoals();
            case 8:
                commonGoals[0] = new twoRowsAllDifferentCommonGoals();
            case 9:
                commonGoals[0] = new threeDisegualColumnsCommonGoals();
            case 10:
                commonGoals[0] = new equalXCommonGoals();
            case 11:
                commonGoals[0] = new eightEqualCommonGoals();
            case 12:
                commonGoals[0] = new fiveColumnsCommonGoals();
        }

        switch(id_1){
            case 1:
                commonGoals[1] = new SixPairsEqualCommonGoals();
            case 2:
                commonGoals[1] = new diagonalEqualCommonGoals();
            case 3:
                commonGoals[1] = new CornersEqualsCommonGoals();
            case 4:
                commonGoals[1] = new fourRowsCommonGoals();
            case 5:
                commonGoals[1] = new FourVerticalCommonGoals();
            case 6:
                commonGoals[1] = new twoColumnsCommonGoals();
            case 7:
                commonGoals[1] = new subMatrix2CommonGoals();
            case 8:
                commonGoals[1] = new twoRowsAllDifferentCommonGoals();
            case 9:
                commonGoals[1] = new threeDisegualColumnsCommonGoals();
            case 10:
                commonGoals[1] = new equalXCommonGoals();
            case 11:
                commonGoals[1] = new eightEqualCommonGoals();
            case 12:
                commonGoals[1] = new fiveColumnsCommonGoals();
        }
    }

    public void updatePoints(Player pl, int points){
        pl.points+=points;
    }

    //The endgame token is taken by the current player if his shelf is completed
    public void endGameToken(){
        if(getCurrentPlayer().shelfCompleted==true){
            //The endgame token value is 1
            getCurrentPlayer().points++;
            endGame=true;
        }
    }
}
