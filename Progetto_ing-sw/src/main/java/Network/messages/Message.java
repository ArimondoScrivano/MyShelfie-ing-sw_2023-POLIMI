package Network.messages;

import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.Serializable;
import java.util.List;

public  class Message implements Serializable {
    private static final long serialVersionUID = 382104422531955291L;

    //LOBBY ID
    private int id;
    //MessageType related to the event
    private MessageType messageType;
    private SocketMessages msg;
    private String name;
    private int np;

    private Tile[][] dashboard;
    private List<CommonGoals> commonGoals;
    private Tile[][] shelf;
    private PersonalGoal pg;
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

    public Message(String name, SocketMessages msg, Tile[][] dashboard, List<CommonGoals> commonGoals, Tile[][] shelf, PersonalGoal pg){
        this.name=name;
        this.msg=msg;
        this.dashboard=dashboard;
        this.commonGoals=commonGoals;
        this.shelf=shelf;
        this.pg=pg;
    }

    public Tile[][] getDashboard(){
        return this.dashboard;
    }
    public Tile[][] getShelf(){
        return this.shelf;
    }
    public List<CommonGoals> getCommonGoals(){
        return this.commonGoals;
    }public PersonalGoal getPg(){
        return this.pg;
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
