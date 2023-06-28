package view;

import Network.GameChat.GameMessage;
import model.COLOR;
import model.PersonalGoal;
import model.Tile;
import model.cgoal.CommonGoals;

import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * The TextualUI class represents the textual user interface for the game.
 * It implements the View interface and provides methods for displaying game information and interacting with the user.
 */
public class TextualUI  implements View {

    //Output stream
    private final PrintStream out;
    //Input stream


    /**
     * Scanner object for reading input from the standard input stream.
     */
    private final Scanner in = new Scanner(new InputStreamReader(System.in));


    /**
     * Constructs a TextualUI object.
     * Initializes the output stream to the standard output.
     */
    public TextualUI() {
        out = System.out;
    }



    /**
     * Initializes a new game.
     * This method performs the necessary setup and preparations for starting a new game.
     */
    public void initGame(){}



    /**
     * Ends the game and displays the outcome.
     * This method prints the provided outcome message to the standard output.
     *
     * @param esito The outcome message to be displayed.
     */
    @Override
    public void endGame(String esito) {
        System.out.println(ColorUI.YELLOW_TEXT+esito+ColorUI.RESET);
    }


    //Initialize the game
    /**
     * Initializes the game.
     * This method clears the user interface, then prints a welcome message and the game logo to the standard output.
     */
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

