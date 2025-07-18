package it.lysz210.fluitrix.models;

public interface Grid {
    int getWidth();
    int getHeight();
    Coordinate2D getPosition();
    Coordinate2D move(Orientation direction);
    byte getCell(Coordinate2D position);

    default boolean[][] toElectrods() {
        var electrods = new boolean[this.getWidth()][this.getHeight()];
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                electrods[x][y] = this.getCell(new Coordinate2D(x, y)) > 0;
            }
        }
        return electrods;
    }
}
