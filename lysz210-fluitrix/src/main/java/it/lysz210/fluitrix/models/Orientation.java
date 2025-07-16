package it.lysz210.fluitrix.models;

public enum Orientation {
    // is the first position
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public Orientation next() {
        return switch (this) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
}
