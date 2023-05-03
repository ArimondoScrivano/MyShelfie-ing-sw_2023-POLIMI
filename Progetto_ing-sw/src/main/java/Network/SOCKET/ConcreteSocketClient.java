package Network.SOCKET;
import Network.RMI.Server_RMI;
import Network.messages.Message;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public class ConcreteSocketClient {
    private final int defaultPortNumber=16001; //serverIdentity
    private ObjectInputStream ois;
    //ois.readObject();
    private Socket soc; //communication socket
    private BufferedReader userInput; //user input stream
    private BufferedReader in; //server stream reader
    private PrintWriter out; //out stream
    private int LobbyReference; //riferimento alla Lobby
    private String playerName; //nome del giocatore
    private int myId; //indice del giocatore
    public ConcreteSocketClient(){
        try{
            soc=new Socket("localhost", defaultPortNumber); //socket di comunicazione con il server
            userInput=new BufferedReader(new InputStreamReader(System.in)); //stream di lettura del flusso da tastiera
                                                                            //(interazione con l'user)
            out=new PrintWriter(soc.getOutputStream(), true);
            in= new BufferedReader(new InputStreamReader(soc.getInputStream())); //lettore dello stream da server
            InputStream objectInput= soc.getInputStream();
            ois=new ObjectInputStream(objectInput);

            System.out.println("insert your name");
            playerName=userInput.readLine(); //player's name
            decodeMessage(0);
        }catch(IOException e){

        }
    }
    /*^public void sendMessages(){
        try{
            System.out.println("insert a message");
            String str=userInput.readLine();
            out.println(str);
        }catch(IOException e){

        }
    }*/
    public void decodeMessage(int parameter){
        if(parameter==0){ //parametro 0-> setup della lobby
            try{
                System.out.println("START a new game/JOIN an existent game");
                String response= userInput.readLine();
                boolean responseAccepted=false;
                while(!responseAccepted){
                    if(response.equals("START")){
                        responseAccepted=true;
                        //out.println(response);
                        //startingGame();
                        System.out.println("how many players?");
                        int numberOfPlayers=userInput.read();
                        createLobby(numberOfPlayers, playerName);
                    }else if(response.equals("JOIN")){
                        responseAccepted=true;
                        out.println(response);
                        joinLobby();
                    }else{
                        System.out.println("please choose between 'START' and 'JOIN' ");
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    //Lobby creata quando il cient invia il messaggio start
    public void createLobby(int numPL, String creatorLobby) { //riadattata da RMI
        out.println("START");
        boolean setupFinished=false;
        String serverResponse;
        while(!setupFinished){
            try{
                serverResponse=in.readLine();
                if(serverResponse.equals("NAME")){
                    out.println(playerName);
                }else if(serverResponse.equals("NUMBER OF PLAYERS")){
                    out.println(numPL);
                } else if(serverResponse.equals("LOBBY")){
                    LobbyReference=in.read();
                    setupFinished=true;
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public int getLobbyReference(){ //a cosa serviva in rmi?
        return this.LobbyReference;
    }

    public void joinLobby() {
        boolean setupFinished=false;
        String serverResponse;
        while(!setupFinished){
            try{
                serverResponse=in.readLine();
                if(serverResponse.equals("NAME")){
                    out.println(playerName);
                }else if(serverResponse.equals("NAME ERROR")){ //gestire l'errore

                }else if(serverResponse.equals("LOBBY")){
                    LobbyReference=in.read();
                }else if(serverResponse.equals("ORDER")){
                    myId=in.read();
                    setupFinished=true;
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void addPlayer(String name) { //perché a carico del client?
        /*try {
            this.myId = server.addPlayer(LobbyReference, name);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }*/
    }

    public boolean isItMyTurn(){
        String serverResponse;
        try{
            out.println("MY_TURN?");
            serverResponse=in.readLine();
            if(serverResponse.equals("NO")){return false;}
            return true;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public Tile[][] getDashboard() {
        /*try {
            return server.getDashboard(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/ return null;
    }

    public Tile[][] getMyShelfie(){

        /*try{
            return server.getMyShelfie(LobbyReference, playerName, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/return null;
    }

    public PersonalGoal getMyPersonalGoal(){

        /*try {
            return server.getMyPersonalGoal(LobbyReference, myId);
        }catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/ return null;


    }

    public List<CommonGoals> getCommonGoals(){
        /*try {
            return server.getCommonGoals(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/ return null;
    }
    public boolean pickableTiles(List<Integer> xCoord, List<Integer> yCoord){
        /*try{
            return server.pickableTiles(LobbyReference, xCoord, yCoord);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }*/ return false;

    }



    public boolean columnAvailable(int numTiles, int selectedCol){
        /*try{
            return  server.columnAvailable(LobbyReference, numTiles, server.getMyShelfieREF(LobbyReference,playerName, myId), selectedCol);
        } catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }*/ return false;

    }

    public void FinalPick(int tilesToPick, List<Integer> xCord,List<Integer> yCord ){
        /*try{
            server.finalPick(LobbyReference,xCord, yCord);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }




    public void insertTiles ( List<Integer> xCoord, List<Integer> yCoord, int column){
        /*try {
            server.insertTiles(LobbyReference, xCoord,yCoord,column);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }*/
    }
    public String checkWinner() {
        /*try {
            return server.checkWinner(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return "ERROR";
        }*/ return "ciao";
    }

    public int myPoints(){
        /*try{
            return server.myPoints(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return -1;
        }*/ return 0;

    }

    public Message notifyMe(){
        /*try{
            return server.getMyMessage(LobbyReference);
        } catch (RemoteException e) {
            //System.out.println("ERROR BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/ return null;

    }
}
