package Network.SOCKET;

import Network.RMI.ConcreteServerRMI;
import Network.messages.Message;
import Network.messages.MessageType;
import controller.GameController;
import model.*;
import model.cgoal.CommonGoals;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//possibii richieste del client verso il server
//START/JOIN -chiamato da setup() --gestito esternamente
//MY_TURN? -chiamato da isMyTurn() --parameter 0
//DASHBOARD -chiamato da getDashboard() --parameter 1
//MY_SHELF -chiamato da getMyShelfie() --parameter 2
//PERSONAL_GOAL -chiamato da getPersonalGoal()
//COMMON_GOAL -chiamato da getCommonGoal()
//PICKABLE_TILES -chiamato da pickableTiles()
//TILES_PICKED -chiamato da finalPick()
//COLUMN_CHOSEN -chiamato da columnAvailable()
//INSERT_TILES -chiamato da insertTiles()
//WINNER -chiamato da checkWinner()
//POINTS- chiamato da myPoints()


public class ConcreteSocketServer {

    //TODO: per i test i canali di connessione con il client sono attualmente aperti e gestiti nel main ma NON VA BENE:
// ok gestione, KO apertura perché i metodi mi sentono out (per ora solo questo) come null.
// readmessage deve ricevere la stringa in ingresso e deve essere chiamato dal main. Servono dei getter per i canali.

    //TODO: sistemare i pezzi commentati


    //mappa con liste di stream associate all'indice della lobby
    private List<GameController> Lobby; //lobby di gioco a cui unirsi
    private int portNumber = 16001;
    private ServerSocket s; //gestore delle comunicazioni
    private BufferedReader in; //readers degli stream in arrivo dai Clients mantenuti in una lista
    private PrintWriter out; //streams in uscita verso i clients mantenuti in una lista
    private ObjectOutputStream oos; //objoutstream dei player mantenuti in una lista
    //oos.writeObject(Object obj)
    private ObjectInputStream ois;
    private HashMap<Integer, List<BufferedReader>> inputStreamMap; //mappa che mantiene una traccia degli streams
    // di input delle varie lobby
    private HashMap<Integer, List<PrintWriter>> outputStreamMap;
    private HashMap<Integer, List<ObjectOutputStream>> objStreamMap;
    private HashMap<Integer, List<ObjectInputStream>> objInStreamMap;
    private String clientMessage;

//input da utente a carico della funzione read message
    //output verso l'utente a carico della funzione decode message




