package it.lysz210.fluitrix.models;

public class Coordinate2D {
    private final int x;
    public int x() {
        return x;
    }
    private final int y;
    public int y() {
        return y;
    }

    public Coordinate2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate2D(Coordinate2D original) {
        this(original.x(), original.y());
    }

    public Movement move(Orientation direction) {
        return Movement.of(new Coordinate2D(this), direction);
    }

    public Movement moveUp() {
        return move(Orientation.UP);
    }

    public Movement moveDown() {
        return move(Orientation.DOWN);
    }

    public Movement moveLeft() {
        return move(Orientation.LEFT);
    }

    public Movement moveRight() {
        return move(Orientation.RIGHT);
    }
}
