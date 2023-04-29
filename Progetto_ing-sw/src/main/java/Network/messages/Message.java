package Network.messages;

import java.io.Serializable;

public  class Message implements Serializable {
    private static final long serialVersionUID = 382104422531955291L;

    //LOBBY ID
    private int name;
    //MessageType related to the event
    private  MessageType messageType;




    public Message(int name, MessageType messageType) {
        this.name = name;
        this.messageType = messageType;
    }

    public int getName() {
        return name;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
