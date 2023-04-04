package controller;

import model.Game;
import model.*;

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

    public void chooseColumnShelf(){}
}
