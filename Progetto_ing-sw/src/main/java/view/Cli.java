package view;

import model.Game;

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

    //TODO
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
        return in.nextLine().equals("Y");
    }


    public int askNumberOfPlayers(){
        out.println("Select the number of players between 2 and 4");
        int numberOfPlayers;
        do{
            numberOfPlayers = in.nextInt();
        }while(numberOfPlayers>4 && numberOfPlayers<2);

        return numberOfPlayers;
    }

    //Asking the number of tiles to pick
    public int askNumberOfTiles(){
        int numberOfTile;
        do{
            out.println("Chose the number of tiles that you want to pick: ");
            numberOfTile=in.nextInt();
        }while(numberOfTile>=1 && numberOfTile<=3);
        return numberOfTile;
    }

    //Coordinate delle tiles da prendere
    public List<Integer> askTilesToPick(int numberOfTile){
        //Se 3 tile da prendere -->lista con 6 elementi
        List<Integer> tilesToPick = new ArrayList<>();
        out.println("Choose the tiles to pick using x and y coordinates: ");
        for(int i=0; i<numberOfTile; i++){
            if(i%2==0){
                out.println("Chose the x coordinate of the tile that you want to pick: ");
                tilesToPick.add(in.nextInt());
            }else{
                out.println("Chose the y coordinate of the tile that you want to pick: ");
                tilesToPick.add(in.nextInt());
            }
        }
        return tilesToPick;
    }
    public int askColumn(){
        out.println("Choose the column to insert the tiles chosen: ");
        return in.nextInt()+1;
    }

    public void displayPoints(int myPoint){
        out.println("Your points: "+ myPoint);
    }
}
