package it.lysz210.fluitrix.utils;

import it.lysz210.fluitrix.models.Coordinate2D;
import it.lysz210.fluitrix.models.Grid;

import java.util.function.Function;

public class GridToElectrodsMapper implements Function<Grid, boolean[][]> {
    @Override
    public boolean[][] apply(Grid grid) {
        boolean[][] electrods = new boolean[grid.getWidth()][grid.getHeight()];
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                electrods[x][y] = grid.getCell(new Coordinate2D(x, y)) > 0;
            }
        }
        return electrods;
    }

    public boolean[][] apply(byte[][] grid) {
        boolean[][] electrods = new boolean[grid.length][grid[0].length];
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                electrods[x][y] = grid[x][y] > 0;
            }
        }
        return electrods;
    }
}
