package it.lysz210.fluitrix.models;

public class Board implements Grid {
    private final int width;
    private final int height;
    private final DropletType[][] droplets;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.droplets = new DropletType[width][height];
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Coordinate2D getPosition() {
        // todo: implementation
        return null;
    }

    @Override
    public Coordinate2D move(Orientation direction) {
        // todo: implementation
        return null;
    }

    @Override
    public boolean[][] toElectrods() {
        // todo: implementation
        return new boolean[0][];
    }
}
