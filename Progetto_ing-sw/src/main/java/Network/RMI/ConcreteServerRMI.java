package Network.RMI;
import java.util.*;
import controller.*;

import controller.GameController;

import java.util.List;

public class ConcreteServerRMI implements Server_RMI{
    private List<GameController> Lobby;


    public int  createLobby(int numPlayers){
        GameController controller=  new GameController(numPlayers);
        Lobby.add(controller);
        int indexLobby= Lobby.indexOf(controller);
        return indexLobby;
    }

    public int  joinLobby(){

        for (int i =0; i< Lobby.size(); i++){
            if (!Lobby.get(i).isFull()){
                return i;
            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller=  new GameController(2);
        Lobby.add(controller);
        return Lobby.indexOf(controller);
    }







}
