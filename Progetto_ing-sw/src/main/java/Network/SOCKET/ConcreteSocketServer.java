package Network.SOCKET;

import Network.RMI.ConcreteServerRMI;
import controller.GameController;
import model.*;
import model.cgoal.CommonGoals;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConcreteSocketServer {
    //mappa con liste di stream associate all'indice della lobby
    private List<GameController> Lobby; //lobby di gioco a cui unirsi
    private int portNumber = 16001; //TODO: definire un vero numero di porta
    private ServerSocket s; //gestore delle comunicazioni
    private BufferedReader in; //readers degli stream in arrivo dai Clients mantenuti in una lista
    private PrintWriter out; //streams in uscita verso i clients mantenuti in una lista
    private ObjectOutputStream oos; //objoutstream dei player mantenuti in una lista
    //oos.writeObject(Object obj)
    private ObjectInputStream ois;
    private String clientMessage;






    public ConcreteSocketServer()throws IOException{
        try{
            s=new ServerSocket(portNumber);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void addPlayer(){ //azioni fatte per ogni nuovo client
        try{
            Socket soc; //gestore della singola comunicazione
            soc= s.accept();
            in=new BufferedReader(new InputStreamReader(soc.getInputStream())); //creato per ogni client che si collega
            out=new PrintWriter(soc.getOutputStream(), true);
            OutputStream outputStream= soc.getOutputStream();
            oos=new ObjectOutputStream(outputStream); //canale di passaggio degli oggetti per ogni client che si connette
            InputStream inputStream= soc.getInputStream();
            ois=new ObjectInputStream(inputStream);
            clientMessage=in.readLine();
            String myResponse="myResponse";
            if(clientMessage.equals("START")){
                myResponse="STARTED";
                startGame();
            }else if(clientMessage.equals("JOIN")){
                myResponse="JOINED";
                joinGame();
            }
            out.println(myResponse);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void receiveMessages(int lobbyIndex)throws IOException, ClassNotFoundException{
        Player currentPlayer=Lobby.get(lobbyIndex).playerTurn();
        int playerIndex=currentPlayer.getId();
        Lobby.get(lobbyIndex).getOutputStream(playerIndex).println("TURN"); //your turn
        boolean messageReceived=false;
        while(!messageReceived){
            try{
                String clientMessage=Lobby.get(lobbyIndex).getInputStream(playerIndex).readLine();
                messageReceived=true;
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(clientMessage.equals("CHOSEN_TILES")){
            //Chiamata a pickable tiles
            List<Integer> xCoord=new ArrayList<>();
            Lobby.get(lobbyIndex).getOutputStream(playerIndex).println("X_COORDINATE");
            try{
                xCoord=(List<Integer>) Lobby.get(lobbyIndex).getObjInputStream(playerIndex).readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            List<Integer> yCoord=new ArrayList<>();
            Lobby.get(lobbyIndex).getOutputStream(playerIndex).println("Y_COORDINATE");
            try{
                yCoord=(List<Integer>) Lobby.get(lobbyIndex).getObjInputStream(playerIndex).readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            pickableTiles(lobbyIndex, playerIndex, xCoord, yCoord);

        }else if(clientMessage.equals("TILES_PICKED")){
            //chiamata a dashboard
            getDashboard(lobbyIndex);
        }else if(clientMessage.equals("COLUMN_CHOSEN")){
            Lobby.get(lobbyIndex).getOutputStream(playerIndex).println("NUMBER_TILES");
            int numOfTiles=Lobby.get(lobbyIndex).getInputStream(playerIndex).read();
            Shelf myShelf=Lobby.get(lobbyIndex).playerTurn().getShelf();
            Lobby.get(lobbyIndex).getOutputStream(playerIndex).println("COLUMN");
            int column=Lobby.get(lobbyIndex).getInputStream(playerIndex).read();
            columnAvailable(lobbyIndex, numOfTiles, myShelf, column, playerIndex);
            List<Integer> xCoord=new ArrayList<>();
            Lobby.get(lobbyIndex).getOutputStream(playerIndex).println("X_COORDINATE");
            try{
                xCoord=(List<Integer>) Lobby.get(lobbyIndex).getObjInputStream(playerIndex).readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            List<Integer> yCoord=new ArrayList<>();
            Lobby.get(lobbyIndex).getOutputStream(playerIndex).println("Y_COORDINATE");
            try{
                yCoord=(List<Integer>) Lobby.get(lobbyIndex).getObjInputStream(playerIndex).readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            insertTiles(lobbyIndex, xCoord, yCoord, column);
            getMyShelfie(lobbyIndex, playerIndex);
        }else if(clientMessage.equals("TURN_FINISHED")){
            //getPoints()
        }else if(clientMessage.equals("GAME_ENDED")){
            checkWinner(lobbyIndex, playerIndex); //corretto utilizzo?
        }
        //AZIONI POSSIBILI- START/JOIN già gestite, --funzioni che si attivano
                                                    //-getPersonalGoal()- singolarmente ad ogni player
                                                    //-getCommonGoals()- tutti
                         // SOMETHING CHANGED --funzioni che si devono attivare
                                                //-getDashboard()- tutti
                                                //-getMyShelfie()- solo al player che sta giocando
                                                //-pickableTiles() -solo al player
                                                //-columnAvailable() -solo al player
                                                //-insertTiles() -solo al player
                                                //-getPoints(); -solo al player che sta giocando/tutti
                         // GAME ENDING         //checkWinner()-a tutti
    }


    public void startGame(){ //TODO: implementare la funzione lato client
        String name;
        int numOfPlayers=0;
        out.println("NAME"); //any name because he is the creator
        try{
            name=in.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }

        out.println("NUMBER OF PLAYERS"); //any name because he is the creator
        try{
            numOfPlayers=in.read();
        }catch(IOException e){
            e.printStackTrace();
        }
        int lobbyIndex=createLobby(numOfPlayers);
        Lobby.get(lobbyIndex).addInputStream(in); //sarà nell'indice 0
        Lobby.get(lobbyIndex).addOutputStream(out);
        Lobby.get(lobbyIndex).addObjectStream(oos, ois);
        getMyPersonalGoal(lobbyIndex, 0);
        getMyShelfie(lobbyIndex, 0);
        //aspettare gli altri giocatori per stampare gli oggetti comuni?
    }
    public void joinGame(){
        try{
            out.println("NAME");
            String playerName=in.readLine();
            int lobbyIndex=joinLobby();
            int playerIndex=addPlayer(lobbyIndex, playerName);
            Lobby.get(lobbyIndex).addInputStream(in);
            Lobby.get(lobbyIndex).addOutputStream(out);
            Lobby.get(lobbyIndex).addObjectStream(oos, ois);
            out.println("SECOND"); //modificare in base al playerIndex
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public int createLobby(int numPlayers){
        GameController controller = new GameController(numPlayers, this);
        //per ogni lobby ci dovrebbe essere la lista di stream
        Lobby.add(controller);
        controller.setId(Lobby.indexOf(controller));
        int indexLobby = Lobby.indexOf(controller);
        return indexLobby;
    }
    public int joinLobby(){
        for (int i = 0; i < Lobby.size(); i++) {
            if (!Lobby.get(i).isFull()) {
                return i;
            }
        }
        //if there are no free games, it will create a 2 player lobby
        GameController controller = new GameController(2, this);
        Lobby.add(controller);
        return Lobby.indexOf(controller);
    }

    public int addPlayer(int index, String name){
        int IndexPlayer=Lobby.get(index).getPlayersFilled(); //ordine con cui gioca il player
        Lobby.get(index).createPlayer(IndexPlayer, name);
        return IndexPlayer;
    }
    public void getDashboard(int index){ //originariamente di tipo Tile[][]
        try{
            List<ObjectOutputStream> players;
            players=Lobby.get(index).getObjectStream();
            for(int i=0; i<players.size(); i++){
                players.get(i).writeObject(Lobby.get(index).getDashboardTiles());
            }
             //implementare la ricezione lato client
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void getMyShelfie(int index, int playerId){
       //sistemare perché è un po' brutto
        try{
            Lobby.get(index).getObjectStream().get(playerId).writeObject(Lobby.get(index).getPlayersList().get(playerId).getShelfMatrix());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getMyPersonalGoal(int index, int playerId){
        try{
            Lobby.get(index).getObjectStream().get(playerId).writeObject(Lobby.get(index).getPlayersList().get(playerId).getPersonalGoal());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getCommonGoals(int index){
        try{
            List<ObjectOutputStream> players;
            players=Lobby.get(index).getObjectStream();
            for(int i=0; i<players.size(); i++){
                players.get(i).writeObject(Lobby.get(index).getCommonGoals());
            }
            //implementare la ricezione lato client
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void pickableTiles(int index, int playerIndex, List<Integer> xCoord, List<Integer> yCoord){
        String myResponse;
        boolean pickable=Lobby.get(index).tileAvailablePick(xCoord, yCoord);
        if(pickable){
            myResponse="TRUE";
        }else{
            myResponse="FALSE";
        }
        Lobby.get(index).getOutputStream(playerIndex).println(myResponse);

    }

    public void columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol, int playerIndex){ //in caso di server socket le funzioni avranno un parametro in più
        String myResponse;
        boolean available=Lobby.get(index).columnAvailable(numTiles, myShelf, selectedCol);
        if(available){
            myResponse="TRUE";
        }else{
            myResponse="FALSE";
        }
        Lobby.get(index).getOutputStream(playerIndex).println(myResponse);

    }

    public void insertTiles ( int LobbyReference, List<Integer> xCoord, List<Integer>  yCoord, int column){
        Lobby.get(LobbyReference).insertTiles(xCoord,yCoord,column); //da verificare se trova il giocatore giusto
    }
    public void checkWinner(int index, int id){
        String myResponse;
        if(Lobby.get(index).checkWinner().getId()== id){
            myResponse= "WON";
        }else{
            myResponse= "LOST";
        }
        Lobby.get(index).getOutputStream(id).println(myResponse);
    }
}