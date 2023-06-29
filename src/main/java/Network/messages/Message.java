package Network.messages;

import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * The Message class represents a serializable message used for socket communication.
 * It encapsulates information to be sent between client and server via sockets.
 */
public  class Message implements Serializable {
    @Serial
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
    private List<List<Integer>> myPossiblePick;
    private int possibleCol;

    private int points;
    private int pgPoints;

    /**
     * Returns the list of possible picks for the player.
     *
     * @return the list of possible picks for the player
     */
    public List<List<Integer>> getMyPossiblePick() {
        return myPossiblePick;
    }


    /**
     * Returns the possible column value.
     *
     * @return the possible column value
     */
    public int getPossibleCol() {
        return possibleCol;
    }


    /**
     * Constructs a new Message object with the given name and message.
     *
     * @param name the name associated with the message
     * @param msg  the message content
     */
    public Message(String name, SocketMessages msg) {
        this.name = name;
        this.msg = msg;
    }

    //Message for new game (np=numberOfPlayers)
    //Message for Lobby_created (np=indexOfController)
    /**
     * Constructs a new Message object with the given name, message, and index.
     *
     * @param name  the name associated with the message
     * @param msg   the message content
     * @param index the index value
     */
    public Message(String name, SocketMessages msg, int index) {
        this.name = name;
        this.msg = msg;
        this.np = index;
    }

    /**
     * Creates a new instance of the Message class with the specified parameters.
     *
     * @param name        the name associated with the message
     * @param msg         the socket message
     * @param dashboard   the two-dimensional array representing the dashboard
     * @param commonGoals the list of common goals
     * @param shelf       the two-dimensional array representing the shelf
     * @param pg          the personal goal
     * @param points      the total points
     * @param pgPoints    the points associated with the personal goal
     */
    public Message(String name, SocketMessages msg, Tile[][] dashboard, List<CommonGoals> commonGoals, Tile[][] shelf, PersonalGoal pg, int points, int pgPoints) {
        this.name = name;
        this.msg = msg;
        this.dashboard = dashboard;
        this.commonGoals = commonGoals;
        this.shelf = shelf;
        this.pg = pg;
        this.points = points;
        this.pgPoints = pgPoints;
    }

    /**
     * Creates a new instance of the Message class with the specified parameters.
     *
     * @param name           the name associated with the message
     * @param msg            the socket message
     * @param lobbyid        the ID of the lobby
     * @param myPossiblePick the list of possible picks
     * @param possibleCol    the possible column
     */
    public Message(String name, SocketMessages msg, int lobbyid, List<List<Integer>> myPossiblePick, int possibleCol) {
        this.name = name;
        this.msg = msg;
        this.np = lobbyid;
        this.myPossiblePick = myPossiblePick;
        this.possibleCol = possibleCol;
    }

    /**
     * Returns the dashboard.
     *
     * @return the two-dimensional array representing the dashboard
     */
    public Tile[][] getDashboard() {
        return this.dashboard;
    }

    /**
     * Returns the shelf.
     *
     * @return the two-dimensional array representing the shelf
     */
    public Tile[][] getShelf() {
        return this.shelf;
    }

    /**
     * Returns the list of common goals.
     *
     * @return the list of common goals
     */
    public List<CommonGoals> getCommonGoals() {
        return this.commonGoals;
    }

    /**
     * Returns the personal goal.
     *
     * @return the personal goal
     */
    public PersonalGoal getPg() {
        return this.pg;
    }

    /**
     * Returns the total points.
     *
     * @return the total points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the total points.
     *
     * @param points the new value for the total points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the points associated with the personal goal.
     *
     * @return the points associated with the personal goal
     */
    public int getPgPoints() {
        return pgPoints;
    }


    /**
     * Retrieves the value of np from the message.
     *
     * @return the value of np
     */
    public int getNp() {
        return np;
    }

    /**
     * Retrieves the name from the message.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the SocketMessages object associated with the message.
     *
     * @return the SocketMessages object
     */
    public SocketMessages getMsg() {
        return msg;
    }

    /**
     * Constructs a new Message object with the given ID and message type.
     *
     * @param id          the ID of the message
     * @param messageType the message type
     */
    public Message(int id, MessageType messageType) {
        this.id = id;
        this.messageType = messageType;
    }

    /**
     * Retrieves the ID of the message.
     *
     * @return the ID
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves the message type.
     *
     * @return the message type
     */
    public MessageType getMessageType() {
        return messageType;
    }
}
