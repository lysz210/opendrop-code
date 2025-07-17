package it.lysz210.fluitrix.models;

public record Coordinate2D(
        int x,
        int y
) {

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
