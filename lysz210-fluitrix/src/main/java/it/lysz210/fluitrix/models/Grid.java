package it.lysz210.fluitrix.models;

public interface Grid {
    int getWidth();
    int getHeight();
    Coordinate2D getPosition();
    Coordinate2D move(Orientation direction);

    boolean[][] toElectrods();
}
