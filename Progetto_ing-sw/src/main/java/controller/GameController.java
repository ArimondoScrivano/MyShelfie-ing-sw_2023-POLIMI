package controller;

import Network.RMI.ConcreteServerRMI;
import Network.messages.Message;
import Network.messages.MessageType;
import model.Dashboard;
import model.Game;
import model.Shelf;
import model.*;
import model.cgoal.CommonGoals;


import java.io.IOException;
import java.util.*;

//TODO: scrivere i messaggi per i metodi del controller che modificano qualcosa del models

public class GameController  extends Observable {
    //Model
   private Game currentGame;
    private int NumPlayers;
    private int id;

    // 0 if the game is NOT ended or 1 if the Game Ended
    private int end;

    //View
    //UI userInterface;
    //Chat currentChat;


    public void setId(int id) {
        this.id = id;
    }

    public int getId(){
        return id;
    }
    public void setEnd(int end) {
        this.end = end;
    }


    public int getEnd(){
        return end;
    }
    //Constructor of the game
    public GameController(int NumPlayers, Observer serverCreator) {
        super();
        this.addObserver(serverCreator);
        this.NumPlayers= NumPlayers;
        this.end=0;
        id=0;
        //List of players from the pre-game
        List<Player> playersList = new ArrayList<>();
        Dashboard dashboard = new Dashboard(NumPlayers, new Bag());
        this.currentGame = new Game(0, dashboard, playersList,NumPlayers);
    }

    public void createPlayer(int id_new, String np){
        Player NewPlayer= new Player(id_new, np);
        currentGame.getPlayers().add(NewPlayer);

        if(currentGame.getPlayers().size()==NumPlayers) {
            started();
        }
    }

    public int getNumPlayers() {
        return this.NumPlayers;
    }

public int getPlayersFilled(){
        return currentGame.getPlayers().size();
}
public List<Player> getPlayersList(){
        return this.currentGame.getPlayers();
    }

    public List<CommonGoals> getCommonGoals(){
        return currentGame.getCommonGoals();
    }
    public boolean isFull(){
        if(currentGame.getPlayers().size()==NumPlayers){
            started();
            return true;
        }else {
            return false;
        }
    }


    public void somethingChanged(){
        MessageType m= MessageType.SOMETHINGCHANGED;
        Message msg= new Message(id, m);
        notifyObservers();

    }


    public void started(){
        MessageType m= MessageType.GAME_STARTING;
        Message msg= new Message(id, m);
        notifyObservers();

    }

    public void ended(){
        setEnd(1);
        //create a notify message
        MessageType m= MessageType.GAME_ENDING;
        Message msg= new Message(id,m);
        notifyObservers();
    }


    // this method is intended as modify and pick the next player when the current player finished his turn
    public void pickNextPlayer(){
        //CHECK IF THE OLD CURRENT PLAYER HAS COMPLETED SOME COMMON GOALS
        checkPoints();
        int flagPreviusDone=0;
        //check finish
        if(playerTurn().isShelfCompleted()){
            playerTurn().setLastRound(true);
        }
        //check if the last turn ended
        if(playerTurn().isLastRound()){
            flagPreviusDone=1;
        }

        // this is a control for the end of the list
        if(currentGame.getPlayers().get(currentGame.getPlayers().size()-1).equals(playerTurn())){
            currentGame.setCurrentPlayer(currentGame.getPlayers().get(0));

        }else{
            int flag=0;
            for(int i =0; i< currentGame.getPlayers().size() -1 ; i++){
                if(currentGame.getPlayers().get(i).equals(currentGame.getCurrentPlayer())){
                    flag=i+1;
                }
            }
            currentGame.setCurrentPlayer(currentGame.getPlayers().get(flag));
        }
        //check if the last turn ended
        if(playerTurn().isLastRound()){
            ended();
        }

        //check if is the last Turn
        //check if the last turn ended
        if(flagPreviusDone==1){
            playerTurn().setLastRound(true);
        }

        somethingChanged();
    }
    public Player playerTurn(){

        return currentGame.getCurrentPlayer();
    }


