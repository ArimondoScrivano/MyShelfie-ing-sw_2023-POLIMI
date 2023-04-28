package view;

import model.*;
import model.cgoal.CommonGoals;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class TextualUI extends Observable implements View {

    //Game state
    private enum State{
        WAITING, TILES_PICK, COLUMN_CHOICE, ENDGAME_STARTED, GAME_FINISHED
    }

    private State state = State.WAITING;

    private final Object lock = new Object();

    //Getter method for the state
    private State getState(){
        synchronized (lock){
            return state;
        }
    }

    //Setter method for the state
    private void setState(State state) {
        synchronized (lock) {
            this.state = state;
            lock.notifyAll();
        }
    }

    @Override
    public void askConnection() {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        System.out.println("Choose the connection method:");
        String input = in.nextLine();
        if(input.equals("Network/RMI")){
            System.out.println("RMI connection chose");
            //TODO:RMI CONNECTION
        } else if (input.equals("Socket")) {
            System.out.println("Socket connection chose");
            //TODO: Socket connection
        }else{
            System.out.println("I don't know how to use this connection method :(");
            askConnection();
        }
    }

    //Recalling the method in controller to check if the name is not the same
    @Override
    public void askNickname() {
        Scanner in = new Scanner(new InputStreamReader(System.in));
        System.out.println("Choose your nickname:");
        String input = in.nextLine();
        //Checking if the name is not the same as other players in game
    }

    //Showing the actual state of the game
    @Override
    public void showMatchInfo(Game current_game) {
        clearUI();
        //TODO: Showing this print only at the beginning of the game
        System.out.println(ColorUI.YELLOW_TEXT + """
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


        clearUI();
        //Printing the dashboard state
        System.out.println(ColorUI.RED_TEXT+"Current Dashboard"+ColorUI.RESET);
        Tile[][] copy=current_game.getDashboard().getTilesCopy();
        String copyColor = "";
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                int color=copy[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInStringTiles(copyColor, color);
            }
            System.out.println();
        }

        //Printing the layout of the common goal card
        System.out.println(ColorUI.BLUE_TEXT+"COMMON GOAL CARDS"+ColorUI.RESET);
        for(CommonGoals cg : current_game.getCommonGoals()){
            if(cg.equals(current_game.getCommonGoals().get(0))){
                System.out.println(ColorUI.RED_TEXT+"First Common Goal"+ColorUI.RESET);
                //Printing the common goal card
                cg.printLayout();
                //Printing the available points
                System.out.println("Points available");
                //Printing the available points in reverse order
                for(int i=cg.getScoreList().size()-1; i>=0; i--){
                    if(i==0){
                        System.out.println(cg.getScoreList().get(i));
                    }else{
                        System.out.print(cg.getScoreList().get(i)+", ");
                    }
                }
            }else{
                System.out.println(ColorUI.RED_TEXT+"\nSecond Common Goal"+ColorUI.RESET);
                //Printing the common goal card
                cg.printLayout();
                //Printing the available points
                System.out.println("Points available");
                //Printing the available points in reverse order
                for(int i=cg.getScoreList().size()-1; i>=0; i--){
                    if(i==0){
                        System.out.println(cg.getScoreList().get(i));
                    }else{
                        System.out.print(cg.getScoreList().get(i)+", ");
                    }
                }
            }
        }

        //TODO: Rendere più visibile la shelf e dividere i quadrati della shelf in personal goal, da implementare la parte di visione del solo personal goal del giocatore connesso e non la visione complessiva
        //Printing the shelf and the personal goal card associated to the player
        for(Player p : current_game.getPlayers()){
            System.out.println(p.getName()+"'s shelf");
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    int color=p.getShelf().getTilesShelf()[i][j].getColor().compareTo(COLOR.BLANK);
                    copyColor = convertColorInStringTiles(copyColor, color);
                }
                System.out.println();
            }

            System.out.println("Personal Goal");
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    int color=p.getPersonalGoal().getLayout()[i][j].getColor().compareTo(COLOR.BLANK);
                    copyColor = convertColorInStringTiles(copyColor, color);
                }
                System.out.println();
            }
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
        System.out.print(copyColor);
        return copyColor;
    }

    //Clearing the UI
    public void clearUI(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args){
        TextualUI UI = new TextualUI();
        List<Player> pl = Arrays.asList(
                new Player(0, "Arimondo"),
                new Player(1, "Lorenzo")
        );
        System.out.println();
        UI.showMatchInfo(new Game(0, new Dashboard(2, new Bag()), pl, pl.size()));
    }
}
