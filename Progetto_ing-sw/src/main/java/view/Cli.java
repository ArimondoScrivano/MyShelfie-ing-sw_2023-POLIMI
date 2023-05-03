package view;

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

    public Cli() {
        this.out = System.out;
    }

    //TODO: when we have the socket connection method
    //Asking the connection method
    public void askConnection() {

    }

    //Asking the nickname
    public String askNickname() {
        out.println("Choose your nickname: ");
        //Checking if the name is not the same as other players in game
        return in.nextLine();
    }

    public boolean askNewGame(){
        out.println("Do you want to create a new game?[Y/N]");
        String read=in.nextLine();
        return read.equals("Y") || read.equals("y");
    }


    public int askNumberOfPlayers(){
        out.println("Select the number of players between 2 and 4");
        int numberOfPlayers=in.nextInt();
        while(numberOfPlayers<2 || numberOfPlayers>4){
            System.out.println(ColorUI.RED_TEXT+"You must choose a number of players between 2 and 4! Retry"+ColorUI.RESET);
            numberOfPlayers=in.nextInt();
        }
        return numberOfPlayers;
    }

    //Asking the number of tiles to pick
    public int askNumberOfTiles(){
        out.println("Chose the number of tiles that you want to pick: ");
        int numberOfTile=in.nextInt();
        while(numberOfTile<1 || numberOfTile>3){
            out.println(ColorUI.RED_TEXT+"You must choose a number between 1 and 3! Retry"+ColorUI.RESET);
            numberOfTile=in.nextInt();
        }
        return numberOfTile;
    }

    //Coordinate delle tiles da prendere
    public List<Integer> askTilesToPick(int numberOfTile){
        //Se 3 tile da prendere -->lista con 6 elementi
        List<Integer> tilesToPick = new ArrayList<>();
        out.println("Choose the tiles to pick using x and y coordinates: ");
        for(int i=0; i<numberOfTile*2; i++){
            if(i%2==0){
                out.println("Chose the x coordinate of the tile that you want to pick: ");
                int x=in.nextInt();
                //Check if the value is between 1 and 10
                while(x<0 || x>11){
                    out.println(ColorUI.RED_TEXT+"You must choose a number between 1 and 10! Retry"+ColorUI.RESET);
                    x=in.nextInt();
                }
                tilesToPick.add(x);
            }else{
                out.println("Chose the y coordinate of the tile that you want to pick: ");
                int y=in.nextInt();
                //Check if the value is between 1 and 10
                while(y<0 || y>11){
                    out.println(ColorUI.RED_TEXT+"You must choose a number between 1 and 10! Retry"+ColorUI.RESET);
                    y=in.nextInt();
                }
                tilesToPick.add(y);
            }
        }
        return tilesToPick;
    }

    public int askColumn(){
        out.println("Choose the column to insert the tiles chosen: ");
        int column=in.nextInt();
        while(column<1 || column>5){
            out.println(ColorUI.RED_TEXT+"You must choose a number between 0 and 4! Retry"+ColorUI.RESET);
            column= in.nextInt();
        }
        return (column-1);
    }

    public void displayPoints(int myPoint, int myPGpoints){
        out.println("Current points: "+ myPoint +"  and " +myPGpoints +" Personal Goal points");
    }
}
