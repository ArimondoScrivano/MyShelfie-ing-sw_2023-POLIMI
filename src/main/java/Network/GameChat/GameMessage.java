package Network.GameChat;

import Network.RMI.Server_RMI;

import java.io.Serializable;

public class GameMessage implements Serializable {
    private String myGameMessage;
    private String Name_Creator;
    private String receiver;

    public String  getReceiver() {
        return receiver;
    }

    public String getMyGameMessage() {
        return myGameMessage;
    }

    public String getName_Creator() {
        return Name_Creator;
    }
    public GameMessage(String myGameMessage, String Name_Creator,String receiver) {
        this.myGameMessage = myGameMessage;
        this.Name_Creator= Name_Creator;
        this.receiver= receiver;
    }

}
