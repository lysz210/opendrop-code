package it.lysz210.fluitrix.models;

public enum Orientation {
    // is the first position
    UP('↑'),
    RIGHT('→'),
    DOWN('↓'),
    LEFT('←');

    public final char symbol;
    Orientation(char symbol) {
        this.symbol = symbol;
    }

    public Orientation next() {
        switch (this) {
            case UP:
                return RIGHT;
            case RIGHT:
                return DOWN;
            case DOWN:
                return LEFT;
            case LEFT:
                return UP;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
