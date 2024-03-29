package Network;

import Network.GameChat.GameMessage;
import Network.RMI.ClientCallback;
import Network.RMI.Server_RMI;
import Network.SOCKET.SocketClientHandler;
import Network.messages.Message;
import Network.messages.MessageType;
import Network.messages.SocketMessages;
import controller.GameController;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * The Server class represents a server for handling client connections and implementing remote method invocation (RMI).
 * It extends the UnicastRemoteObject class and implements the Runnable and Server_RMI interfaces.
 */
public class Server extends UnicastRemoteObject implements Runnable,Server_RMI {
    private final int defaultPort;
    private ServerSocket serverSocket;
    private final Object lock;

    // How it works:
    //Integer: lobby index, the internal map has the association player's name and socket
    private Map<Integer, Map<String, SocketClientHandler> > clientHandlerMap;
    private List<GameController> Lobby;
    private List<Message> LobbyMessage;
    private Map<Integer, List<GameMessage>> lobbychat;

    private Map<Integer, List<ClientCallback>> ConnectionClientMap;


    /**
     * Constructs a new instance of the Server class.
     *
     * @param defaultPort The default port number to be used for the server.
     * @throws RemoteException If there is an error in the remote method invocation.
     */
    public Server(int defaultPort) throws RemoteException {
        super();
        this.defaultPort=defaultPort;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock=new Object();
        this.Lobby=new ArrayList<>();
        this.LobbyMessage=new ArrayList<>();
        this.lobbychat= new HashMap<>();
        this.ConnectionClientMap= Collections.synchronizedMap(new HashMap<>());
    }

