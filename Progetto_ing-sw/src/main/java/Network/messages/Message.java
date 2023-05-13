package Network.messages;

import java.io.Serializable;

public  class Message implements Serializable {
    private static final long serialVersionUID = 382104422531955291L;

    //LOBBY ID
    private int id;
    //MessageType related to the event
    private MessageType messageType;
    private SocketMessages msg;
    private String name;
    private int np;

    public Message(String name, SocketMessages msg){
        this.name = name;
        this.msg=msg;
    }

    //Message for new game (np=numberOfPlayers)
    //Message for Lobby_created (np=indexOfController)
    public Message(String name, SocketMessages msg, int index){
        this.name = name;
        this.msg=msg;
        this.np=index;
    }

    public int getNp(){
        return np;
    }

    public String getName() {
        return name;
    }
    public SocketMessages getMsg() {
        return msg;
    }

    public Message(int id, MessageType messageType) {
        this.id = id;
        this.messageType = messageType;
    }

    public int getId() {
        return id;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
