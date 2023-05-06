package controller;

import Network.RMI.Server_RMI;
import Network.SOCKET.ConcreteSocketServer;
import Network.messages.Message;
import Network.messages.MessageType;
import model.Dashboard;
import model.Game;
import model.Shelf;
import model.*;
import model.cgoal.CommonGoals;


import java.io.BufferedReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.*;

//TODO: scrivere i messaggi per i metodi del controller che modificano qualcosa del models

public class GameController extends Observable {
    //Model
    private Game currentGame;
    private int NumPlayers;
    private int id;
    private Server_RMI myServer;
    private ConcreteSocketServer mySocketServer;

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
        this.mySocketServer=null;
        this.NumPlayers= NumPlayers;
        this.end=0;
        id=0;
        //List of players from the pre-game
        List<Player> playersList = new ArrayList<>();
        Dashboard dashboard = new Dashboard(NumPlayers, new Bag());
        this.currentGame = new Game(0, dashboard, playersList,NumPlayers);
    }

    public GameController(int NumPlayers, ConcreteSocketServer serverCreator) {
        this.NumPlayers= NumPlayers;
        this.end=0;
        this.mySocketServer= serverCreator;
        this.myServer=null; //mutual exclusion
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

        if(currentGame.getCurrentPlayer().isLastRound()){
            int counter=0;
            for(int i=0; i< currentGame.getPlayers().size(); i++){
                if(currentGame.getPlayers().get(i).isLastRound()){
                    counter++;
                }
            }
            if(counter==currentGame.getPlayers().size()){
                for(int i=0; i< currentGame.getPlayers().size(); i++){
                    currentGame.getPlayers().get(i).sumUpPoints();
                }
                ended();
            }
        }
        if(currentGame.getCurrentPlayer().isLastRound()){
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
      if(flagPreviusDone==1){
          currentGame.getCurrentPlayer().setLastRound(true);
      }

    }
    public Player playerTurn(){

        return currentGame.getCurrentPlayer();
    }


    public void pickTiles(List<Integer> xCord, List<Integer> yCord){
        try {
            currentGame.updateDashboard(xCord, yCord);
            pickNextPlayer();
        }catch (Exception e){
            ended();
        }
    }



    public boolean tileAvailablePick(List<Integer> yCord, List<Integer> xCord){

        //checking if the player selected blk tiles
        for(int i=0; i<xCord.size(); i++){
            if(currentGame.getDashboardMatrix()[xCord.get(i)][yCord.get(i)].getColor().equals(COLOR.BLANK)){
                return false;
            }
        }

        //check one tile picked
        if(xCord.size()==1){
            if(currentGame.getDashboardMatrix()[xCord.get(0)+1][yCord.get(0)].getColor().equals(COLOR.BLANK)
                || currentGame.getDashboardMatrix()[xCord.get(0)-1][yCord.get(0)].getColor().equals(COLOR.BLANK)
                || currentGame.getDashboardMatrix()[xCord.get(0)][yCord.get(0)+1].getColor().equals(COLOR.BLANK)
                || currentGame.getDashboardMatrix()[xCord.get(0)][yCord.get(0)-1].getColor().equals(COLOR.BLANK)){
                return true;
            }else{
                return false;
            }

        }else {
            for(int i=0; i<xCord.size(); i++){
                if(!currentGame.getDashboardMatrix()[xCord.get(i)+1][yCord.get(i)].getColor().equals(COLOR.BLANK)
                        && !currentGame.getDashboardMatrix()[xCord.get(i)-1][yCord.get(i)].getColor().equals(COLOR.BLANK)
                        && !currentGame.getDashboardMatrix()[xCord.get(i)][yCord.get(i)+1].getColor().equals(COLOR.BLANK)
                        && !currentGame.getDashboardMatrix()[xCord.get(i)][yCord.get(i)-1].getColor().equals(COLOR.BLANK)){
                    return false;

                }
            }
            //check the line adjacency of the selected Tiles
            if (xCord.size()==2){
                //check  vertical line
                if(xCord.get(0).equals(xCord.get(1))){
                    if(yCord.get(0)== yCord.get(1) +1
                       || yCord.get(0)== yCord.get(1) -1){
                        return true;
                    }
                }
                //check horizontal line
                if(yCord.get(0).equals(yCord.get(1))){
                    if(xCord.get(0)== xCord.get(1) +1
                            || xCord.get(0)== xCord.get(1) -1){
                        return true;
                    }
                }
                return false;

            }else{
                //check  vertical line
                if(xCord.get(0).equals(xCord.get(1)) && xCord.get(0).equals(xCord.get(2))) {
                    //1-2-3 <---< 3-2-1 <---< 3-1-2 <---< 2-1-3 <---< 1-3-2 <---< 2-3-1
                    if((yCord.get(0)== yCord.get(1)-1 && yCord.get(0)== yCord.get(2)-2)
                        || (yCord.get(0)== yCord.get(1)+1 && yCord.get(0)== yCord.get(2)+2)
                        || (yCord.get(0)== yCord.get(1)+2 && yCord.get(0)== yCord.get(2)+1)
                        || (yCord.get(0)== yCord.get(1)+1 && yCord.get(0)== yCord.get(2)-1)
                        || (yCord.get(0)== yCord.get(1)-2 && yCord.get(0)== yCord.get(2)-1)
                        || (yCord.get(0)== yCord.get(1)-1 && yCord.get(0)== yCord.get(2)+1)){
                        return true;
                }
                }
                //check  Horizontal line
                if(yCord.get(0).equals(yCord.get(1)) && yCord.get(0).equals(yCord.get(2))) {
                    //1-2-3 <---< 3-2-1 <---< 3-1-2 <---< 2-1-3 <---< 1-3-2 <---< 2-3-1
                    if((xCord.get(0)== xCord.get(1)-1 && xCord.get(0)== xCord.get(2)-2)
                            || (xCord.get(0)== xCord.get(1)+1 && xCord.get(0)== xCord.get(2)+2)
                            || (xCord.get(0)== xCord.get(1)+2 && xCord.get(0)== xCord.get(2)+1)
                            || (xCord.get(0)== xCord.get(1)+1 && xCord.get(0)== xCord.get(2)-1)
                            || (xCord.get(0)== xCord.get(1)-2 && xCord.get(0)== xCord.get(2)-1)
                            || (xCord.get(0)== xCord.get(1)-1 && xCord.get(0)== xCord.get(2)+1)){
                        return true;
                    }
                }

            }
        return false;
        }

    }

    public void insertTiles( List<Integer> xCoord, List<Integer> yCoord, int column){
        Shelf shelfToModify= currentGame.getCurrentPlayer().getShelf();
        Tile[] tilesPicked= new Tile[xCoord.size()];
        for(int i=0; i< xCoord.size(); i++){
            tilesPicked[i]= currentGame.getDashboard().getTiles()[yCoord.get(i)][xCoord.get(i)];
        }
        currentGame.addTiles(shelfToModify, tilesPicked,column);

        if(playerTurn().isShelfCompleted()){
            playerTurn().setLastRound(true);
        }


    } //rita
    public boolean columnAvailable(int numTiles, Shelf myShelf, int column) {
        boolean flag = false;
       Tile[][] tilesShelf= myShelf.getTilesShelf();
        if (numTiles == 3) {
            if (tilesShelf[2][column].getColor().equals(COLOR.BLANK)) {
                flag = true;
            }
        }
        if (numTiles == 2) {
            if (tilesShelf[1][column].getColor().equals(COLOR.BLANK)) {
                flag = true;
            }
        }
        if (numTiles== 1) {
            if (tilesShelf[0][column].getColor().equals(COLOR.BLANK)) {
                flag = true;
            }
        }
        return flag;
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
                currentGame.getCurrentPlayer().setCommonGoalsCompleted(1);
            }
        }

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
