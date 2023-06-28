package model;

/**
 * The TileLayout class represents the layout of a tile in the game.
 */
public class TileLayout {
    private int x;
    private int y;
    private String color;

    /**
     * Constructs a new TileLayout with the specified coordinates and color.
     *
     * @param x     the x-coordinate of the tile
     * @param y     the y-coordinate of the tile
     * @param color the color of the tile
     */
    public TileLayout(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Returns the x-coordinate of the tile.
     *
     * @return the x-coordinate of the tile
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the tile.
     *
     * @param x the x-coordinate of the tile
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the tile.
     *
     * @return the y-coordinate of the tile
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the tile.
     *
     * @param y the y-coordinate of the tile
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the color of the tile.
     *
     * @return the color of the tile
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of the tile.
     *
     * @param color the color of the tile
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns a string representation of the TileLayout object.
     *
     * @return a string representation of the TileLayout object
     */
    @Override
    public String toString() {
        return "TileLayout{" +
                "x=" + x +
                ", y=" + y +
                ", color='" + color + '\'' +
                '}';
    }

    /**
     * Converts the color string to the corresponding COLOR enum value.
     *
     * @return the COLOR enum value representing the color of the tile
     */
    public COLOR convert() {
        return switch (this.color) {
            case "Yellow" -> COLOR.YELLOW;
            case "Violet" -> COLOR.VIOLET;
            case "Blue" -> COLOR.BLUE;
            case "Lightblue" -> COLOR.LIGHTBLUE;
            case "Green" -> COLOR.GREEN;
            default -> COLOR.WHITE;
        };
    }
}