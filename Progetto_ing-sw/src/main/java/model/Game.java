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
    private List<CommonGoals> commonGoals;
    private List<Integer> pointsCommonGoal_1;
    private List<Integer> pointsCommonGoal_2;
    private boolean endGame;

    public Game(int id, Dashboard dashboard, List<Player> pl){
        //Setting the number of the game
        this.id=id;
        //Setting the reference to the dashboard
        this.dashboard=dashboard;
        //Updating the List of players
        this.players = new ArrayList<>(pl);
        //Setting the first player
        this.currentPlayer = pl.get(0);
        //Setting the commonGoals based on the number of players
        int numberOfPlayers = players.size();
        switch (numberOfPlayers) {
            case 2 -> {
                setCommonGoals();
                //Setting the points for the commonGoals
                this.pointsCommonGoal_1 = Arrays.asList(4, 8);
                //2nd commonGoal
                this.pointsCommonGoal_2 = Arrays.asList(4, 8);
            }
            case 3 -> {
                setCommonGoals();
                //Setting the points for the commonGoals
                this.pointsCommonGoal_1 = Arrays.asList(4, 6, 8);
                //2nd commonGoal
                this.pointsCommonGoal_2 = Arrays.asList(4, 6, 8);
            }
            case 4 -> {
                setCommonGoals();
                //Setting the points for the commonGoals
                this.pointsCommonGoal_1 = Arrays.asList(2, 4, 6, 8);
                //2nd commonGoal
                this.pointsCommonGoal_2 = Arrays.asList(2, 4, 6, 8);
            }
        }

        //Game just started-->no endgame yet
        this.endGame=false;

    }

    public List<Player> getPlayers(){
        return this.players;
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
    public List<CommonGoals> getCommonGoals() {
        return this.commonGoals;
    }

    //TODO: cambio tipo ritorno, List<integer> al posto di int
    public List<Integer> getCommonGoalsPoints(List<CommonGoals> goal, int idCommonGoal) throws ArrayIndexOutOfBoundsException{
        if(idCommonGoal==1){
            //Return the last-1 element-->this will be removed if a commonGoal is completed
            return pointsCommonGoal_1;
        }
        //Return the last-1 element-->this will be removed if a commonGoal is completed
        return pointsCommonGoal_2;
    }

    public void setCommonGoals(){
        //Two random numbers to get the 2 id of the common_goals of the game
        Random rand=new Random();
        //Random number between 0-11-->12 bound
        int id_1=rand.nextInt(12);
        int id_2=rand.nextInt(12);

        while(id_1==id_2){
            id_2=rand.nextInt(12);
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

        this.commonGoals = new ArrayList<>();
        //Create the commonGoal list
        this.commonGoals.add(0, temporaryCommonGoals.get(id_1));
        this.commonGoals.add(1, temporaryCommonGoals.get(id_2));
    }

    //Updating the points of the common Goal if completed from the current player
    public void updatePointsCommonGoals(){
        for(int i=0; i<2; i++){
            if(!currentPlayer.commonGoalsCompleted[i] && currentPlayer.commonGoalCompleted(commonGoals, i)){
                currentPlayer.points += getCommonGoalsPoints(commonGoals, i).get(getCommonGoalsPoints(commonGoals, i).size()-1);
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
