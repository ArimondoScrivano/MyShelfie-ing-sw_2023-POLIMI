package Network.messages;

import java.io.Serializable;

public  class Message implements Serializable {
    private static final long serialVersionUID = 382104422531955291L;

    //LOBBY ID
    private int id;
    //MessageType related to the event
    private  MessageType messageType;




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
