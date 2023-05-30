package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
        int i = 0;
        while(true){
            try{
                i=in.nextInt();
                while(i<1 || i>2){
                    out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 2! Retry "+ColorUI.RESET);
                    i=in.nextInt();
                }
            }catch(InputMismatchException e){
                System.err.println("You must choose a number");

            }
            return i;
        }
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
        while(true){
            try{
                int numberOfPlayers=in.nextInt();
                while(numberOfPlayers<2 || numberOfPlayers>4){
                    out.print(ColorUI.RED_TEXT+"You must choose a number of players between 2 and 4! Retry "+ColorUI.RESET);
                    numberOfPlayers=in.nextInt();
                }
                return numberOfPlayers;
            }catch(InputMismatchException e){
                System.err.println("You must choose a number");
            }
        }
    }


    public List<String> askNewChatMessage(List<String> playersName, String myplayername){
        String read;
        out.println(ColorUI.YELLOW_TEXT+"Who do you want to send it to?"+ColorUI.RESET);
        int myIndex=0;
            //Displaying the possible receivers
            for(int i=0; i<playersName.size(); i++) {
                if (playersName.get(i).equals(myplayername)) {
                    out.println(ColorUI.BLUE_TEXT + "[not selectable]" + ColorUI.RESET);
                    myIndex = i;
                } else {
                    out.println(ColorUI.GREEN_TEXT + "[" + i + "]|| " + playersName.get(i) + ColorUI.RESET);
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

    }

    //Asking the number of tiles to pick
    public int askNumberOfTiles(){
        out.print("Chose the number of tiles that you want to pick: ");
        while(true){
            try{
                int numberOfTile=in.nextInt();
                while(numberOfTile<1 || numberOfTile>3){
                    out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 3! Retry "+ColorUI.RESET);
                    numberOfTile=in.nextInt();
                }
                return numberOfTile;
            }catch (InputMismatchException e){
                System.err.println("You must choose a number");
            }
        }
    }

    //Coordinate delle tiles da prendere
    public List<Integer> askTilesToPick(int numberOfTile){
        //Se 3 tile da prendere -->lista con 6 elementi
        List<Integer> tilesToPick = new ArrayList<>();
        out.println("Choose the tiles to pick using x and y coordinates");
        for(int i=0; i<numberOfTile*2; i++){
            if(i%2==0){
                out.print("Chose the x coordinate of the tile that you want to pick: ");
                int x=-1;
                do{
                    try{
                        x=in.nextInt();
                    }catch(InputMismatchException e){
                        System.err.println("You must choose a number! Retry");
                        x=-1;
                    }
                }while(x==-1);
                //Check if the value is between 1 and 10
                while(x<0 || x>11){
                    out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 10! Retry "+ColorUI.RESET);
                    x=-1;
                    do{
                        try{
                            x=in.nextInt();
                        }catch(InputMismatchException e){
                            System.err.println("You must choose a number! Retry");
                            x=-1;
                        }
                    }while(x==-1);
                }
                tilesToPick.add(x);
            }else{
                out.print("Chose the y coordinate of the tile that you want to pick: ");
                int y=-1;
                do{
                    try{
                        y=in.nextInt();
                    }catch(InputMismatchException e){
                        System.err.println("You must choose a number! Retry");
                        y=-1;
                    }
                }while(y==-1);

                //Check if the value is between 1 and 10
                while(y<0 || y>11){
                    out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 10! Retry "+ColorUI.RESET);
                    do{
                        try{
                            y=in.nextInt();
                        }catch(InputMismatchException e){
                            System.err.println("You must choose a number! Retry");
                            y=-1;
                        }
                    }while(y==-1);
                }
                tilesToPick.add(y);
            }
        }
        return tilesToPick;
    }

    public int askColumn(){
        out.print("Choose the column to insert the tiles chosen: ");
        int column=-1;
        do{
            try{
                column =in.nextInt();
            }catch(InputMismatchException e){
                System.err.println("You must choose a number! Retry");
                column=-1;
            }
        }while(column==-1);

        while(column<1 || column>5){
            out.print(ColorUI.RED_TEXT+"You must choose a number between 1 and 5! Retry "+ColorUI.RESET);
            do{
                try{
                    column =in.nextInt();
                }catch(InputMismatchException e){
                    System.err.println("You must choose a number! Retry");
                    column=-1;
                }
            }while(column==-1);
        }
        return (column-1);
    }

    public void displayPoints(int myPoint, int myPGpoints){
        out.println("Current points: "+ myPoint +"  and " +myPGpoints +" Personal Goal points");
    }
}
