package it.lysz210.fluitrix.models;

import it.lysz210.fluitrix.utils.GridMerger;

public class Board implements Grid {
    private final int width;
    private final int height;
    private byte[][] grid;
    private final Coordinate2D position;
    private final GridMerger merger;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        this.grid = new byte[width][height];
        this.position = new Coordinate2D(0, 0);
        this.merger = new GridMerger();
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
        this.grid = this.merger.apply(this, other);
    }

    public boolean isTouchingFloor(Grid other) {
        var offsetX = other.getPosition().x();
        var offsetY = other.getPosition().y();

        if (offsetX >= this.getWidth() -1) {
            throw new RuntimeException("Is underground!");
        }

        if (offsetY  >= this.getHeight()) {
            throw new RuntimeException("Is outside the left wall!");
        }

        if (offsetY <= 0 - other.getWidth()) {
            throw new RuntimeException("Is outside the right wall!");
        }

        for (int x = other.getWidth() -1; x >= 0; x--) {
            var newX = x + offsetX;
            if (newX >= this.getWidth() || newX < 0) {
                continue;
            }
            for (int y = other.getHeight() -1; y >= 0; y--) {
                var newY = y + offsetY;
                if (newY >= this.getHeight() || newY < 0) {
                    continue;
                }
                var otherCellActive = other.getCell(new Coordinate2D(x, y)) > 0;
                if (!otherCellActive && newX + 1 >= this.getWidth()) {
                    continue;
                }
                if (otherCellActive && (newX + 1 >= this.getWidth() || this.grid[newX + 1][newY] > 0)) {
                    return true;
                }
            }
        }
        return false;
    }
}
