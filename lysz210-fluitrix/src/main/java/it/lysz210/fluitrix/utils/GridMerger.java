package it.lysz210.fluitrix.utils;

import it.lysz210.fluitrix.models.Coordinate2D;
import it.lysz210.fluitrix.models.Grid;

import java.util.function.Consumer;

public class GridMerger implements Consumer<Grid> {
    private final byte[][] originalGrid;
    private final int width;
    private final int height;

    public GridMerger(byte[][] originalGrid) {
        this.originalGrid = originalGrid;
        this.width = originalGrid.length;
        this.height = originalGrid[0].length;
    }
    @Override
    public void accept(Grid other) {
        var offsetX = other.getPosition().x();
        var offsetY = other.getPosition().y();
        for (int x = 0; x < other.getWidth(); x++) {
            var newX = offsetX + x;
            if (newX < 0 || newX >= width) {
                continue;
            }
            for (int y = 0; y < other.getHeight(); y++) {
                var newY = offsetY + y;
                if (newY < 0 || newY >= height) {
                    continue;
                }
                this.originalGrid[newX][newY] += other.getCell(new Coordinate2D(x, y));
            }
        }
    }
}
