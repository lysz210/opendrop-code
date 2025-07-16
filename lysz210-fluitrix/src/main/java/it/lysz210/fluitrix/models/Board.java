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
}
