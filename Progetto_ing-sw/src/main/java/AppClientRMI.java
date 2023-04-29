import Network.RMI.Client_RMI;
import controller.GameController;
import view.Cli;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AppClientRMI {
    public static void main(String[] args) throws NotBoundException, RemoteException {
        Cli cli = new Cli();
        String playerName;

        //Asking the nickname
        playerName=cli.askNickname();
        //Creating the client
        Client_RMI client = new Client_RMI(playerName);
        if(cli.askNewGame()){
            //New game to create
            GameController clientController = new GameController(cli.askNumberOfPlayers(), client.getServer());
        }
    }
}