    public ConcreteSocketServer()throws IOException{
        try{
            s=new ServerSocket(portNumber);
            /*Socket soc; //gestore della singola comunicazione
            soc= s.accept();
            addPlayer();*/
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public ServerSocket getSocketServer(){
        return this.s;
    }
    public Socket acceptConnections() throws IOException {
        Socket mySocket=s.accept();
        return mySocket;
    }
    public void receiveMessages(BufferedReader in, PrintWriter out, ObjectInputStream ois, ObjectOutputStream oos)throws IOException, ClassNotFoundException{ //decoder dei messaggi
        for(int lobbyIndex=0; lobbyIndex<Lobby.size(); lobbyIndex++) { //to implement multiple matches

            Player currentPlayer = Lobby.get(lobbyIndex).playerTurn();
            int playerIndex = currentPlayer.getId();
            //List<BufferedReader> inputStreams = inputStreamMap.get(lobbyIndex);
            BufferedReader myInputStream = in;
            //List<PrintWriter> outputStreams = outputStreamMap.get(lobbyIndex);
            PrintWriter myOutputStream = out;
            myOutputStream.println("TURN"); //your turn
            //List<ObjectInputStream> objInputStream = objInStreamMap.get(lobbyIndex);
            ObjectInputStream myObjectInputStream = ois;
            //TODO
            boolean messageReceived = false;
            while (!messageReceived) {
                try {
                    myInputStream.readLine();
                    messageReceived = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientMessage.equals("MY_TURN")) { //parameter 0
                decodeMessage(0, lobbyIndex, playerIndex);

            } else if (clientMessage.equals("DASHBOARD")) {
                decodeMessage(1, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("MY_SHELF")) {
                decodeMessage(2, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("PERSONAL_GOAL")) {
                decodeMessage(3, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("COMMON_GOAL")) { //mandato da finalPick
                decodeMessage(4, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("PICKABLE_TILES")) {
                decodeMessage(5, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("TILES_PICKED")) {
                decodeMessage(6, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("COLUMN_CHOSEN")) {
                decodeMessage(7, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("INSERT_TILES")) {
                decodeMessage(8, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("WINNER")) {
                decodeMessage(9, lobbyIndex, playerIndex);
            } else if (clientMessage.equals("POINTS")) {
                decodeMessage(10, lobbyIndex, playerIndex);
            } else if(clientMessage.equals("PG_POINTS")){
                decodeMessage(11, lobbyIndex, playerIndex);
            } else if(clientMessage.equals("START")){
                System.out.println("connected with client");
                addPlayer();
            }
        }
    }

    public void decodeMessage(int parameter, int lobbyIndex, int playerId){
        BufferedReader myInputStream= inputStreamMap.get(lobbyIndex).get(playerId);
        PrintWriter myOutStream= outputStreamMap.get(lobbyIndex).get(playerId);
        ObjectOutputStream myObjOutStream= objStreamMap.get(lobbyIndex).get(playerId);
        ObjectInputStream myObjInStream= objInStreamMap.get(lobbyIndex).get(playerId);
        if(parameter==0){
            //isItMyTurn()
            int myResponse=getCurrentPlayer(lobbyIndex);
            myOutStream.println(myResponse);

        }if(parameter==1){
            //getDashboard()
            Tile[][] myDashboard;
            myDashboard=getDashboard(lobbyIndex);
            List<ObjectOutputStream> objectStreams= objStreamMap.get(lobbyIndex);
            for(int i=0; i<objectStreams.size(); i++){
                try{
                    //inviata a tutti i giocatori
                    objectStreams.get(i).writeObject(myDashboard);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

        }if(parameter==2){
            //getMyShelfie()
            Tile[][] shelf;
            shelf=getMyShelfie(lobbyIndex, playerId);
            try{
                List<ObjectOutputStream> objectStreams= objStreamMap.get(lobbyIndex);
                objectStreams.get(playerId).writeObject(shelf);
            }catch(IOException e){
                e.printStackTrace();
            }

        }if(parameter==3){
            //personal goal
            PersonalGoal myPersonalGoal=getMyPersonalGoal(lobbyIndex, playerId);
            try{
                List<ObjectOutputStream> objectStreams= objStreamMap.get(lobbyIndex);
                objectStreams.get(playerId).writeObject(myPersonalGoal);
            }catch(IOException e){
                e.printStackTrace();
            }

        }if(parameter==4){
            //common goal
            List<CommonGoals> myCommonGoals=Lobby.get(lobbyIndex).getCommonGoals();
            try{
                List<ObjectOutputStream> objectStreams= objStreamMap.get(lobbyIndex);
                for(int i=0; i<objectStreams.size(); i++){
                    //inviata a tutti i giocatori
                    objectStreams.get(i).writeObject(myCommonGoals);
                }
            }catch(IOException e){
                e.printStackTrace();
            }

        }if(parameter==5){
            //pickable tiles
            List<Integer> xCord;
            myOutStream.println("X_COORDINATE");
            List<ObjectInputStream> inputStreams= objInStreamMap.get(lobbyIndex);
            try{
                xCord= (List<Integer>) inputStreams.get(playerId).readObject();
            }catch(Exception e){
                e.printStackTrace();
                xCord=null;
            }
            List<Integer> yCord;
            myOutStream.println("Y_COORDINATE");
            try{
                yCord= (List<Integer>) inputStreams.get(playerId).readObject();
            }catch(Exception e){
                e.printStackTrace();
                yCord=null;
            }
            boolean pickable=pickableTiles(lobbyIndex, playerId, xCord, yCord);
            if(pickable){
                myOutStream.println("PICKABLE");
            }else{
                myOutStream.println("NOT_PICKABLE");
            }

        }if(parameter==6){
            //tiles picked
            List<Integer> xCord;
            myOutStream.println("X_COORDINATE");
            List<ObjectInputStream> inputStreams= objInStreamMap.get(lobbyIndex);
            try{
                xCord= (List<Integer>) inputStreams.get(playerId).readObject();
            }catch(Exception e){
                e.printStackTrace();
                xCord=null;
            }
            List<Integer> yCord;
            myOutStream.println("Y_COORDINATE");
            try{
                yCord= (List<Integer>) inputStreams.get(playerId).readObject();
            }catch(Exception e){
                e.printStackTrace();
                yCord=null;
            }
            finalPick(lobbyIndex, xCord, yCord);

        }if(parameter==7){
            //column chosen
            int numberOfTiles;
            myOutStream.println("NUMBER_OF_TILES");
            try{
                numberOfTiles=myInputStream.read();
            }catch(Exception e){
                e.printStackTrace();
                numberOfTiles=0;
            }
            int selectedCol;
            myOutStream.println("SELECTED_COL");
            try{
                selectedCol=myInputStream.read();
            }catch(Exception e){
                e.printStackTrace();
                selectedCol=0;
            }
            Shelf myShelf=Lobby.get(lobbyIndex).playerTurn().getShelf();
            try{
                myShelf=(Shelf) myObjInStream.readObject();
            }catch(Exception e){
                e.printStackTrace();
                myShelf=null;
            }
            boolean available=Lobby.get(lobbyIndex).columnAvailable(numberOfTiles, myShelf, selectedCol);
            if(available){
                myOutStream.println("AVAILABLE");
            }else{
                myOutStream.println("NOT_AVAILABLE");
            }

        }if(parameter==8){
            //insert tiles
            List<Integer> xCord;
            myOutStream.println("X_COORDINATE");
            List<ObjectInputStream> inputStreams= objInStreamMap.get(lobbyIndex);
            try{
                xCord= (List<Integer>) inputStreams.get(playerId).readObject();
            }catch(Exception e){
                e.printStackTrace();
                xCord=null;
            }
            List<Integer> yCord;
            myOutStream.println("Y_COORDINATE");
            try{
                yCord= (List<Integer>) inputStreams.get(playerId).readObject();
            }catch(Exception e){
                e.printStackTrace();
                yCord=null;
            }
            int selectedCol;
            myOutStream.println("COLUMN");
            try{
                selectedCol=myInputStream.read();
            }catch (Exception e){
                e.printStackTrace();
                selectedCol=0;
            }
            insertTiles(lobbyIndex, xCord, yCord, selectedCol);

        }if(parameter==9){
            //winner
            String myResponse= checkWinner(lobbyIndex, playerId);
            myOutStream.println(myResponse);
        }if(parameter==10){
            //points
            int myPoints;
            myPoints=myPoints(lobbyIndex, playerId);
            myOutStream.println(myPoints);
        }if(parameter==11){
            //personal goal points
            int myPGPoints=myPGpoints(lobbyIndex, playerId);
        }
    }

    public void addPlayer(){ //azioni fatte per ogni nuovo client
        try{
            /*Socket soc; //gestore della singola comunicazione
            soc= s.accept();
            BufferedReader in=new BufferedReader(new InputStreamReader(soc.getInputStream())); //creato per ogni client che si collega
            out=new PrintWriter(soc.getOutputStream(), true);
            OutputStream outputStream= soc.getOutputStream();
            oos=new ObjectOutputStream(outputStream); //canale di passaggio degli oggetti per ogni client che si connette
            InputStream inputStream= soc.getInputStream();
            ois=new ObjectInputStream(inputStream);*/
            //clientMessage=in.readLine();
            String myResponse="myResponse";
            //if(clientMessage.equals("START")){
                myResponse="STARTED";
                //startGame();
                //TODO: implementare due funzioni diverse per join e add da chiamare dal main
            //}else if(clientMessage.equals("JOIN")){
                //myResponse="JOINED";
                //joinGame();
            //}
            //out.println(myResponse);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void startGame(PrintWriter output){
        String name;
        int numOfPlayers=0;
        output.println("NAME"); //any name because he is the creator
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
        out.println("LOBBY");
        out.println(lobbyIndex);
        List<BufferedReader> inputStreams= inputStreamMap.get(lobbyIndex);
        inputStreams.add(in);
        inputStreamMap.put(lobbyIndex, inputStreams); //aggiunta del canale in alla mappa
        List<PrintWriter> outputStreams= outputStreamMap.get(lobbyIndex);
        outputStreams.add(out);
        outputStreamMap.put(lobbyIndex, outputStreams);// aggiunta del canale out alla mappa
        List<ObjectOutputStream> objectStreams= objStreamMap.get(lobbyIndex);
        objectStreams.add(oos);
        List<ObjectInputStream> objectInputStreams=objInStreamMap.get(lobbyIndex);
        objectInputStreams.add(ois);
        objInStreamMap.put(lobbyIndex, objectInputStreams);
        objStreamMap.put(lobbyIndex, objectStreams);// aggiunta del canale obj alla mappa
        getMyPersonalGoal(lobbyIndex, 0);
        getMyShelfie(lobbyIndex, 0);
        //aspettare gli altri giocatori per stampare gli oggetti comuni?
    }
    public void joinGame(){
        try{
            out.println("NAME");
            String playerName=in.readLine();
            //verifica sui nomi uguali
            int lobbyIndex=joinLobby();
            out.println("LOBBY");
            out.println(lobbyIndex);
            int playerIndex=addPlayer(lobbyIndex, playerName);
            List<BufferedReader> inputStreams= inputStreamMap.get(lobbyIndex);
            inputStreams.add(in);
            inputStreamMap.put(lobbyIndex, inputStreams); //aggiunta del canale in alla mappa
            List<PrintWriter> outputStreams= outputStreamMap.get(lobbyIndex);
            outputStreams.add(out);
            outputStreamMap.put(lobbyIndex, outputStreams);// aggiunta del canale out alla mappa
            List<ObjectOutputStream> objectStreams= objStreamMap.get(lobbyIndex);
            objectStreams.add(oos);
            objStreamMap.put(lobbyIndex, objectStreams);// aggiunta del canale obj alla mappa
            out.println("ORDER"); //modificare in base al playerIndex
            out.println(playerIndex);
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
    public Tile[][] getDashboard(int index){ //originariamente di tipo Tile[][]
             //implementare la ricezione lato client
            return Lobby.get(index).getDashboardTiles();
    }
    public Tile[][] getMyShelfie(int index, int playerId){
       //sistemare perché è un po' brutto
        return Lobby.get(index).getPlayersList().get(playerId).getShelfMatrix();
    }

    public PersonalGoal getMyPersonalGoal(int index, int playerId){
        return Lobby.get(index).getPlayersList().get(playerId).getPersonalGoal();
    }

    public List<CommonGoals> getCommonGoals(int index){
        return Lobby.get(index).getCommonGoals();
    }
    public boolean pickableTiles(int index, int playerIndex, List<Integer> xCoord, List<Integer> yCoord){
        return Lobby.get(index).tileAvailablePick(xCoord, yCoord);
    }

    public boolean columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol, int playerIndex){ //in caso di server socket le funzioni avranno un parametro in più
        return Lobby.get(index).columnAvailable(numTiles, myShelf, selectedCol);

    }

    public void insertTiles ( int LobbyReference, List<Integer> xCoord, List<Integer>  yCoord, int column){
        Lobby.get(LobbyReference).insertTiles(xCoord,yCoord,column); //da verificare se trova il giocatore giusto
    }
    public String checkWinner(int index, int id){
        if(Lobby.get(index).checkWinner().getId()== id){
            return "WON";
        }else{
            return "LOST";
        }
    }
    public void finalPick(int index, List<Integer> xCord, List<Integer> yCord){
        Lobby.get(index).pickTiles(xCord,yCord);
    }
    public Shelf getMyShelfieREF(int index, String playerName, int playerId) throws RemoteException{
        return Lobby.get(index).getPlayersList().get(playerId).getShelf(); //deve ritornare al chiamante
    }
    public Tile[] getSelectedTiles(int index,int tilesToPick, List<Integer> yCoord, List<Integer> xCoord) throws RemoteException {
        Tile[] returnedTiles= new Tile[tilesToPick];
        int x=0;
        for(int i=0; i<tilesToPick; i++){

            //returnedTiles[i]= new Tile(Lobby.get(index).getDashboardTiles()[xCoord.get(x)][yCoord.get(x)].getColor(), Lobby.get(index).getDashboardTiles()[xCoord.get(x)][yCoord.get(x)].getId());
            x++;
        }
        return returnedTiles;
    }
    public int getCurrentPlayer( int index) {
        return Lobby.get(index).playerTurn().getId();
    }
    public int myPoints(int index, int playerId){
        return Lobby.get(index).getPlayersList().get(playerId).getPoints();
    }
    public int myPGpoints(int index, int playerId) {
        return Lobby.get(index).getPlayersList().get(playerId).getPGpoints();
    }

    public void endGame(int lobbyIndex){
        List<PrintWriter> myEndedPlayers=outputStreamMap.get(lobbyIndex);
        for(int i=0; i<myEndedPlayers.size(); i++){
            myEndedPlayers.get(i).println("ENDED");
        }
    }
}