package model;

import model.COLOR;

public class TileLayout {
    private int x;
    private int y;
    private String color;

    public TileLayout(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", color='" + color + '\'' +
                '}';
    }

    public COLOR convert(){
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
