package it.lysz210.fluitrix.models;

import it.lysz210.fluitrix.utils.GridMerger;

import java.util.ArrayList;
import java.util.List;

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

    public boolean canMoveTetraminoTo(Tetramino tetramino, Orientation direction) {
        Coordinate2D tetraminoPosition = tetramino.getPosition();
        Coordinate2D newPosition = tetraminoPosition.move(direction).destination();
        int offsetY = newPosition.y();

        for (int y = 0; y < tetramino.getWidth(); y++) {
            int newY = y + offsetY;
            if (0 <= newY && newY < this.height) {
                continue;
            }
            for (int x = 0; x < tetramino.getHeight(); x++) {
                if (tetramino.getCell(new Coordinate2D(x, y)) > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isTouchingFloor(Grid other) {
        int offsetX = other.getPosition().x();
        int offsetY = other.getPosition().y();

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
            int newX = x + offsetX;
            if (newX >= this.getWidth() || newX < 0) {
                continue;
            }
            for (int y = other.getHeight() -1; y >= 0; y--) {
                int newY = y + offsetY;
                if (newY >= this.getHeight() || newY < 0) {
                    continue;
                }
                boolean otherCellActive = other.getCell(new Coordinate2D(x, y)) > 0;
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

    public List<Action> clearLines() {
        List<Action> actionSequenceBuilder = new ArrayList<>();
        for (int x = 0; x < this.getWidth(); x++) {
            boolean isFull = true;
            for (int y = 0; y < this.getHeight() && isFull; y++) {
                isFull = grid[x][y] > 0;
            }
            if (isFull) {
                for (int i = x; i >= 0; i--) {
                    actionSequenceBuilder.add(new BubbleUpAction(i));
                }
            }
        }
        return actionSequenceBuilder;
    }

    class BubbleUpAction implements Action {
        private final int line;
        private boolean done;

        public BubbleUpAction(int line) {
            this.line = line;
            done = false;
        }

        @Override
        public void execute() {
            if (done) {
                return;
            }
            if (line < 0) {
                done = true;
                return;
            }
            if (line == 0) {
                grid[line] = new byte[height];
            } else {
                byte[] upperline = grid[line - 1];
                grid[line - 1] = grid[line];
                grid[line] = upperline;
            }
            done = true;
        }
    }
}
