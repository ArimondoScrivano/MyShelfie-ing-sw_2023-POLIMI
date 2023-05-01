package controller;

import Network.RMI.ConcreteServerRMI;
import Network.RMI.Server_RMI;
import Network.messages.Message;
import Network.messages.MessageType;
import model.Dashboard;
import model.Game;
import model.Shelf;
import model.*;
import model.cgoal.CommonGoals;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

//TODO: scrivere i messaggi per i metodi del controller che modificano qualcosa del models

public class GameController  extends Observable {
    //Model
   private Game currentGame;
    private int NumPlayers;
    private int id;
    private Server_RMI myServer;
    // 0 if the game is NOT ended or 1 if the Game Ended
    private int end;

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
    public GameController(int NumPlayers, Server_RMI serverCreator, String creatorLobby) {
        super();
        this.myServer= serverCreator;
        this.NumPlayers= NumPlayers;
        this.end=0;
        id=0;
        //List of players from the pre-game
        List<Player> playersList = new ArrayList<>();
        playersList.add(new Player(0, creatorLobby));
        Dashboard dashboard = new Dashboard(NumPlayers, new Bag());
        this.currentGame = new Game(0, dashboard, playersList,NumPlayers);
    }

    public GameController(int NumPlayers, Server_RMI serverCreator) {
        super();
        this.myServer= serverCreator;
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
        currentGame.getPlayers().add(id_new,NewPlayer);

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
        try {
            myServer.setMessage(msg);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public void started(){
        MessageType m= MessageType.GAME_STARTING;
        Message msg= new Message(id, m);
        try {
            myServer.setMessage(msg);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }


    }

    public void ended(){
        setEnd(1);
        //create a notify message
        MessageType m= MessageType.GAME_ENDING;
        Message msg= new Message(id,m);
        try {
            myServer.setMessage(msg);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    // this method is intended as modify and pick the next player when the current player finished his turn
    public void pickNextPlayer(){
        //CHECK IF THE OLD CURRENT PLAYER HAS COMPLETED SOME COMMON GOALS
        checkPoints();
        int flagPreviusDone=0;
        //check finish
        if(playerTurn().isShelfCompleted()){
            playerTurn().setLastRound(true);
            if(!currentGame.getEndGame()){
                currentGame.setEndGameTrue();
                playerTurn().setPointsEndGame();
            }
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
        Tile[][] tilesShelf;
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

    public void checkPoints() {
        //check first common goal
        if (!currentGame.getCurrentPlayer().getCommonGoalsCompleted()[0]) {
            int partialSum = currentGame.getCommonGoals().get(0).Checker(currentGame.getCurrentPlayer().getShelf().getTilesShelf());
            if (partialSum > 0) {
                currentGame.getCurrentPlayer().setPoints(partialSum);
                currentGame.getCurrentPlayer().setCommonGoalsCompleted(0);
            }
        }
        //check second common goal
        if (!currentGame.getCurrentPlayer().getCommonGoalsCompleted()[1]) {
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
