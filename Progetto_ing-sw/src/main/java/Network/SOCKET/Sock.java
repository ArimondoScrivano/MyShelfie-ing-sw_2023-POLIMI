package Network.SOCKET;

import controller.ClientControllerV2;
import view.Cli;
import view.TextualUI;

import java.io.IOException;

public class Sock {
    public static void main(String[] args) {
        Cli cli = new Cli();
        TextualUI view = new TextualUI();
        view.init();
        int defaultPort = 16001;
        System.out.println("Client started");
        int conn=cli.askConnection();
        if(conn==2) {
            ClientControllerV2 clientControllerV2 = new ClientControllerV2(view, "localhost", defaultPort);
            try{
                clientControllerV2.gameFlow();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
