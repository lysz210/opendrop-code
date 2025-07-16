package it.lysz210.fluitrix.models;

public enum DropletType {
    STILL('X'),
    PIECE('O');

    public final char value;
    DropletType(char value) {
        this.value = value;
    }
}
