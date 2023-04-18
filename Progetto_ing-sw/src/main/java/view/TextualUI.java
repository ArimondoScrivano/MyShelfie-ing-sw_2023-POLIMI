package view;

import model.*;

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
        Tile[][] copy=current_game.getDashboard().getTilesCopy();
        String copyColor = new String();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                int color=copy[i][j].getColor().compareTo(COLOR.BLANK);
                switch(color){
                    case 0:
                        //Blank
                        copyColor="\033[40m\u001B[30m* ";
                        break;
                    case 1:
                        //Green
                        copyColor="\033[42m\u001B[32m* ";
                        break;
                    case 2:
                        //Yellow
                        copyColor="\033[43m\u001B[33m* ";
                        break;
                    case 3:
                        //White
                        copyColor="\033[47m\u001B[00m* ";
                        break;
                    case 4:
                        //Lightblue
                        copyColor="\033[46m\u001B[36m* ";
                        break;
                    case 5:
                        //Violet
                        copyColor="\033[45m\u001B[35m* ";
                        break;
                    case 6:
                        //Blue
                        copyColor="\033[44m\u001B[34m* ";
                        break;
                }
                System.out.print(copyColor);
            }
            System.out.println();

        }
        //Printing the layout of the common goal card
        System.out.println("\u001B[00mCOMMON GOAL CARDS");

        //Printing the shelf and the personal goal card associated to the player
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