    /**
     * Starts the server and listens for incoming client connections.
     * The server socket is created using the default port specified during server initialization.
     * For each accepted client connection, a new SocketClientHandler is created and started in a separate thread.
     * The method continues to listen for incoming connections until the current thread is interrupted.
     * If an IOException occurs while accepting a client connection, the exception is printed.
     */
    @Override
    public void run() {

        try{
            serverSocket = new ServerSocket(defaultPort);
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        while(!Thread.currentThread().isInterrupted()){
            try{
                Socket client = serverSocket.accept();
                //Set the timeout to infinity
                client.setSoTimeout(0);

                SocketClientHandler clientHandler = new SocketClientHandler(this, client);
                System.out.println("Client connected at address: "+client.getInetAddress());
                Thread thread = new Thread(clientHandler, "handler"+client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Creates a lobby with the specified number of players and creator's name.
     * If there are any previous inactive lobbies, they are replaced by the new lobby.
     * The lobby is added to the Lobby list, and a corresponding lobby-created message is added to the LobbyMessage list.
     * The GameController is initialized with the number of players, server reference, and creator's name.
     * The client handler for the lobby creator is added to the clientHandlerMap.
     *
     * @param numPlayers      The number of players for the lobby.
     * @param creatorLobby    The name of the lobby creator.
     * @param message         The original message that triggered the lobby creation.
     * @param clientHandler   The client handler associated with the lobby creator.
     * @return The index of the created lobby in the Lobby list.
     */
    public int createLobby(int numPlayers, String creatorLobby, Message message, SocketClientHandler clientHandler){
        System.out.println("Creating a lobby");
        GameController controller = new GameController(numPlayers, this, creatorLobby);
        int foundPreviousMatch=0;
        for(int i=0; i<Lobby.size() && foundPreviousMatch==0; i++){
            if(LobbyMessage.get(i)!=null){
                if(LobbyMessage.get(i).getMessageType().equals(MessageType.GAME_ENDING) ||LobbyMessage.get(i).getMessageType().equals(MessageType.DISCONNECT)){
                    Lobby.set(i, null);
                    foundPreviousMatch=1;

                }
            }
        }

        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED));
        controller.setId(Lobby.indexOf(controller));
        this.clientHandlerMap.put(Lobby.indexOf(controller), new HashMap<>());
        addPlayer(message.getName(), clientHandler, Lobby.indexOf(controller), 1);
        return Lobby.indexOf(controller);
    }


    /**
     * Joins a lobby with the specified player name.
     * If there is an available lobby that is not full, the player is added to that lobby.
     * If no available lobby is found, a new 2-player lobby is created.
     * The player is added to the lobby and a corresponding lobby-created message is added to the LobbyMessage list.
     * The GameController is initialized with the server reference.
     * The client handler for the player is added to the clientHandlerMap.
     *
     * @param message       The message containing the player name.
     * @param clientHandler The client handler associated with the player.
     * @return The index of the joined lobby in the Lobby list.
     */
    public int joinLobby(Message message, SocketClientHandler clientHandler){

        for (int i = 0; i < Lobby.size(); i++) {
            if (Lobby.get(i)!=null && !Lobby.get(i).isFull()) {
                //these lines are for the case when The first Socket client is NOT the first of the match
                if(clientHandlerMap.get(i)==null){
                    this.clientHandlerMap.put(i, new HashMap<>());
                }
                addPlayer(message.getName(), clientHandler, i, 0);
                return i; //index of lobby not full

            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller = new GameController(2, this);
        int foundPreviousMatch=0;
        for(int i=0; i<Lobby.size() && foundPreviousMatch==0; i++){
            if(LobbyMessage.get(i)!=null){
                if(LobbyMessage.get(i).getMessageType().equals(MessageType.GAME_ENDING) ||LobbyMessage.get(i).getMessageType().equals(MessageType.DISCONNECT)){
                    Lobby.set(i, null);
                    foundPreviousMatch=1;

                }
            }
        }

        Lobby.add(controller);
        this.clientHandlerMap.put(Lobby.indexOf(controller), new HashMap<>());
        addPlayer(message.getName(), clientHandler, Lobby.indexOf(controller), 0);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED ));
        controller.setId(Lobby.indexOf(controller));
        return Lobby.indexOf(controller);
    }

    /**
     * Adds a player to the specified lobby with the given name and client handler.
     *
     * @param name          The name of the player to be added.
     * @param clientHandler The client handler associated with the player.
     * @param index         The index of the lobby where the player will be added.
     * @param mult          Indicates whether the player needs to be added to the match (0) or not (1).
     */
    public void addPlayer(String name, SocketClientHandler clientHandler, int index, int mult){
        // mult= 0 ----->>>>> The Player needs to be Added to the Match
        // mult= 1 ----->>>>> The Player DOES NOT need to be Added to the Match


        System.out.println("Adding player "+ name);
        //Checking no duplicates of the name
        boolean flag=clientHandlerMap.get(index).containsKey(name);
        int counter=0;
        for(int count=0; count<Lobby.get(index).getPlayersList().size(); count++){
            if(Lobby.get(index).getPlayersList().get(count).getName().equals(name)){
                counter++;
            }
        }


        if(flag || (counter== 1 && mult==0)){
            clientHandler.sendMessage(new Message(name, SocketMessages.NAME_FAILED, index));

        }else{
            clientHandlerMap.get(index).put(name, clientHandler);
            clientHandlerMap.get(index).get(name).sendMessage(new Message(name, SocketMessages.LOGIN_REPLY, index));
            //Bound to JoinLobby

            if(mult==0){
                int IndexPlayer=Lobby.get(index).getPlayersFilled();
                Lobby.get(index).createPlayer(IndexPlayer, name);
            }
        }

    }

    //Messages for game flow management
    /**
     * Handles the received message and performs corresponding actions based on the message type.
     *
     * @param message The message received.
     * @throws RemoteException If a remote communication error occurs.
     */
    public void onMessageReceived(Message message) throws RemoteException{
        System.out.println("Message received");
        switch(message.getMsg()){
            case IS_GAME_STARTING -> {
             //  checkGameStarting(message);
            }
            case IS_IT_MY_TURN -> {
                System.out.println("User " +message.getName() +" ask if it's his turn");
                if(nameCurrentPlayer(message.getNp()).equals(message.getName())){
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.MY_TURN,
                            Lobby.get(message.getNp()).getDashboardTiles(),
                            Lobby.get(message.getNp()).getCommonGoals(),
                            Lobby.get(message.getNp()).playerTurn().getShelfMatrix(),
                            Lobby.get(message.getNp()).playerTurn().getPersonalGoal(),
                            Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPoints(),
                            Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPGpoints()));
                }else{
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.WAITING_FOR_YOUR_TURN));
                }
            }
            case ARE_PARAMETERS_OK -> {
                List<List<Integer>> listToTest= message.getMyPossiblePick();
                int index= message.getNp();
                int numberTiles= listToTest.get(1).size();
                int possibleCol= message.getPossibleCol();
                List<Integer> xCoord= listToTest.get(0);
                List<Integer> yCoord= listToTest.get(1);
                //control parameters, if ok-> PARAMETERS_OK, IF ko-> PARAMETERS_KO
                if(pickableTiles( index, xCoord,  yCoord) &&  columnAvailable(index,  numberTiles, Lobby.get(message.getNp()).playerTurn().getShelf(), possibleCol )){
                    insertTiles(index,xCoord,yCoord,possibleCol);
                    finalPick(index, xCoord,yCoord);
                    if(!clientHandlerMap.get(message.getNp()).isEmpty()) {
                        clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server", SocketMessages.PARAMETERS_OK,
                                Lobby.get(message.getNp()).getDashboardTiles(),
                                Lobby.get(message.getNp()).getCommonGoals(),
                                Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getShelfMatrix(),
                                Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPersonalGoal(),
                                Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPoints(),
                                Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPGpoints()));
                    }
                }else{
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.PARAMETERS_KO));
                }
            }

            case HAVE_I_WON -> {
                int index= message.getNp();
                String namePlayer= message.getName();
                if(checkSocketWinner(index, namePlayer)){
                    clientHandlerMap.get(message.getNp()).get(namePlayer).sendMessage(new Message("server", SocketMessages.WINNER));
                }else{
                    clientHandlerMap.get(message.getNp()).get(namePlayer).sendMessage(new Message("server", SocketMessages.LOSER));
                }
            }
            //ASYNCHRONOUS REQUEST MANAGEMENT
            case DASHBOARD_REQ -> {
                clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.DASHBOARD_RES,
                        Lobby.get(message.getNp()).getDashboardTiles(),null,null,null,0,0));
            }
            case SHELF_REQ -> {
                clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.SHELF_RES,
                       null,null,Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getShelfMatrix(),null,0,0));
            }
            case COMMONGOAL_REQ -> {
                clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.COMMONGOAL_RES,
                        null,Lobby.get(message.getNp()).getCommonGoals(),null,null,0,0));
            }
            case PERSONAL_GOAL_REQ -> {
                clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.PERSONAL_GOAL_RES,
                        null,null,null,Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPersonalGoal(),0,0));
            }
            case REFRESH_REQ -> {
                clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.REFRESH_RES,
                        Lobby.get(message.getNp()).getDashboardTiles(),
                        Lobby.get(message.getNp()).getCommonGoals(),
                        Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getShelfMatrix(),
                        Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPersonalGoal(),
                        Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPoints(),
                        Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPGpoints()));
            }
        }
    }
    /**
     * Retrieves the name of the current player in the specified lobby.
     *
     * @param index The index of the lobby.
     * @return The name of the current player.
     */
    public String nameCurrentPlayer( int index) {
        return Lobby.get(index).playerTurn().getName();
    }


    /**
     * Handles the disconnection of a client from the specified lobby.
     *
     * @param clientHandler The client handler associated with the disconnected client.
     * @param index The index of the lobby.
     */
    public void onDisconnect(SocketClientHandler clientHandler, int index){
        synchronized (lock){
            for(String chiave : clientHandlerMap.get(index).keySet()){
                clientHandlerMap.get(index).get(chiave).sendMessage(new Message("server", SocketMessages.DISCONNECT));
            }
            System.out.println("Server: On disconnect");
            LobbyMessage.set(index, new Message(index, MessageType.DISCONNECT));
        }
    }

    /**
     * Checks if the game in the specified lobby is starting or waiting for more players.
     * Sends corresponding messages to the clients.
     *
     * @param message The message indicating the lobby index.
     */
    public void checkGameStarting(Message message){
        if (Lobby.get(message.getId()).isFull()){

            for( String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                //Generalized for on the map
                clientHandlerMap.get(message.getId()).get(chiave).sendMessage(new Message("server", SocketMessages.GAME_STARTING));
            }
        }else{
            for( String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                //Generalized for on the map
                clientHandlerMap.get(message.getId()).get(chiave).sendMessage(new Message("server", SocketMessages.WAITING_FOR_OTHER_PLAYERS));
            }
        }
    }

    public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord) throws RemoteException  {
        return Lobby.get(index).tileAvailablePick(xCoord, yCoord);
    }

    public boolean columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol) throws RemoteException{
        return Lobby.get(index).columnAvailable(numTiles, myShelf, selectedCol);


    }

    public void finalPick(int index, List<Integer> xCord, List<Integer> yCord)throws RemoteException{
        Lobby.get(index).pickTiles(xCord,yCord);
    }



    //This method adds the tiles in the shelf
    public void insertTiles ( int LobbyReference, List<Integer> xCoord, List<Integer>  yCoord, int column)throws RemoteException {
        Lobby.get(LobbyReference).insertTiles(xCoord,yCoord,column);
    }

    /**
     * Checks if the specified player in the lobby is the winner.
     *
     * @param index The index of the lobby.
     * @param name The name of the player.
     * @return {@code true} if the player is the winner, {@code false} otherwise.
     */
    public boolean checkSocketWinner(int index, String name) {
        return Lobby.get(index).checkWinner().getName().equals(name);
    }


    /**
     * Checks the connection status of clients in the specified lobby and handles disconnections.
     *
     * @param message The message containing the lobby ID.
     */
    public void checkDisconnection(Message message) {
        int numLobby = message.getId();
        boolean stop=false;
        while (!Thread.currentThread().isInterrupted() & !stop) {
            for (int j = 0; j < ConnectionClientMap.get(numLobby).size() &!stop; j++) {
                try {
                    ConnectionClientMap.get(numLobby).get(j).CheckConnectionClient();
                } catch (RemoteException e) {
                    setMessage(new Message(numLobby, MessageType.DISCONNECT));
                    Thread.currentThread().interrupt();
                    stop=true;
                    System.out.println("An RMI client disconnect...");
                }
            }

        }
    }

    /**
     * Sets the specified message in the LobbyMessage list and performs corresponding actions based on the message type.
     *
     * @param message The message to be set.
     */
    public void setMessage(Message message) {
        LobbyMessage.set(message.getId(), message);
        //Control print for debugging purpose
        //System.out.println("Setting message "+ message.getMessageType());
            if (message.getMessageType().equals(MessageType.GAME_STARTING)) {
                System.out.println("The game "+message.getId()+" is starting");
                if(clientHandlerMap.get(message.getId()) != null) {
                    checkGameStarting(message);
                }

                if(ConnectionClientMap.get(message.getId())!=null) {
                    Thread clientDisconnectionHandler = new Thread(() -> checkDisconnection(message));
                    clientDisconnectionHandler.start();
                }
            }

            if (clientHandlerMap.get(message.getId()) != null) {
                if (message.getMessageType().equals(MessageType.SOMETHINGCHANGED)) {

                    for (String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                        //Generalized for on the map
                        clientHandlerMap.get(message.getId()).get(chiave).sendMessage(new Message("server", SocketMessages.CHECK_YOUR_TURN));
                    }

                } else if (message.getMessageType().equals(MessageType.GAME_ENDING)) {
                    for (String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                        //Generalized for on the map
                        if (checkSocketWinner(message.getId(), chiave)) {
                            clientHandlerMap.get(message.getId()).get(chiave).sendMessage(new Message("server", SocketMessages.WINNER));
                        } else {
                            clientHandlerMap.get(message.getId()).get(chiave).sendMessage(new Message("server", SocketMessages.LOSER));
                        }

                    }
                }else if(message.getMessageType().equals(MessageType.DISCONNECT)) {
                    System.out.println("Sending disconnection messages to " +message.getId()+" lobby");
                    if (clientHandlerMap.get(message.getId()) != null) {
                        for (String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                            System.out.println("Sending message to " + chiave);
                            clientHandlerMap.get(message.getId()).get(chiave).sendMessage(new Message("server", SocketMessages.DISCONNECT));
                        }
                        clientHandlerMap.get(message.getId()).clear();
                        ConnectionClientMap.get(message.getId()).clear();
                        LobbyMessage.set(message.getId(),new Message(message.getId(), MessageType.LOBBYCLOSED));

                    }
                }
            }
        }
    @Override
    public int createLobby(int numPlayers, String creatorLobby, ClientCallback client) throws RemoteException {
        GameController controller = new GameController(numPlayers, this, creatorLobby);
        //If another match ended
        int foundPreviousMatch=0;
        for(int i=0; i<Lobby.size() && foundPreviousMatch==0; i++){
            if(LobbyMessage.get(i)!=null){
                if(LobbyMessage.get(i).getMessageType().equals(MessageType.GAME_ENDING) ||LobbyMessage.get(i).getMessageType().equals(MessageType.DISCONNECT)){
                    Lobby.set(i, null);
                    foundPreviousMatch=1;

                }
            }
        }

        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED ));
        controller.setId(Lobby.indexOf(controller));
        lobbychat.put(Lobby.indexOf(controller), new ArrayList<>());
        this.ConnectionClientMap.putIfAbsent(controller.getId(), new ArrayList<>());
        this.ConnectionClientMap.get(controller.getId()).add(client);
        return Lobby.indexOf(controller);
    }

    @Override
    public int joinLobby() throws RemoteException {

        for (int i = 0; i < Lobby.size(); i++) {
            if (Lobby.get(i)!=null && !Lobby.get(i).isFull()) {
                return i;
            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller = new GameController(2, this);
        int foundPreviousMatch=0;
        for(int i=0; i<Lobby.size() && foundPreviousMatch==0; i++){
            if(LobbyMessage.get(i)!=null){
                if(LobbyMessage.get(i).getMessageType().equals(MessageType.GAME_ENDING) ||LobbyMessage.get(i).getMessageType().equals(MessageType.DISCONNECT)){
                    Lobby.set(i, null);
                    foundPreviousMatch=1;

                }
            }
        }
        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED ));
        controller.setId(Lobby.indexOf(controller));
        return Lobby.indexOf(controller);
    }

    @Override
    public int addPlayer(int index, String name, ClientCallback client) throws RemoteException {
        int IndexPlayer=Lobby.get(index).getPlayersFilled();
        if(ConnectionClientMap.get(index)==null){
            this.ConnectionClientMap.putIfAbsent(index, new ArrayList<>());
        }
        this.ConnectionClientMap.get(index).add(client);
        Lobby.get(index).createPlayer(IndexPlayer, name);
        return IndexPlayer;
    }

    public boolean nameAleradyTaken(int index, String name, int id) throws RemoteException {
        for(int i=0; i< Lobby.get(index).getPlayersList().size(); i++){
            if(Lobby.get(index).getPlayersList().get(i).getName().equals(name) && i!=id){
                return true;
            }
        }
        return false;
    }

    public void changeName(int index, int id, String name)throws RemoteException{
        Lobby.get(index).changeName(id, name);
    }
   public List<GameMessage> showGameChat(int index, String Name_receiver) throws RemoteException{
        List<GameMessage> playerChat= new ArrayList<>();
        for(int i=0; i< lobbychat.get(index).size(); i++){
            if( lobbychat.get(index).get(i).getReceiver().equals("EVERYONE")|| lobbychat.get(index).get(i).getReceiver().equals(Name_receiver)|| lobbychat.get(index).get(i).getName_Creator().equals(Name_receiver)){
                playerChat.add(lobbychat.get(index).get(i));
            }
        }
        return playerChat;
    }
    public void appendchatmex(int index, List<String> possibleChatmex, String myname){
        lobbychat.get(index).add(new GameMessage(possibleChatmex.get(1),myname, possibleChatmex.get(0)));
    }


    public List<String> playersName(int index) throws RemoteException{
        List<String> playersNameList= new ArrayList<>();
        for (int i=0; i<Lobby.get(index).getPlayersList().size();i++){
            playersNameList.add(Lobby.get(index).getPlayersList().get(i).getName());
        }
        return playersNameList;
    }
    @Override
    public Tile[][] getDashboard(int index) throws RemoteException{
        return Lobby.get(index).getDashboardTiles();
    }

    @Override
    public Tile[][] getMyShelfie(int index, String playerName, int playerId) throws RemoteException {
        return Lobby.get(index).getPlayersList().get(playerId).getShelfMatrix();
    }
    public Shelf getMyShelfieREF(int index, String playerName, int playerId) throws RemoteException{
        return Lobby.get(index).getPlayersList().get(playerId).getShelf();
    }
    @Override
    public PersonalGoal getMyPersonalGoal(int index, int playerId) throws RemoteException {
        return Lobby.get(index).getPlayersList().get(playerId).getPersonalGoal();
    }
    @Override
    public List<CommonGoals> getCommonGoals(int index) throws RemoteException {
        return Lobby.get(index).getCommonGoals();
    }

    @Override
    public int myPoints(int index, int playerId) throws RemoteException {
        return Lobby.get(index).getPlayersList().get(playerId).getPoints();
    }

    public int myPGpoints(int index, int playerId)  throws RemoteException{
        return Lobby.get(index).getPlayersList().get(playerId).getPGpoints();
    }

    @Override
    public Tile[] getSelectedTiles(int index,int tilesToPick, List<Integer> yCoord, List<Integer> xCoord) throws RemoteException {
        Tile[] returnedTiles= new Tile[tilesToPick];
        int x=0;
        for(int i=0; i<tilesToPick; i++){

            returnedTiles[i]= new Tile(Lobby.get(index).getDashboardTiles()[xCoord.get(x)][yCoord.get(x)].getColor(), Lobby.get(index).getDashboardTiles()[xCoord.get(x)][yCoord.get(x)].getId());
            x++;
        }
        return returnedTiles;
    }

    @Override
    public String checkWinner(int index, int id) throws RemoteException {

        if(Lobby.get(index).checkWinner().getId()== id){
            return "YOU WON";
        }
        return "YOU LOST";
    }

    @Override
    public int getCurrentPlayer( int index) throws RemoteException {
        return Lobby.get(index).playerTurn().getId();
    }

    @Override
    public Message getMyMessage(int index) throws RemoteException{
        return LobbyMessage.get(index);
    }
}