        out.println(ColorUI.YELLOW_TEXT+"Welcome to MY SHELFIE game"+ColorUI.RESET);
    }




    //Showing the actual state of the game
    /**
     * Shows the match information on the user interface.
     * This method clears the user interface and then prints the current dashboard state, the layout of the common goal card,
     * the shelf, and the personal goal card associated with the player.
     *
     * @param copy         The copy of the dashboard state.
     * @param commonGoals  The list of common goals.
     * @param myShelf      The shelf of the player.
     * @param pg           The personal goal card associated with the player.
     */
    @Override
    public void showMatchInfo(Tile[][] copy, List<CommonGoals> commonGoals, Tile[][] myShelf, PersonalGoal pg) {
        clearUI();
        //Printing the dashboard state
        out.println(ColorUI.RED_TEXT+"Current Dashboard"+ColorUI.RESET);


        printDashboard(copy);

        //Printing the layout of the common goal card
        printCommonGoal(commonGoals);

        //Printing the shelf and the personal goal card associated to the player
        printShelf(myShelf);

        printPersonalGoal(pg);
    }



    /**
     * Prints the personal goal card on the user interface.
     *
     * @param pg The personal goal card to be printed.
     */
    public void printPersonalGoal(PersonalGoal pg){
        String copyColor="";
        out.println("Your Personal Goal");
        for(int i=0; i<5; i++){
            out.print(" "+(i+1)+"    ");
        }
        out.println();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                int color=pg.getLayout()[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInStringTiles(copyColor, color);
            }
            out.println();
        }
    }



    /**
     * Displays a new message notification on the user interface.
     * This method prints the text "New message" in yellow color on the console.
     */
    public void shownewMex(){
    System.out.println(  "\u001B[33m" + "New message" + "\u001B[0m");
}



    /**
     * Displays the game chat messages on the user interface.
     * If the provided list of game messages is not empty, it prints the sender's name and the content of each message.
     * If the list is empty, it prints a notification indicating that there are no messages to display yet.
     *
     * @param listToDisplay The list of game messages to be displayed.
     */
    public void showGameChat(List<GameMessage> listToDisplay){
        if (listToDisplay.size()>0) {
            for (int i = 0; i < listToDisplay.size(); i++) {
                System.out.println("from: " + "\u001B[33m" + listToDisplay.get(i).getName_Creator() + "\u001B[0m");
                System.out.println("Content of the message: ");
                System.out.println(listToDisplay.get(i).getMyGameMessage());

            }
        }else{
            System.out.println(  "\u001B[33m" + "No message to dispay yet" + "\u001B[0m");
        }
    }



    /**
     * Prints the dashboard on the user interface.
     * The dashboard is represented by a two-dimensional array of tiles.
     * Each tile's color is printed according to its corresponding position in the array.
     *
     * @param copy The two-dimensional array representing the dashboard.
     */
    public void printDashboard(Tile[][] copy){
        String copyColor="";
        //First row
        for(int i=0; i<11; i++){
            out.print("  "+i+"    ");
        }
        out.print("\n");
        //Dashboard print
        for(int i=0; i<11; i++){
            out.print(i);
            for(int j=0; j<11; j++){
                int color=copy[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInStringTiles(copyColor, color);
            }
            out.println();
        }
    }



    /**
     * Prints the shelf and the personal goal card associated with the player on the user interface.
     * The shelf is represented by a two-dimensional array of tiles.
     * Each tile's color is printed according to its corresponding position in the array.
     *
     * @param myShelf The two-dimensional array representing the shelf and personal goal card.
     */
    public void printShelf(Tile[][] myShelf){
        String copyColor="";
        //Printing the shelf and the personal goal card associated to the player
        for(int i=0; i<5; i++){
            out.print(" "+(i+1)+"    ");
        }
        out.println();
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                int color=myShelf[i][j].getColor().compareTo(COLOR.BLANK);
                copyColor = convertColorInStringTiles(copyColor, color);
            }
            out.println();
        }
    }




    /**
     * Prints the common goal cards on the user interface.
     * Each common goal card contains a layout and an available score.
     *
     * @param commonGoals A list of CommonGoals objects representing the common goal cards.
     */
    public void printCommonGoal(List<CommonGoals> commonGoals){
        out.println(ColorUI.BLUE_TEXT+"COMMON GOAL CARDS"+ColorUI.RESET);
        for(CommonGoals cg : commonGoals){
            if(cg.equals(commonGoals.get(0))){
                out.println(ColorUI.RED_TEXT+"\nFirst Common Goal"+ColorUI.RESET);
                //Printing the common goal card
                cg.printLayout();
                //Printing the available score
                if(cg.getCurrent_point()==0){
                    out.println("No points available for this common goal");
                }else{
                    out.println("Points available: "+ cg.getCurrent_point());
                }
            }else{
                out.println(ColorUI.RED_TEXT+"\nSecond Common Goal"+ColorUI.RESET);
                //Printing the common goal card
                cg.printLayout();
                //Printing the available score
                if(cg.getCurrent_point()==0){
                    out.println("No points available for this common goal");
                }else{
                    out.println("Points available: "+ cg.getCurrent_point());
                }
            }
        }
    }





    //Function that convert the color
    /**
     * Converts the color value of a tile into a string representation with the corresponding background color.
     * The converted string is printed on the user interface.
     *
     * @param copyColor The current string representation of the tile's color.
     * @param color     The color value of the tile to be converted.
     * @return The updated string representation of the tile's color.
     */
    private String convertColorInStringTiles(String copyColor, int color) {
        switch (color) {
            case 0 ->
                //Blank
                    copyColor = ColorUI.BLANK_BG+"    "+ColorUI.RESET;
            case 1 ->
                //Green
                    copyColor = ColorUI.GREEN_BG+"    "+ColorUI.RESET;
            case 2 ->
                //Yellow
                    copyColor = ColorUI.YELLOW_BG+"    "+ColorUI.RESET;
            case 3 ->
                //White
                    copyColor = ColorUI.WHITE_BG+"    "+ColorUI.RESET;
            case 4 ->
                //Lightblue
                    copyColor = ColorUI.LIGHTBLUE_BG+"    "+ColorUI.RESET;
            case 5 ->
                //Violet
                    copyColor = ColorUI.VIOLET_BG+"    "+ColorUI.RESET;
            case 6 ->
                //Blue
                    copyColor = ColorUI.BLUE_BG+"    "+ColorUI.RESET;
        }
        out.print(copyColor);
        return copyColor;
    }




    //Clearing the UI
    /**
     * Clears the user interface by printing special escape characters that move the cursor to the top-left position
     * and then flushes the output stream.
     */
    public void clearUI(){
        out.println("\033[H\033[2J");
        out.flush();
    }
}