    public void pickTiles(Tile[] tilesPicked){
        currentGame.updateDashboard(tilesPicked);
      somethingChanged();

    }



    public boolean tileAvailablePick(List<Integer> rowsList, List<Integer> columnsList){
        //variable that counts the tiles picked
        int sizePicked= rowsList.size();
        // column== x variable
        //row == x variable
            int FlagDifferentlineOr = 0;
            //check  vertical line
            for (int index = 0; index < columnsList.size() && FlagDifferentlineOr==0; index++) {
                if (!Objects.equals(columnsList.get(index), columnsList.get(0))) {
                    FlagDifferentlineOr = 1;
                }
            }
            int FlagDifferentlineVer = 0;
                //check  orizontal line
                for (int index = 0; index < rowsList.size() && FlagDifferentlineVer==0; index++) {
                    if (!Objects.equals(rowsList.get(index), rowsList.get(0))) {
                        FlagDifferentlineVer = 1;
                    }
                }

            // check for line adjacency
            if(FlagDifferentlineOr==1 && FlagDifferentlineVer==1){
                //line adjacency not found
                return false;
            }else {
                //1-2-3 or 1-2 SEQUENCE
                int commonadjacencyFE=0;
                //3-2-1 or 3-2 SEQUENCE
                int commonadjacencyLE=0;
                //2-1-3 or 2-3-1 SEQUENCE
                int commonadjacencyME=1;

                if (sizePicked > 1) {
                    //check for orizontal common adjacency
                    if (FlagDifferentlineVer == 1) {

                        for (int index=1; index<rowsList.size(); index++){
                            // first element inserted is the fist one
                            if(rowsList.get(0)!=rowsList.get(index) + index ){
                                 commonadjacencyFE=1;
                            }
                            //first element inserted is the last one
                            if(rowsList.get(0)!=rowsList.get(index) - index ) {
                                commonadjacencyLE = 1;
                            }
                        }

                        //first element inserted is the middle one
                        //2-1-3 SEQUENCE
                        if (sizePicked==3){
                            if(rowsList.get(0)== rowsList.get(1)+1 && rowsList.get(0)== rowsList.get(2) - 1){
                                commonadjacencyME=0;
                                //2-3-1 SEQUENCE
                            } else if (rowsList.get(0)== rowsList.get(1)-1 && rowsList.get(0)== rowsList.get(2) + 1) {
                                commonadjacencyME=0;

                            }
                        }
                        //check for vertical common adjacency
                    }else{

                        for (int index=1; index<columnsList.size(); index++){
                            // first element inserted is the fist one
                            if(columnsList.get(0)!=columnsList.get(index) + index ){
                                commonadjacencyFE=1;
                            }
                            //first element inserted is the last one
                            if(columnsList.get(0)!=columnsList.get(index) - index ) {
                                commonadjacencyLE = 1;
                            }
                        }

                        //first element inserted is the middle one
                        //2-1-3 SEQUENCE
                        if (sizePicked==3){
                            if(columnsList.get(0)== columnsList.get(1)+1 && columnsList.get(0)== columnsList.get(2) - 1){
                                commonadjacencyME=0;
                                //2-3-1 SEQUENCE
                            } else if (columnsList.get(0)== columnsList.get(1)-1 && columnsList.get(0)== columnsList.get(2) + 1) {
                                commonadjacencyME=0;

                            }
                        }

                    }

                }
                //no common adjacency
                if(commonadjacencyFE==1 && commonadjacencyLE==1 && commonadjacencyME==1){
                    return false;
                }else{
                    int tilesChecked=0;
                    for(int i=0; i<sizePicked; i++){
                            if(columnsList.get(i)==8 || columnsList.get(i)==0 || rowsList.get(i)==8 || rowsList.get(i)==0){
                                tilesChecked= tilesChecked+1;
                            }else{
                                //check for at least one empty near spot
                                if(currentGame.getDashboard().pickTile(rowsList.get(i)+1,columnsList.get(i) ).equals(COLOR.BLANK)
                                    ||currentGame.getDashboard().pickTile(rowsList.get(i)-1,columnsList.get(i)).equals(COLOR.BLANK)
                                    || currentGame.getDashboard().pickTile(rowsList.get(i),columnsList.get(i)-1 ).equals(COLOR.BLANK)
                                    || currentGame.getDashboard().pickTile(rowsList.get(i),columnsList.get(i)+1 ).equals(COLOR.BLANK))  {
                                    tilesChecked= tilesChecked+1;
                                }
                            }

                    }
                    //final conclusive control statement
                    if(tilesChecked== sizePicked){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
    }

    public void chooseColumnShelf(int column, Tile[] tiles, Shelf myShelf){
        myShelf.addTiles(tiles, column);
        somethingChanged();
        if(playerTurn().isShelfCompleted()){
            playerTurn().setLastRound(true);
        }
        pickNextPlayer();

    } //rita
    public boolean columnAvailable(Tile[] chosenTiles, Shelf myShelf, int selectedCol) {
        boolean available=true;
        int chosenColumn=selectedCol-1;
        Tile[][] tilesShelf= new Tile[6][5];
        tilesShelf= myShelf.getTilesShelf();
        int i=5;
        int counter=0;
        while(i>=0){
            if(tilesShelf[i][chosenColumn].getColor().equals(COLOR.BLANK)) counter++;
            i--;
        }
        if(chosenTiles.length>counter){
            available=false;
        }else {
            chooseColumnShelf(chosenColumn, chosenTiles, myShelf);
        }
        return available;
    } //rita


    /*---------------------------------------------------------------------------------------------------
    //no usage
    public Tile[] chooseOrder(Tile[] chosenTiles) throws IOException {
        String position= new String();
        Tile helper;
        for(int i=0; i<chosenTiles.length; i++){
            System.out.println("In which position do you want to place the "+chosenTiles[i].getColor()+" tile?");
            position= String.valueOf(System.in.read());
            if(position.equals("first")||position.equals("FIRST")||position.equals("First")){
                helper=chosenTiles[0];
                chosenTiles[0]=chosenTiles[i];
                chosenTiles[i]=helper;
            }if(position.equals("second")||position.equals("SECOND")||position.equals("Second")){
                helper=chosenTiles[1];
                chosenTiles[1]=chosenTiles[i];
                chosenTiles[i]=helper;
            }if(position.equals("third")||position.equals("THIRD")||position.equals("Third")){
                helper=chosenTiles[2];
                chosenTiles[2]=chosenTiles[i];
                chosenTiles[i]=helper;
            }
        }
        return chosenTiles;
    } //rita
//-------------------------------------------------------------------------------------------------------------------------
*/

    public void checkPoints() {
        //check first common goal
        if (currentGame.getCurrentPlayer().getCommonGoalsCompleted()[0] == false) {
            int partialSum = currentGame.getCommonGoals().get(0).Checker(currentGame.getCurrentPlayer().getShelf().getTilesShelf());
            if (partialSum > 0) {
                currentGame.getCurrentPlayer().setPoints(partialSum);
                currentGame.getCurrentPlayer().setCommonGoalsCompleted(0);
            }
        }
        //check second common goal
        if (currentGame.getCurrentPlayer().getCommonGoalsCompleted()[1] == false) {
            int partialSecondSum = currentGame.getCommonGoals().get(1).Checker(currentGame.getCurrentPlayer().getShelf().getTilesShelf());
            if (partialSecondSum > 0) {
                currentGame.getCurrentPlayer().setPoints(partialSecondSum);
                currentGame.getCurrentPlayer().setCommonGoalsCompleted(0);
            }
        }
        somethingChanged();
    }


    public Player checkWinner(){
        //Searching the player with the most points
        Player winner=currentGame.getPlayers().get(0);
        for(Player p : currentGame.getPlayers()){
            if(winner.getPoints()<=p.getPoints()){
                winner=p;
            }
        }
        return winner;
    }



    public Tile[][] getDashboardTiles(){
        return currentGame.getDashboardMatrix();
    }
}
