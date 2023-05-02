package Network.SOCKET;

import Network.RMI.ConcreteServerRMI;
import controller.GameController;
import model.Dashboard;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
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
            clientMessage=in.readLine();
            String myResponse="myResponse";
            if(clientMessage.equals("START")){
                myResponse="game started";
                startGame();
            }else if(clientMessage.equals("JOIN")){
                myResponse="game joined";
                joinGame();
            }
            out.println(myResponse);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void startGame(){ //TODO: implementare la funzione lato client
        String name;
        int numOfPlayers=0;
        out.println("What's your name ?"); //any name because he is the creator
        try{
            name=in.readLine();
        }catch(IOException e){
            e.printStackTrace();
        }

        out.println("How many players ?"); //any name because he is the creator
        try{
            numOfPlayers=in.read();
        }catch(IOException e){
            e.printStackTrace();
        }
        int lobbyIndex=createLobby(numOfPlayers);
        Lobby.get(lobbyIndex).addInputStream(in); //sarà nell'indice 0
        Lobby.get(lobbyIndex).addOutputStream(out);
        Lobby.get(lobbyIndex).addObjectStream(oos);
    }
    public void joinGame(){
        try{
            out.println("What's your name ?");
            String playerName=in.readLine();
            int lobbyIndex=joinLobby();
            int playerIndex=addPlayer(lobbyIndex, playerName);
            Lobby.get(lobbyIndex).addInputStream(in);
            Lobby.get(lobbyIndex).addOutputStream(out);
            Lobby.get(lobbyIndex).addObjectStream(oos);
            out.println("you are the second player. "); //modificare in base al playerIndex
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
            myResponse= "YOU WON";
        }else{
            myResponse= "YOU LOST";
        }
        Lobby.get(index).getOutputStream(id).println(myResponse);
    }
}