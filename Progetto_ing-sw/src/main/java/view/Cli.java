package view;

import model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cli{
    //Output stream
    private final PrintStream out;
    //Input stream
    private final Scanner in = new Scanner(new InputStreamReader(System.in));
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public Cli() {
        this.out = System.out;
    }

    //Asking the connection method
    public int askConnection() {
        out.println("Choose the connection method that you prefer");
        out.println("1 for RMI and 2 for Socket");
        int i=in.nextInt();
        while(i<1 || i>2){
            out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 2! Retry "+ColorUI.RESET);
            i=in.nextInt();
        }
        return i;
    }

    //Asking the nickname
    public String askNickname() {
        out.print("Choose your nickname: ");
        //Checking if the name is not the same as other players in game
        return in.nextLine();
    }

    public boolean askNewGame(){
        out.println("Do you want to create a new game?[Y/N]");
        String read=in.nextLine();
        return read.equals("Y") || read.equals("y");
    }


    public int askNumberOfPlayers(){
        out.print("Select the number of players between 2 and 4 ");
        int numberOfPlayers=in.nextInt();
        while(numberOfPlayers<2 || numberOfPlayers>4){
            out.print(ColorUI.RED_TEXT+"You must choose a number of players between 2 and 4! Retry "+ColorUI.RESET);
            numberOfPlayers=in.nextInt();
        }
        return numberOfPlayers;
    }


    public List<String> askNewChatMessage(List<String> playersName, String myplayername){
        String read;

        do{
        out.print(ColorUI.YELLOW_TEXT+"Do you want to write a message?[Y]||[N]"+ColorUI.RESET);
        read=in.next();

        if((!read.equals("Y") && !read.equals("y") && !read.equals("n") && !read.equals("N"))){
            out.println(ColorUI.RED_TEXT+"WRONG PARAMETERS"+ColorUI.RESET);
        }

        } while(!read.equals("Y") && !read.equals("y") && !read.equals("n") && !read.equals("N"));


        if(read.equals("Y") || read.equals("y")){
            out.println(ColorUI.YELLOW_TEXT+"Who do you want to send it to?"+ColorUI.RESET);
            int myIndex=0;
            //Displaying the possible receivers
            for(int i=0; i<playersName.size(); i++){
                if(playersName.get(i).equals(myplayername)){
                    out.println(ColorUI.BLUE_TEXT+"[not selectable]"+ColorUI.RESET);
                    myIndex= i;
                }else{
                    out.println(ColorUI.GREEN_TEXT+"[" +i +"]|| " +playersName.get(i) +ColorUI.RESET);
                }
            }
            out.println(ColorUI.GREEN_TEXT+"[4]|| Broadcast message" +ColorUI.RESET);
           int indexSelectedreceiver=in.nextInt();
            //Check if the receiver is correct

                while (indexSelectedreceiver == myIndex || (indexSelectedreceiver >= playersName.size() && indexSelectedreceiver != 4) || indexSelectedreceiver < 0) {
                    out.println(ColorUI.RED_TEXT + "WRONG PARAMETERS" + ColorUI.RESET);
                    out.print(ColorUI.YELLOW_TEXT + "Select a correct number" + ColorUI.RESET);
                    indexSelectedreceiver = in.nextInt();
                }

            out.println(ColorUI.GREEN_TEXT+"Type your message and then press Enter" +ColorUI.RESET);
            String context= "no text";
            try {
               context = reader.readLine();
            } catch (IOException e) {
                context = "An error has occurred";
            }
            List<String> messagedone= new ArrayList<>();
            if(indexSelectedreceiver==4){
                messagedone.add("EVERYONE");
            }else {
                messagedone.add((playersName.get(indexSelectedreceiver)));
            }
            messagedone.add(context);
            out.println("il messagio è:" +messagedone.get(1) +" e il destinatario è "+messagedone.get(0));
            return messagedone;
        }else{
            List<String> NOmessage= new ArrayList<>();
            NOmessage.add("no message");
            return NOmessage;
        }
    }

    //Asking the number of tiles to pick
    public int askNumberOfTiles(){
        out.print("Chose the number of tiles that you want to pick: ");
        int numberOfTile=in.nextInt();
        while(numberOfTile<1 || numberOfTile>3){
            out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 3! Retry "+ColorUI.RESET);
            numberOfTile=in.nextInt();
        }
        return numberOfTile;
    }

    //Coordinate delle tiles da prendere
    public List<Integer> askTilesToPick(int numberOfTile){
        //Se 3 tile da prendere -->lista con 6 elementi
        List<Integer> tilesToPick = new ArrayList<>();
        out.println("Choose the tiles to pick using x and y coordinates");
        for(int i=0; i<numberOfTile*2; i++){
            if(i%2==0){
                out.print("Chose the x coordinate of the tile that you want to pick: ");
                int x=in.nextInt();
                //Check if the value is between 1 and 10
                while(x<0 || x>11){
                    out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 10! Retry "+ColorUI.RESET);
                    x=in.nextInt();
                }
                tilesToPick.add(x);
            }else{
                out.print("Chose the y coordinate of the tile that you want to pick: ");
                int y=in.nextInt();
                //Check if the value is between 1 and 10
                while(y<0 || y>11){
                    out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 10! Retry "+ColorUI.RESET);
                    y=in.nextInt();
                }
                tilesToPick.add(y);
            }
        }
        return tilesToPick;
    }

    public int askColumn(){
        out.print("Choose the column to insert the tiles chosen: ");
        int column=in.nextInt();
        while(column<1 || column>5){
            out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 5! Retry "+ColorUI.RESET);
            column= in.nextInt();
        }
        return (column-1);
    }

    public void displayPoints(int myPoint, int myPGpoints){
        out.println("Current points: "+ myPoint +"  and " +myPGpoints +" Personal Goal points");
    }
}
