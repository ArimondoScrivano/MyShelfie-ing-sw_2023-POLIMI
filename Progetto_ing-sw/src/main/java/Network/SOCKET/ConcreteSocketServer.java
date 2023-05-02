package Network.SOCKET;

import Network.RMI.ConcreteServerRMI;
import controller.GameController;
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
    public void admitPlayers(){ //per ogni nuovo player
        try{
            Socket soc; //gestore della singola comunicazione
            soc= s.accept();
            in=new BufferedReader(new InputStreamReader(soc.getInputStream())); //creato per ogni client che si collega
            out=new PrintWriter(soc.getOutputStream(), true);
            OutputStream outputStream= soc.getOutputStream();
            oos=new ObjectOutputStream(outputStream); //canale di passaggio degli oggetti per ogni client che si connette
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void readMessage(){ //trovare un modo per inviare il PlayerIndex
        try{
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
        createLobby(numOfPlayers);
    }
    public void joinGame(){
        try{
            out.println("What's your name ?");
            String playerName=in.readLine();
            int lobbyIndex=joinLobby();
            int playerIndex=addPlayer(lobbyIndex, playerName);
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
            oos.writeObject(Lobby.get(index).getDashboardTiles()); //implementare la ricezione lato client
            //tenere una lista di output stream
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public Tile[][] getMyShelfie(int index, String playerName, int playerId){
        Tile[][] matrix =new Tile[6][5];
        return matrix;
    }

    public PersonalGoal getMyPersonalGoal(int index, int playerId){
        PersonalGoal myPersonalGoal=new PersonalGoal(2);
        return myPersonalGoal;
    }

    public List<CommonGoals> getCommonGoals(int index){
        List<CommonGoals> commonGoals= new ArrayList<>();
        return commonGoals;
    }
    public boolean pickableTiles(int index, List<Integer> xCoord, List<Integer> yCoord){
        return false;
    }

    public boolean columnAvailable(int index, Tile[] tiles, Shelf myShelf, int selectedCol){
        return false;
    }

    public void insertTiles ( int index, Tile[] tilesToInsert, Shelf myShelf, int columnPicked){}
    public String checkWinner(int index, int id){
        return "ciao";
    }
}