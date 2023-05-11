package Network.SOCKET;
import Network.messages.Message;
import Network.messages.MessageType;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ConcreteSocketClient {

    //TODO: controllare pezzi commentati
    //
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
    private boolean myGameEnded=false;
    public ConcreteSocketClient(String name){
        try{
            /*soc=new Socket("localhost", defaultPortNumber); //socket di comunicazione con il server
            System.out.println("asked for connection pemission");
            //System.out.println("connection established");
            userInput=new BufferedReader(new InputStreamReader(System.in)); //stream di lettura del flusso da tastiera
                                                                            //(interazione con l'user)
            System.out.println("userInput created");
            out=new PrintWriter(soc.getOutputStream(), true);
            System.out.println("serverOutput created");
            in= new BufferedReader(new InputStreamReader(soc.getInputStream())); //lettore dello stream da server
            System.out.println("serverInput created");
            OutputStream objectOutput=soc.getOutputStream();
            oos=new ObjectOutputStream(objectOutput);
            System.out.println("serverOutputObject created");
            InputStream objectInput= soc.getInputStream();
            System.out.println("inputStream got");
            ois=new ObjectInputStream(objectInput);
            System.out.println("serverInputObject created");
            playerName=name; //player's name
            System.out.println("Connection established");
            setup();*/
            playerName=name; //player's name
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void setIn(BufferedReader in){
        this.in=in;
    }

    public void setOut(PrintWriter out){
        this.out=out;
    }

    public void setOis(ObjectInputStream ois){
        this.ois=ois;
    }

    public void setOos(ObjectOutputStream oos){
        this.oos=oos;
    }

    public void setup(){
        try{
            System.out.println("START a new game/JOIN an existent game");
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

    //Lobby creata quando il cient invia il messaggio start
    public void createLobby(int numPL, String creatorLobby) { //riadattata da RMI
        //out.println("START");
        System.out.println("now speaking with server");
        boolean setupFinished=false;
        String serverResponse;
        while(!setupFinished){
            try{
                serverResponse=in.readLine();
                if(serverResponse.equals("NAME")){
                    out.println(playerName);
                    System.out.println("successfully communicated my name");
                }else if(serverResponse.equals("NUMBER OF PLAYERS")){
                    out.println(numPL);
                    System.out.println("successfully communicated number of players");
                } else if(serverResponse.equals("LOBBY")){
                    LobbyReference=in.read();
                    setupFinished=true;
                    System.out.println("successfully completed setup");
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    } //funzione aggiornata, definire solo se inviare subito gli oggetti comuni o aspettare gli altri giocatori

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

    //aggiornata
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
    }
    public boolean pickableTiles(List<Integer> xCoord, List<Integer> yCoord){
        out.println("PICKABLE_TILES");
        String serverMessage="NOTHING";
        boolean responseReceived=false;
        while(!responseReceived){
            try{
                serverMessage=in.readLine();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(serverMessage.equals("X_COORDINATE")){
                try{
                    oos.writeObject(xCoord);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(serverMessage.equals("Y_COORDINATE")){
                try{
                    oos.writeObject(yCoord);
                }catch(Exception e){
                    e.printStackTrace();
                }
                responseReceived=true;
            }
        } try{
            serverMessage=in.readLine();
            if(serverMessage.equals("PICKABLE")) return true;
            return false;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }



    public boolean columnAvailable(int numTiles, int selectedCol){
        out.println("COLUMN_CHOSEN");
       String serverResponse="NOTHING";
       boolean responseReceived=false;
       while(!responseReceived){
           try{
               serverResponse=in.readLine();
           }catch(Exception e){
               e.printStackTrace();
           }
           if(serverResponse.equals("NUMBER_OF_TILES")){
               try{
                   out.println(numTiles);
               }catch (Exception e){
                   e.printStackTrace();
               }
           }else if(serverResponse.equals("SELECTED_COL")){
               try{
                   out.println(selectedCol);
               }catch(Exception e){
                   e.printStackTrace();
               }
               responseReceived=true;
           }
       }
       try{
           serverResponse=in.readLine();
       }catch(Exception e){
           e.printStackTrace();
       }
       if(serverResponse.equals("AVAILABLE")) return true;
       return false;
    }


    public void FinalPick(int tilesToPick, List<Integer> xCord,List<Integer> yCord ){
        out.println("TILES_PICKED");
        String serverMessage="NOTHING";
        boolean responseReceived=false;
        while(!responseReceived){
            try{
                serverMessage=in.readLine();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(serverMessage.equals("X_COORDINATE")){
                try{
                    oos.writeObject(xCord);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }if(serverMessage.equals("Y_COORDINATE")){
                try{
                    oos.writeObject(yCord);
                }catch(Exception e){
                    e.printStackTrace();
                }
                responseReceived=true;
            }
        }
    }




    public void insertTiles ( List<Integer> xCoord, List<Integer> yCoord, int column){
        out.println("INSERT_TILES");
        String serverMessage="NOTHING";
        boolean responseReceived=false;
        while(!responseReceived){
            try{
                serverMessage=in.readLine();
            }catch (IOException e){
                e.printStackTrace();
            }
            if(serverMessage.equals("X_COORDINATE")){
                try{
                    oos.writeObject(xCoord);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }if(serverMessage.equals("Y_COORDINATE")){
                try{
                    oos.writeObject(yCoord);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }if(serverMessage.equals("COLUMN")){
                try{
                    out.println(column);
                }catch(Exception e){
                    e.printStackTrace();
                }
                responseReceived=true;
            }
        }
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
    }

    public int myPGpoints(){
        out.println("PG_POINTS");
        try{
            int pgPoints=in.read();
            return pgPoints;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    public void receiveMessage() throws IOException {
        if(in.readLine().equals("ENDED")){myGameEnded=true;}
        else{myGameEnded=false;}
    }
    public Message notifyMe(){
        if(myGameEnded){
            Message myMessage= new Message(0, MessageType.GAME_ENDING);
            return myMessage;
        }
        return null;
    }

}
