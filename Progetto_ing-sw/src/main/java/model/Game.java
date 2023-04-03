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

    //Get the reference to the list which contains the points for the common goals
    public List<Integer> getCommonGoalsPoints(int idCommonGoal) throws ArrayIndexOutOfBoundsException{
        if(idCommonGoal==1){
            //Return the points of the 1st common goal
            return pointsCommonGoal_1;
        }
        //Return the points of the 2nd common goal
        return pointsCommonGoal_2;
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
List<Integer>provaLista= new ArrayList<>();
        provaLista.add(1);
        provaLista.add(1);


        //Setting the commonGoals array with the id of the two commonGoals
        List<CommonGoals> temporaryCommonGoals = Arrays.asList(
                new SixPairsEqualCommonGoals(provaLista),
                new diagonalEqualCommonGoals(provaLista),
                new CornersEqualsCommonGoals(provaLista),
                new fourRowsCommonGoals(provaLista),
                new FourVerticalCommonGoals(provaLista),
                new twoColumnsCommonGoals(provaLista),
                new subMatrix2CommonGoals(provaLista),
                new twoRowsAllDifferentCommonGoals(provaLista),
                new threeDisegualColumnsCommonGoals(provaLista),
                new equalXCommonGoals(provaLista),
                new eightEqualCommonGoals(provaLista),
                new fiveColumnsCommonGoals(provaLista)
        );

        this.commonGoals = new ArrayList<>();
        //Create the commonGoal list
        this.commonGoals.add(0, temporaryCommonGoals.get(id_1));
        this.commonGoals.add(1, temporaryCommonGoals.get(id_2));
    }

    //Updating the points of the common Goal if completed from the current player
    public void updatePointsCommonGoals(){
        for(int i=0; i<2; i++){
            if(!currentPlayer.getCommonGoalsCompleted()[i] && currentPlayer.commonGoalCompleted(commonGoals, i)){
                currentPlayer.points += getCommonGoalsPoints(i).get(getCommonGoalsPoints(i).size()-1);
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
        if(currentPlayer.isShelfCompleted()){
            //The endgame token value is 1
            getCurrentPlayer().points++;
            endGame=true;
        }
    }

    //Get method for the endGame
    public boolean getEndGame(){
        return endGame;
    }
}
