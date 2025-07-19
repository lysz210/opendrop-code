package it.lysz210.fluitrix.utils;

import it.lysz210.fluitrix.models.Coordinate2D;
import it.lysz210.fluitrix.models.Grid;

import java.util.function.BiFunction;

public class GridMerger implements BiFunction<Grid, Grid, byte[][]> {

    @Override
    public byte[][] apply(Grid main, Grid other) {
        int width = main.getWidth();
        int height = main.getHeight();
        byte[][] originalGrid = new byte[width][height];
        int offsetX = other.getPosition().x();
        int offsetY = other.getPosition().y();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                originalGrid[x][y] = main.getCell(new Coordinate2D(x, y));
            }
        }
        for (int x = 0; x < other.getWidth(); x++) {
            int newX = offsetX + x;
            if (newX < 0 || newX >= width) {
                continue;
            }
            for (int y = 0; y < other.getHeight(); y++) {
                int newY = offsetY + y;
                if (newY < 0 || newY >= height) {
                    continue;
                }
                originalGrid[newX][newY] += other.getCell(new Coordinate2D(x, y));
            }
        }
        return originalGrid;
    }
}
