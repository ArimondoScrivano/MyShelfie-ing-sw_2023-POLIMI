package view;

import model.*;
import model.cgoal.CommonGoals;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class TextualUI extends Observable implements View {

    //Output stream
    private final PrintStream out;
    //Input stream
    private final Scanner in = new Scanner(new InputStreamReader(System.in));

    public TextualUI() {
        out = System.out;
    }

    //Asking the connection type to the player
    @Override
    public void askConnection(){
        out.println("Choose the connection method:");
        String input = in.nextLine();
        if(input.equals("Network/RMI")){
            out.println("RMI connection chose");
            //TODO:RMI CONNECTION
        } else if (input.equals("Socket")) {
            out.println("Socket connection chose");
            //TODO: Socket connection
        }else{
            out.println("I don't know how to use this connection method :(");
            askConnection();
        }
    }

    //Recalling the method in controller to check if the name is not the same
    @Override
    public void askNickname() {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        out.println("Choose your nickname:");
        String input = in.nextLine();
        //Checking if the name is not the same as other players in game
    }

    //Initialize the game
    public void init(){
        clearUI();
        out.println(ColorUI.YELLOW_TEXT + """
                .----------------.  .----------------.              .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.  .----------------.\s
                | .--------------. || .--------------. |            | .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |
                | | ____    ____ | || |  ____  ____  | |            | |    _______   | || |  ____  ____  | || |  _________   | || |   _____      | || |  _________   | || |     _____    | || |  _________   | |
                | ||_   \\  /   _|| || | |_  _||_  _| | |            | |   /  ___  |  | || | |_   ||   _| | || | |_   ___  |  | || |  |_   _|     | || | |_   ___  |  | || |    |_   _|   | || | |_   ___  |  | |
                | |  |   \\/   |  | || |   \\ \\  / /   | |            | |  |  (__ \\_|  | || |   | |__| |   | || |   | |_  \\_|  | || |    | |       | || |   | |_  \\_|  | || |      | |     | || |   | |_  \\_|  | |
                | |  | |\\  /| |  | || |    \\ \\/ /    | |            | |   '.___`-.   | || |   |  __  |   | || |   |  _|  _   | || |    | |   _   | || |   |  _|      | || |      | |     | || |   |  _|  _   | |
                | | _| |_\\/_| |_ | || |    _|  |_    | |            | |  |`\\____) |  | || |  _| |  | |_  | || |  _| |___/ |  | || |   _| |__/ |  | || |  _| |_       | || |     _| |_    | || |  _| |___/ |  | |
                | ||_____||_____|| || |   |______|   | |            | |  |_______.'  | || | |____||____| | || | |_________|  | || |  |________|  | || | |_____|      | || |    |_____|   | || | |_________|  | |
                | |              | || |              | |            | |              | || |              | || |              | || |              | || |              | || |              | || |              | |
                | '--------------' || '--------------' |            | '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |
                 '----------------'  '----------------'              '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'\s"""+ColorUI.RESET);

        out.println("Welcome to MY SHELFIE game");
    }

    //Showing the actual state of the game
    @Override
    public void showMatchInfo(Tile[][] copy, int np, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg) {
        clearUI();
        //Printing the dashboard state
        out.println(ColorUI.RED_TEXT+"Current Dashboard"+ColorUI.RESET);
        String copyColor = "";
        //First row
        //TODO:CHECKING IF IT WORKS
        switch(np){
            case 2:
                for(int i=0; i<9; i++){
                    if(i==0){
                        continue;
                    }
                    out.print(i+1 + "\t");
                }
                break;
            case 3:
            case 4:
                for(int i=0; i<9; i++){
                    out.print(i+1 + "\t");
                }
                break;
        }

        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                out.println(i+1+"\t");
                int color=copy[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInStringTiles(copyColor, color);
            }
            out.println();
        }

        //Printing the layout of the common goal card
        out.println(ColorUI.BLUE_TEXT+"COMMON GOAL CARDS"+ColorUI.RESET);

        for(CommonGoals cg : commonGoals){
            if(cg.equals(commonGoals.get(0))){
                out.println(ColorUI.RED_TEXT+"First Common Goal"+ColorUI.RESET);
                //Printing the common goal card
                cg.printLayout();
                //Printing the available points
                out.println("Points available");
                //Printing the available points in reverse order
                for(int i=cg.getScoreList().size()-1; i>=0; i--){
                    if(i==0){
                        out.println(cg.getScoreList().get(i));
                    }else{
                        out.print(cg.getScoreList().get(i)+", ");
                    }
                }
            }else{
                out.println(ColorUI.RED_TEXT+"\nSecond Common Goal"+ColorUI.RESET);
                //Printing the common goal card
                cg.printLayout();
                //Printing the available points
                out.println("Points available");
                //Printing the available points in reverse order
                for(int i=cg.getScoreList().size()-1; i>=0; i--){
                    if(i==0){
                        out.println(cg.getScoreList().get(i));
                    }else{
                        out.print(cg.getScoreList().get(i)+", ");
                    }
                }
            }
        }

        //TODO: Rendere più visibile la shelf e dividere i quadrati della shelf in personal goal, da implementare la parte di visione del solo personal goal del giocatore connesso e non la visione complessiva
        //Printing the shelf and the personal goal card associated to the player
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                int color=myShelf[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInStringTiles(copyColor, color);
            }
            out.println();
        }

        out.println("Personal Goal");
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                int color=pg.getLayout()[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInStringTiles(copyColor, color);
            }
            out.println();
        }
    }

    //Function that convert the color
    private String convertColorInStringTiles(String copyColor, int color) {
        switch (color) {
            case 0 ->
                //Blank
                    copyColor = ColorUI.BLANK_BG+"\t"+ColorUI.RESET;
            case 1 ->
                //Green
                    copyColor = ColorUI.GREEN_BG+"\t"+ColorUI.RESET;
            case 2 ->
                //Yellow
                    copyColor = ColorUI.YELLOW_BG+"\t"+ColorUI.RESET;
            case 3 ->
                //White
                    copyColor = ColorUI.WHITE_BG+"\t"+ColorUI.RESET;
            case 4 ->
                //Lightblue
                    copyColor = ColorUI.LIGHTBLUE_BG+"\t"+ColorUI.RESET;
            case 5 ->
                //Violet
                    copyColor = ColorUI.VIOLET_BG+"\t"+ColorUI.RESET;
            case 6 ->
                //Blue
                    copyColor = ColorUI.BLUE_BG+"\t"+ColorUI.RESET;
        }
        out.print(copyColor);
        return copyColor;
    }

    //Clearing the UI
    public void clearUI(){
        out.println("\033[H\033[2J");
        out.flush();
    }
}
