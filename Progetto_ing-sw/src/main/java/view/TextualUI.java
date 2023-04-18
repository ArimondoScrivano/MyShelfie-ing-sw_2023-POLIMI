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
        if(input.equals("RMI")){
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
    }

    //Showing the actual state of the game
    @Override
    public void showMatchInfo(Game current_game) {
        System.out.println("""
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
                 '----------------'  '----------------'              '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'  '----------------'\s""");


        //Printing the dashboard state
        System.out.println("Current Dashboard");
        Tile[][] copy=current_game.getDashboard().getTilesCopy();
        String copyColor = new String();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                int color=copy[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInString(copyColor, color);
            }
            System.out.println();

        }
        //Printing the layout of the common goal card
        System.out.println("\u001B[00mCOMMON GOAL CARDS");
        for(CommonGoals cg : current_game.getCommonGoals()){
            //Printing the common goal card
            //TODO: common goal layout missing
        }

        //TODO: output non corretto
        //Printing the shelf and the personal goal card associated to the player
        for(Player p : current_game.getPlayers()){
            System.out.println(p.getName()+"'s shelf");
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    int color=p.getShelf().getTilesShelf()[i][j].getColor().compareTo(COLOR.BLANK);
                    copyColor = convertColorInString(copyColor, color);
                }
                System.out.println();
            }

            System.out.println("Personal Goal");
            for(int i=0; i<6; i++){
                for(int j=0; j<5; j++){
                    int color=p.getPersonalGoal().getLayout()[i][j].getColor().compareTo(COLOR.BLANK);
                    copyColor = convertColorInString(copyColor, color);
                }
                System.out.println();
            }
        }
    }

    private String convertColorInString(String copyColor, int color) {
        switch(color){
            case 0:
                //Blank
                copyColor="\u001B[40m\t";
                break;
            case 1:
                //Green
                copyColor="\u001B[42m\t";
                break;
            case 2:
                //Yellow
                copyColor="\u001B[43m\t";
                break;
            case 3:
                //White
                copyColor="\u001B[47m\t";
                break;
            case 4:
                //Lightblue
                copyColor="\u001B[46m\t";
                break;
            case 5:
                //Violet
                copyColor="\u001B[45m\t";
                break;
            case 6:
                //Blue
                copyColor="\u001B[44m\t";
                break;
        }
        System.out.print(copyColor);
        return copyColor;
    }

    public static void main(String[] args){
        TextualUI UI = new TextualUI();
        List<Player> pl = Arrays.asList(
                new Player(0, "Arimondo"),
                new Player(1, "Lorenzo")
        );
        System.out.println();
        UI.showMatchInfo(new Game(0, new Dashboard(2, new Bag()), pl));
    }
}
