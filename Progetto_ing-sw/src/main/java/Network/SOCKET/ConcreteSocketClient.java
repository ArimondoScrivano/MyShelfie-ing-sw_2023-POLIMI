package Network.SOCKET;
import Network.RMI.Server_RMI;
import Network.messages.Message;
import model.PersonalGoal;
import model.Shelf;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ConcreteSocketClient {
    //TODO: creare un message decoder che smisti in base alle richieste del client
    // e alle cose che arrivano dal server

    //tipi di messaggi già definiti
    //START/JOIN
    //PICKABLE_TILES-> X_COORDINATE, Y_COORDINATE
    //MY_TURN?
    //TILES_PICKED
    //NUMBER_TILES
    //COLUMN_CHOSEN-> X_COORDINATE, Y_COORDINATE
    //TURN_FINISHED
    //GAME_ENDED

    //possibii richieste del client verso il server
    //START/JOIN -chiamato da setup()
    //MY_TURN? -chiamato da isMyTurn()
    //DASHBOARD -chiamato da getDashboard()
    //MY_SHELF -chiamato da getMyShelfie()
    //PERSONAL_GOAL -chiamato da getPersonalGoal()
    //COMMON_GOAL -chiamato da getCommonGoal()
    //PICKABLE_TILES -chiamato da pickableTiles()
    //TILES_PICKED -chiamato da finalPick()
    //COLUMN_CHOSEN -chiamato da columnAvailable()
    //INSERT_TILES -chiamato da insertTiles()
    //WINNER -chiamato da checkWinner()
    //POINTS- chiamato da myPoints()
    private final int defaultPortNumber=16001; //serverIdentity
    private ObjectInputStream ois;
    //ois.readObject();
    private ObjectOutputStream oos;
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
            OutputStream objectOutput=soc.getOutputStream();
            oos=new ObjectOutputStream(objectOutput);

            //System.out.println("insert your name");
            playerName=userInput.readLine(); //player's name
            setup();
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
    public void setup(){
        try{
            //System.out.println("START a new game/JOIN an existent game");
            String response= userInput.readLine();
            boolean responseAccepted=false;
            while(!responseAccepted){
                if(response.equals("START")){
                    responseAccepted=true;
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
    public void decodeMessage(int parameter){
        if(parameter==0){
        }
        if(parameter==1){

        }if(parameter==2){
            //eliminato
        }if(parameter==3){
           //eliminato
        }if(parameter==4){
            //eliminato
        }if(parameter==5){
            //eliminato
        }if(parameter==6){
        }if(parameter==7){


        }if(parameter==8){

        }if(parameter==9){

        }if(parameter==10){

        }if(parameter==11){

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
        //già implementata nel server
        int serverResponse;
        try{
            out.println("MY_TURN?");
            serverResponse=in.read();
            if(serverResponse==myId){return true;}
            return false;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public Tile[][] getDashboard() throws IOException, ClassNotFoundException{
        /*try {
            return server.getDashboard(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/
        out.println("DASHBOARD");
        try{
            Tile[][] dashboard;
            dashboard=(Tile[][]) ois.readObject();
            return dashboard;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Tile[][] getMyShelfie() throws IOException, ClassNotFoundException{
        out.println("MY_SHELF");
        try{
            Tile[][] myShelf;
            myShelf=(Tile[][]) ois.readObject();
            return myShelf;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        /*try{
            return server.getMyShelfie(LobbyReference, playerName, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/
    }

    public PersonalGoal getMyPersonalGoal()throws IOException, ClassNotFoundException{
        out.println("PERSONAL_GOAL");
        try{
            PersonalGoal myPersonalGoal;
            myPersonalGoal=(PersonalGoal) ois.readObject();
            return myPersonalGoal;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        /*try {
            return server.getMyPersonalGoal(LobbyReference, myId);
        }catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/


    }

    public List<CommonGoals> getCommonGoals()throws IOException, ClassNotFoundException{
        out.println("COMMON_GOAL");
        try{
            List<CommonGoals> myCommonGoals;
            myCommonGoals= (List<CommonGoals>) ois.readObject();
            return myCommonGoals;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        /*try {
            return server.getCommonGoals(LobbyReference);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return null;
        }*/
    }
    public boolean pickableTiles(List<Integer> xCoord, List<Integer> yCoord){
        out.println("PICKABLE_TILES");
        out.println("X_COORDINATE");
        try{
            oos.writeObject(xCoord);
        }catch(Exception e){
            e.printStackTrace();
        }
        out.println("Y_COORDINATE");
        try{
            oos.writeObject(yCoord);
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            String serverResponse;
            serverResponse=in.readLine();
            if(serverResponse.equals("TRUE")) return true;
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        /*try{
            return server.pickableTiles(LobbyReference, xCoord, yCoord);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }*/

    }



    public boolean columnAvailable(int numTiles, int selectedCol){
        out.println("COLUMN_CHOSEN");
        try{
            String serverResponse;
            serverResponse=in.readLine();
            if(serverResponse.equals("TRUE")) return true;
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
        /*try{
            return  server.columnAvailable(LobbyReference, numTiles, server.getMyShelfieREF(LobbyReference,playerName, myId), selectedCol);
        } catch (Exception e){
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return false;
        }*/

    }

    public void FinalPick(int tilesToPick, List<Integer> xCord,List<Integer> yCord ){
        out.println("TILES_PICKED");
        out.println("X_COORDINATE");
        try{
            oos.writeObject(xCord);
        }catch (Exception e){
            e.printStackTrace();
        }
        out.println("Y_COORDINATE");
        try{
            oos.writeObject(yCord);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    public void insertTiles ( List<Integer> xCoord, List<Integer> yCoord, int column){
        out.println("INSERT_TILES");
        try{
            out.println("X COORDINATE");
            oos.writeObject(xCoord);
            out.println("Y COORDINATE");
            oos.writeObject(yCoord);
            out.println("COLUMN_CHOSEN");
            oos.writeObject(xCoord);
        }catch(Exception e){
            e.printStackTrace();
        }
        /*try {
            server.insertTiles(LobbyReference, xCoord,yCoord,column);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
        }*/
    }
    public String checkWinner() {
        out.println("WINNER");
        try{
            String myResponse;
            myResponse=in.readLine();
            return myResponse;
        }catch(IOException e){
            e.printStackTrace();
            return "ERROR";
        }
        /*try {
            return server.checkWinner(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return "ERROR";
        }*/
    }

    public int myPoints(){
        out.println("POINTS");
        try{
            int myPoints;
            myPoints= in.read();
            return myPoints;
        }catch(IOException e){
            e.printStackTrace();
            return -1;
        }
        /*try{
            return server.myPoints(LobbyReference, myId);
        } catch (Exception e) {
            //System.out.println("ERROR, BAD CONNECTION");
            e.printStackTrace();
            return -1;
        }*/

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
