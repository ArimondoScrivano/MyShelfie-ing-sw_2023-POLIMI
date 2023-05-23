package Network;

import Network.GameChat.GameMessage;
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

public class Server extends UnicastRemoteObject implements Runnable,Server_RMI {
    private final int defaultPort;
    private ServerSocket serverSocket;
    private final Object lock;

    // How it works:
    //integer= indice della lobby, la mappa interna contiene l'associazione nome Giocatore, il suo socket
    private Map<Integer, Map<String, SocketClientHandler> > clientHandlerMap;
    private List<GameController> Lobby;
    private List<Message> LobbyMessage;
    private Map<Integer, List<GameMessage>> lobbychat;

    public Server(int defaultPort) throws RemoteException {
        super();
        this.defaultPort=defaultPort;
        this.clientHandlerMap = Collections.synchronizedMap(new HashMap<>());
        this.lock=new Object();
        this.Lobby=new ArrayList<>();
        this.LobbyMessage=new ArrayList<>();
        this.lobbychat= new HashMap<>();
    }

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
                //set the timeout to infinity
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

    public int createLobby(int numPlayers, String creatorLobby, Message message, SocketClientHandler clientHandler){
        System.out.println("Creating a lobby");
        GameController controller = new GameController(numPlayers, this, creatorLobby);
        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED));
        controller.setId(Lobby.indexOf(controller));
        this.clientHandlerMap.put(Lobby.indexOf(controller), new HashMap<>());
        addPlayer(message.getName(), clientHandler, Lobby.indexOf(controller), 1);
        return Lobby.indexOf(controller);
    }

    public int joinLobby(Message message, SocketClientHandler clientHandler){

        for (int i = 0; i < Lobby.size(); i++) {
            if (!Lobby.get(i).isFull()) {
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
        Lobby.add(controller);
        this.clientHandlerMap.put(Lobby.indexOf(controller), new HashMap<>());
        addPlayer(message.getName(), clientHandler, Lobby.indexOf(controller), 0);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED ));
        controller.setId(Lobby.indexOf(controller));
        return Lobby.indexOf(controller);
    }

    public void addPlayer(String name, SocketClientHandler clientHandler, int index, int mult){
        System.out.println("Adding player "+ name);
        //Checking no duplicates of the name
        boolean flag=clientHandlerMap.get(index).containsKey(name);

        for(int count=0; count<Lobby.get(index).getPlayersList().size(); count++){
            if(Lobby.get(index).getPlayersList().get(count).getName().equals(name)){
                flag=true;
            }
        }
        if(flag){
            clientHandler.sendMessage(new Message(name, SocketMessages.NAME_FAILED, index));

        }else{
            clientHandlerMap.get(index).put(name, clientHandler);
            clientHandlerMap.get(index).get(name).sendMessage(new Message(name, SocketMessages.LOGIN_REPLY, index));
            //legato alla joinLobby
            System.out.println("Porcamadonna"+mult);
            if(mult==0){
                int IndexPlayer=Lobby.get(index).getPlayersFilled();
                Lobby.get(index).createPlayer(IndexPlayer, name);
            }
        }

    }

    //Messages for game flow management
    public void onMessageReceived(Message message) throws RemoteException{
        System.out.println("Message received");
        switch(message.getMsg()){
            case IS_GAME_STARTING -> {
                checkGameStarting(message);
            }
            case IS_IT_MY_TURN -> {
                System.out.println("iuser " +message.getName() +" chiede se è il suo turno");
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
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.PARAMETERS_OK,
                            Lobby.get(message.getNp()).getDashboardTiles(),
                            Lobby.get(message.getNp()).getCommonGoals(),
                            Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getShelfMatrix(),
                            Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPersonalGoal(),
                            Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPoints(),
                            Lobby.get(message.getNp()).getPlayersList().get(Lobby.get(message.getNp()).finderPlayer(message.getName())).getPGpoints()));
                }else{
                    clientHandlerMap.get(message.getNp()).get(message.getName()).sendMessage(new Message("server",SocketMessages.PARAMETERS_KO));
                }
            }

            case MY_TURN_ENDED ->{
                int index= message.getNp();

                if(Lobby.get(index).getEnd()==1){
                    for( String chiave : clientHandlerMap.get(message.getNp()).keySet()) {
                        //For generalizzato sulla mappa
                        clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.GAME_ENDING));
                    }
                }else{
                    for( String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                        //For generalizzato sulla mappa
                        clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.CHECK_YOUR_TURN));
                    }                }
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
        }
    }

    public String nameCurrentPlayer( int index) {
        return Lobby.get(index).playerTurn().getName();
    }

    //TODO:invio messaggio a tutti i client collegati a quella lobby che fa terminare immediatamente la partita
    public void onDisconnect(SocketClientHandler clientHandler){
        synchronized (lock){

        }
    }
    public void checkGameStarting(Message message){
        if (Lobby.get(message.getNp()).isFull()){

            for( String chiave : clientHandlerMap.get(message.getNp()).keySet()) {
                //For generalizzato sulla mappa
                clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.GAME_STARTING));
            }
        }else{
            for( String chiave : clientHandlerMap.get(message.getNp()).keySet()) {
                //For generalizzato sulla mappa
                clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.WAITING_FOR_OTHER_PLAYERS));
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

    public boolean checkSocketWinner(int index, String name) {
        if(Lobby.get(index).checkWinner().getName().equals(name)){
            return true;
        }
        return false;
    }


    public void setMessage( Message message){
        LobbyMessage.add(message.getId(), message);
        if (message.getMessageType().equals(MessageType.GAME_STARTING) || message.getMessageType().equals(MessageType.SOMETHINGCHANGED)) {
            for( String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                //For generalizzato sulla mappa
                clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.CHECK_YOUR_TURN));
            }

        }else if(message.getMessageType().equals(MessageType.GAME_ENDING)){
            for( String chiave : clientHandlerMap.get(message.getId()).keySet()) {
                //For generalizzato sulla mappa
                if(checkSocketWinner(message.getId(), chiave)){
                    clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.WINNER));
                }else{
                    clientHandlerMap.get(message.getNp()).get(chiave).sendMessage(new Message("server", SocketMessages.LOSER));
                }
            }
        }
    }

    @Override
    public int createLobby(int numPlayers, String creatorLobby) throws RemoteException {
        GameController controller = new GameController(numPlayers, this, creatorLobby);
        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED ));
        controller.setId(Lobby.indexOf(controller));
        lobbychat.put(Lobby.indexOf(controller), new ArrayList<>());
        return Lobby.indexOf(controller);
    }

    @Override
    public int joinLobby() throws RemoteException {

        for (int i = 0; i < Lobby.size(); i++) {
            if (!Lobby.get(i).isFull()) {
                return i;
            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller = new GameController(2, this);
        Lobby.add(controller);
        LobbyMessage.add(Lobby.indexOf(controller), new Message(Lobby.indexOf(controller), MessageType.LOBBYCREATED ));
        controller.setId(Lobby.indexOf(controller));
        return Lobby.indexOf(controller);
    }

    @Override
    public int addPlayer(int index, String name) throws RemoteException {
        int IndexPlayer=Lobby.get(index).getPlayersFilled();
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
