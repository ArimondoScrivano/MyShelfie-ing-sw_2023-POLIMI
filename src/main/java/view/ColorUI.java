package view;

//Colors contained in the view
/**
 * The ColorUI enum represents different colors used in the view.
 * It provides text colors and background colors for various elements.
 */
public enum ColorUI {
    //Text Colors
    //Reset the colors
    RESET("\u001B[00m"),
    RED_TEXT("\u001B[31m"),
    BLUE_TEXT("\u001B[34m"),
    YELLOW_TEXT("\u001B[33m"),

    GREEN_TEXT("\u001B[32m"),

    //Background colors
    //Used for the tiles
    BLANK_BG("\u001B[40m"),
    GREEN_BG("\u001B[102m"),
    YELLOW_BG("\u001B[103m"),
    WHITE_BG("\u001B[107m"),
    LIGHTBLUE_BG("\u001B[46;1m"),
    VIOLET_BG("\u001B[105m"),
    BLUE_BG("\u001B[104;1m");

    private final String code;



    /**
     * Constructs a ColorUI object with the provided color code.
     *
     * @param code the color code to associate with the ColorUI object.
     */
    ColorUI(String code){
        this.code=code;
    }




    /**
     * Returns the string representation of the ColorUI object.
     *
     * @return the color code associated with the ColorUI object.
     */
    public String toString(){
        return code;
    }
}
