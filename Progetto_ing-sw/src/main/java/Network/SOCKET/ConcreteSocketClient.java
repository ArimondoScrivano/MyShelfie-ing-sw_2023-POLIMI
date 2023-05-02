package Network.SOCKET;
import java.io.*;
import java.net.Socket;

public class ConcreteSocketClient {
    private final int defaultPortNumber=16001;
    private ObjectInputStream ois;
    //ois.readObject();
    private String name;
    private Socket soc; //communication socket
    private BufferedReader userInput; //user input stream
    private BufferedReader in; //server stream reader
    private PrintWriter out; //out stream
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
            name=userInput.readLine(); //player's name

            System.out.println("do you want to START a new game/JOIN an existent game");
            String response= userInput.readLine();
            boolean responseAccepted=false;
            while(!responseAccepted){
                if(response.equals("START")){
                    responseAccepted=true;
                }else if(response.equals("JOIN")){
                    responseAccepted=true;
                }else{
                    System.out.println("please choose between 'START' and 'JOIN' ");
                }
            }
            out.println(response);
        }catch(IOException e){

        }
    }
    public void sendMessages(){
        try{
            System.out.println("insert a message");
            String str=userInput.readLine();
            out.println(str);
        }catch(IOException e){

        }
    }
}
