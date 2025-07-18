package it.lysz210.fluitrix.models;

import it.lysz210.fluitrix.utils.GridMerger;

public class Board implements Grid {
    private final int width;
    private final int height;
    private final byte[][] grid;
    private final Coordinate2D position;
    private final GridMerger merger;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        this.grid = new byte[width][height];
        this.position = new Coordinate2D(0, 0);
        this.merger = new GridMerger(this.grid);
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
        return new Coordinate2D(position);
    }

    /**
     * The board never moves
     * @param direction not used
     * @return position
     */
    @Override
    public Coordinate2D move(Orientation direction) {
        return new Coordinate2D(position);
    }

    @Override
    public byte getCell(Coordinate2D position) {
        return grid[position.x()][position.y()];
    }

    public void merge(Grid other) {
        this.merger.accept(other);
    }
}
