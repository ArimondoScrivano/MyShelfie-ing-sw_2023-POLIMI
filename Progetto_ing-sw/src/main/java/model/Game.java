package model;
import model.cgoal.*;
import java.util.*;

public class Game extends Observable {
    //Number of game
    private int id;
    //Dashboard reference
    private final Dashboard dashboard;
    //Players
    private List<Player> players;
    private Player currentPlayer;
    static int MAX_PLAYERS=4;
    //Common goals
    private CommonGoals[] commonGoals;
    private List<Integer> pointsCommonGoal_1;
    private List<Integer> pointsCommonGoal_2;
    private boolean endGame;

    public Game(int id, Dashboard dashboard, List<Player> pl){
        //Setting the number of the game
        this.id=id;
        //Setting the reference to the dashboard
        this.dashboard=dashboard;
        //Updating the Map of players
        this.players = new ArrayList<>(pl);
        //Setting the first player
        this.currentPlayer = pl.get(0);
        //Setting the commonGoals based on the number of players
        int numberOfPlayers = players.size();
        switch(numberOfPlayers){
            case 2:
                setCommonGoals();
                //Setting the points for the commonGoals
                pointsCommonGoal_1.add(0, 4);
                pointsCommonGoal_1.add(1, 8);
                //2nd commonGoal
                pointsCommonGoal_2.add(0, 4);
                pointsCommonGoal_2.add(1, 8);
            case 3:
                setCommonGoals();
                //Setting the points for the commonGoals
                pointsCommonGoal_1.add(0, 4);
                pointsCommonGoal_1.add(1, 6);
                pointsCommonGoal_1.add(2, 8);
                //2nd commonGoal
                pointsCommonGoal_2.add(0, 4);
                pointsCommonGoal_2.add(1, 6);
                pointsCommonGoal_2.add(2, 8);
            case 4:
                setCommonGoals();
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
        if(players.indexOf(pl)!=players.size()){
            this.currentPlayer=pl;
        }else{
            this.currentPlayer=players.get(0);
        }
    }

    //Return the commonGoals array
    public CommonGoals[] getCommonGoals() {
        return this.commonGoals;
    }

    public int getCommonGoalsPoints(CommonGoals[] goal, int idCommonGoal) throws ArrayIndexOutOfBoundsException{
        if(idCommonGoal==1){
            //Return the last-1 element-->this will be removed if a commonGoal is completed
            return pointsCommonGoal_1.get(pointsCommonGoal_1.size() - 1);
        }
        //Return the last-1 element-->this will be removed if a commonGoal is completed
        return pointsCommonGoal_2.get(pointsCommonGoal_2.size() - 1);
    }

    public void setCommonGoals(){
        //Two random numbers to get the 2 id of the common_goals of the game
        Random rand=new Random();
        //Random number between 0+1-11+1
        int id_1=rand.nextInt(12)+1;
        int id_2=rand.nextInt(12)+1;

        while(id_1==id_2){
            id_2=rand.nextInt(12) + 1;
        }

        //Setting the commonGoals array with the id of the two commonGoals
        List<CommonGoals> temporaryCommonGoals = Arrays.asList(
                new SixPairsEqualCommonGoals(),
                new diagonalEqualCommonGoals(),
                new CornersEqualsCommonGoals(),
                new fourRowsCommonGoals(),
                new FourVerticalCommonGoals(),
                new twoColumnsCommonGoals(),
                new subMatrix2CommonGoals(),
                new twoRowsAllDifferentCommonGoals(),
                new threeDisegualColumnsCommonGoals(),
                new equalXCommonGoals(),
                new eightEqualCommonGoals(),
                new fiveColumnsCommonGoals()
        );

        List<CommonGoals> commonGoalsList = new ArrayList<>();
        commonGoalsList.add(0, temporaryCommonGoals.get(id_1));
        commonGoalsList.add(1, temporaryCommonGoals.get(id_2));

        for(int i = 0; i < commonGoalsList.size(); i++){
            commonGoals=commonGoalsList.toArray(new CommonGoals[i]);
        }
    }

    public void updatePointsCommonGoals(){
        for(int i=0; i<2; i++){
            if(!currentPlayer.commonGoalsCompleted[i] && currentPlayer.commonGoalCompleted(commonGoals, i)){
                currentPlayer.points += getCommonGoalsPoints(commonGoals, i);
                if(i==1){
                    pointsCommonGoal_1.remove(pointsCommonGoal_1.size() - 1);
                }else{
                    pointsCommonGoal_2.remove(pointsCommonGoal_2.size() - 1);
                }
            }
        }

    }

    //The endgame token is taken by the current player if his shelf is completed
    public void endGameToken(){
        if(currentPlayer.shelfCompleted){
            //The endgame token value is 1
            getCurrentPlayer().points++;
            endGame=true;
        }
    }
}
