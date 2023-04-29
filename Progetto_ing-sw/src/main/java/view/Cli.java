package view;

import model.Game;

import java.io.InputStreamReader;
import java.io.PrintStream;
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

    public boolean askNewGame(){
        out.println("Do you want to create a new game?");
        return in.nextLine().equals("Yes");
    }

    //Asking the nickname
    public String askNickname() {
        out.println("Choose your nickname:");
        String input = in.nextLine();
        //Checking if the name is not the same as other players in game
        //TODO:after the binding the player is in the lobby, so i will check if the other players have the name that i chose
        return input;
    }

    public int askNumberOfPlayers(){
        out.println("Select the number of players between 2 and 4");
        int numberOfPlayers;
        do{
            numberOfPlayers = in.nextInt();
        }while(numberOfPlayers<=4 && numberOfPlayers>=2);

        return numberOfPlayers;
    }
}
