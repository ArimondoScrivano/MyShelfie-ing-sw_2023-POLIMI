package controller;

import model.Game;
import model.Shelf;
import model.*;

import java.io.IOException;

//TODO:controller
public class GameController {
    //Model
    Game currentGame;
    //View
    //UI userInterface;
    //Chat currentChat;

    //Constructor
    public GameController(Game currentGame /*, UI userInterface*/) {
        this.currentGame = currentGame;
        //this.userInterface=userInterface;
    }

    @Deprecated
    public void createPlayer(){
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
}
