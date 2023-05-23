package controller;

import Network.RMI.Server_RMI;
import Network.Server;
import Network.messages.Message;
import Network.messages.MessageType;
import model.Dashboard;
import model.Game;
import model.Shelf;
import model.*;
import model.cgoal.CommonGoals;

import java.util.*;

//TODO: scrivere i messaggi per i metodi del controller che modificano qualcosa del models

public class GameController extends Observable {
    //Model
    private Game currentGame;
    private int NumPlayers;
    private int id;


    //final attribute to the server implementation
    private Server server;

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

    public GameController(int NumPlayers, Server serverCreator, String creatorLobby) {
        this.server= serverCreator;
        this.NumPlayers= NumPlayers;
        this.end=0;
        id=0;
        //List of players from the pre-game
        List<Player> playersList = new ArrayList<>();
        playersList.add(new Player(0, creatorLobby));
        Dashboard dashboard = new Dashboard(NumPlayers, new Bag());
        this.currentGame = new Game(0, dashboard, playersList,NumPlayers);
    }

    public GameController(int NumPlayers, Server serverCreator) {
        this.server= serverCreator;
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
        System.out.println("il numero dei giocatori è: " + currentGame.getPlayers().size());
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
            server.setMessage(msg);

    }


    public void started(){
        int flagStartincoming=0;
        //Setting the first player
        currentGame.setCurrentPlayer(currentGame.getPlayers().get(0));
        if(NumPlayers==2){
            if(!currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(1).getName())){
              flagStartincoming=1;
            }
        }

        if(NumPlayers==3){
            if(!currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(1).getName())
                && !currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(2).getName())
                && !currentGame.getPlayers().get(1).getName().equals(currentGame.getPlayers().get(2).getName())){
                    flagStartincoming=1;
            }
        }
        if(NumPlayers==4){
            if(!currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(1).getName())
               && !currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(2).getName())
               && !currentGame.getPlayers().get(0).getName().equals(currentGame.getPlayers().get(3).getName())
               && !currentGame.getPlayers().get(1).getName().equals(currentGame.getPlayers().get(2).getName())
               && !currentGame.getPlayers().get(1).getName().equals(currentGame.getPlayers().get(3).getName())
               && !currentGame.getPlayers().get(2).getName().equals(currentGame.getPlayers().get(3).getName())){
                flagStartincoming=1;

            }
        }

        if(flagStartincoming==1) {
            MessageType m = MessageType.GAME_STARTING;
            Message msg = new Message(id, m);
          server.setMessage(msg);
        }

    }

    public void ended() {
        setEnd(1);
        //create a notify message
        MessageType m = MessageType.GAME_ENDING;
        Message msg = new Message(id, m);
        this.end = 1;
        server.setMessage(msg);
    }

    public void changeName(int id, String name){
        currentGame.getPlayers().get(id).setName(name);
        if(currentGame.getPlayers().size()==NumPlayers) {
            started();
        }
    }
  public int finderPlayer(String name){
        List<Player> playerList= getPlayersList();
        for(int i=0; i<playerList.size(); i++){
            if(playerList.get(i).getName().equals(name)){
                return i;
            }
        }
        return 0;
  }

    // this method is intended as modify and pick the next player when the current player finished his turn
    public void pickNextPlayer(){
        //CHECK IF THE OLD CURRENT PLAYER HAS COMPLETED SOME COMMON GOALS
        checkPoints();
        //check finish
        if(playerTurn().isShelfCompleted()){
            playerTurn().setLastRound(true);
            int pos= finderPlayer(playerTurn().getName());

            //the players before me can't play
            for(int k=0; k<pos; k++){
                currentGame.getPlayers().get(k).setLastRound(true);
            }
            if(!currentGame.getEndGame()){
                currentGame.setEndGameTrue();
                playerTurn().setPointsEndGame();
            }

            // I completed the game and I'm the last in the List
            if(pos==currentGame.getPlayers().size()-1 ){
                for(int p=0; p<currentGame.getPlayers().size(); p++){
                    currentGame.getPlayers().get(p).sumUpPoints();
                }
                ended();
                return;
            }

        }
        int pos= finderPlayer(playerTurn().getName());
        int previous= pos -1;
        if (previous != -1) {
            if(currentGame.getPlayers().get(previous).isLastRound()){
                playerTurn().setLastRound(true);
                if(pos==currentGame.getPlayers().size() -1){
                    for(int p=0; p<currentGame.getPlayers().size(); p++){
                        currentGame.getPlayers().get(p).sumUpPoints();
                    }
                    ended();
                    return;
                }
            }
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

        somethingChanged();

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


    }
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
    }

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
