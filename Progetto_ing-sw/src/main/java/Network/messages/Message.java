package Network.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {
    private static final long serialVersionUID = 382104422531955291L;

    //Player name
    private final String name;
    //MessageType related to the event
    private final MessageType messageType;


    Message(String name, MessageType messageType) {
        this.name = name;
        this.messageType = messageType;
    }

    public String getName() {
        return name;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
