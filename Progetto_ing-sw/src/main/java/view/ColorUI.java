package view;

//Colors contained in the view
public enum ColorUI {
    //Text Colors
    //Reset the colors
    RESET("\u001B[00m"),
    RED_TEXT("\u001B[31m"),
    BLUE_TEXT("\u001B[34m"),
    YELLOW_TEXT("\u001B[33m"),


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

    ColorUI(String code){
        this.code=code;
    }

    public String toString(){
        return code;
    }
}
