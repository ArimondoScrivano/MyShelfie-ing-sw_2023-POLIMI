package controller;

import model.Game;
import model.Shelf;
import model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO:controller
public class GameController {
    //Model
    Game currentGame;
    //View
    //UI userInterface;
    //Chat currentChat;

    //Constructor of the game
    public GameController() {
        //List of players from the pre-game
        List<Player> playersList = new ArrayList<>();
        Dashboard dashboard = new Dashboard(playersList.size(), new Bag());
        this.currentGame = new Game(0, dashboard, playersList);
    }

    @Deprecated
    public void createPlayer(){
        //To implement from the pre-match?
        /*if(currentGame.players.size()< Game.MAX_PLAYERS){
            Add player to the game
        }*/
    }

    public Player playerTurn(){
        return currentGame.getCurrentPlayer();
    }

    public void pickTiles(){

    }

    public boolean tileAvailablePick(){
        return true;
    }

    public void chooseColumnShelf(int column, Tile[] tiles, Shelf myShelf){
        myShelf.addTiles(tiles, column);
    } //rita
    public boolean columnAvailable(Tile[] chosenTiles, Shelf myShelf)throws IOException {
        //il metodo riceve come parametro la shelf del giocatore che sta prendendo tessere e il numero di tessere scelte
        boolean available=true;
        int chosenColumn=0;
        try{
            chosenColumn=System.in.read();
            chosenColumn--; //l'utente umano digita "1" riferendosi alla colonna con numerazione 0
        }catch (IOException e){
            System.out.println("I had problems reading your choice");
        }
        Tile[][] tilesShelf= new Tile[6][5];
        tilesShelf= myShelf.getTilesShelf();
        int i=5;
        int counter=0;
        while(i>=0){
            if(tilesShelf[i][chosenColumn].getColor().equals(COLOR.BLANK)) counter++;
        }
        if(chosenTiles.length>counter){
            available=false;
        }else {
            chooseColumnShelf(chosenColumn, chosenTiles, myShelf);
        }
        return available;
    } //rita
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
}
