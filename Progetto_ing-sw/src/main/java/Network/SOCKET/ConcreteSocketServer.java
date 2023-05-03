package Network.SOCKET;

import Network.RMI.ConcreteServerRMI;
import controller.GameController;
import model.*;
import model.cgoal.CommonGoals;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<Integer, List<BufferedReader>> inputStreamMap; //mappa che mantiene una traccia degli streams
    // di input delle varie lobby
    private HashMap<Integer, List<PrintWriter>> outputStreamMap;
    private HashMap<Integer, List<ObjectOutputStream>> objStreamMap;
    private HashMap<Integer, List<ObjectInputStream>> objInStreamMap;
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
            BufferedReader in=new BufferedReader(new InputStreamReader(soc.getInputStream())); //creato per ogni client che si collega
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
    public void receiveMessages(int lobbyIndex)throws IOException, ClassNotFoundException{ //decoder dei messaggi
        Player currentPlayer=Lobby.get(lobbyIndex).playerTurn();
        int playerIndex=currentPlayer.getId();
        List<BufferedReader> inputStreams=inputStreamMap.get(lobbyIndex);
        BufferedReader myInputStream=inputStreams.get(playerIndex);
        List<PrintWriter> outputStreams= outputStreamMap.get(lobbyIndex);
        PrintWriter myOutputStream= outputStreams.get(playerIndex);
        myOutputStream.println("TURN"); //your turn
        List<ObjectInputStream> objInputStream= objInStreamMap.get(lobbyIndex);
        ObjectInputStream myObjectInputStream= objInputStream.get(playerIndex);
        boolean messageReceived=false;
        while(!messageReceived){
            try{
                myInputStream.readLine();
                messageReceived=true;
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        if(clientMessage.equals("CHOSEN_TILES")){ //tiles pescate, deve essere mandato da picktiles
            //Chiamata a pickable tiles
            List<Integer> xCoord=new ArrayList<>();
            myOutputStream.println("X_COORDINATE");
            try{
                xCoord=(List<Integer>) myObjectInputStream.readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            List<Integer> yCoord=new ArrayList<>();
            myOutputStream.println("Y_COORDINATE");
            try{
                yCoord=(List<Integer>) myObjectInputStream.readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            pickableTiles(lobbyIndex, playerIndex, xCoord, yCoord);

        }else if(clientMessage.equals("MY_TURN?")){
            //già gestita
        }else if(clientMessage.equals("TILES_PICKED")){// mandato da picktiles
            //chiamata a dashboard
            getDashboard(lobbyIndex);
        }else if(clientMessage.equals("COLUMN_CHOSEN")){ //mandato da choosecolumn shelf
            myOutputStream.println("NUMBER_TILES");
            int numOfTiles=myInputStream.read();
            Shelf myShelf=Lobby.get(lobbyIndex).playerTurn().getShelf();
            myOutputStream.println("COLUMN");
            int column=myInputStream.read();
            columnAvailable(lobbyIndex, numOfTiles, myShelf, column, playerIndex);
            List<Integer> xCoord=new ArrayList<>();
            myOutputStream.println("X_COORDINATE");
            try{
                xCoord=(List<Integer>) myObjectInputStream.readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            List<Integer> yCoord=new ArrayList<>();
            myOutputStream.println("Y_COORDINATE");
            try{
                yCoord=(List<Integer>) myObjectInputStream.readObject();
            }catch(Exception e){
                e.printStackTrace();
            }
            insertTiles(lobbyIndex, xCoord, yCoord, column);
            getMyShelfie(lobbyIndex, playerIndex);
        }else if(clientMessage.equals("TURN_FINISHED")){ //mandato a turno finito
            //getPoints()
        }else if(clientMessage.equals("GAME_ENDED")){ //mandato da finalPick
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
    public void getDashboard(int index){ //originariamente di tipo Tile[][]
        try{
             //implementare la ricezione lato client
            List<ObjectOutputStream> objectStreams= objStreamMap.get(index);
            for(int i=0; i<objectStreams.size(); i++){
                objectStreams.get(i).writeObject(Lobby.get(index).getDashboardTiles());
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void getMyShelfie(int index, int playerId){
       //sistemare perché è un po' brutto
        try{
            List<ObjectOutputStream> objectStreams= objStreamMap.get(index);
            objectStreams.get(playerId).writeObject(Lobby.get(index).getPlayersList().get(playerId).getShelfMatrix());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getMyPersonalGoal(int index, int playerId){
        try{
            List<ObjectOutputStream> objectStreams= objStreamMap.get(index);
            objectStreams.get(playerId).writeObject(Lobby.get(index).getPlayersList().get(playerId).getPersonalGoal());
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void getCommonGoals(int index){
        try{
            List<ObjectOutputStream> objectStreams= objStreamMap.get(index);
            for(int i=0; i<objectStreams.size(); i++){
                objectStreams.get(i).writeObject(Lobby.get(index).getCommonGoals());
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
        List<PrintWriter> outputStreams=outputStreamMap.get(index);
        outputStreams.get(playerIndex).println(myResponse);

    }

    public void columnAvailable(int index, int numTiles, Shelf myShelf, int selectedCol, int playerIndex){ //in caso di server socket le funzioni avranno un parametro in più
        String myResponse;
        boolean available=Lobby.get(index).columnAvailable(numTiles, myShelf, selectedCol);
        if(available){
            myResponse="TRUE";
        }else{
            myResponse="FALSE";
        }
        List<PrintWriter> outputStreams=outputStreamMap.get(index);
        outputStreams.get(playerIndex).println(myResponse);

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
        List<PrintWriter> outputStreams=outputStreamMap.get(index);
        outputStreams.get(id).println(myResponse);
    }
}