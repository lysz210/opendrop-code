package it.lysz210.fluitrix.utils;

import it.lysz210.fluitrix.models.Coordinate2D;
import it.lysz210.fluitrix.models.Grid;

import java.util.function.Function;

public class GridToStringMapper implements Function<Grid, String> {
    @Override
    public String apply(Grid grid) {
        int width = grid.getWidth();
        int height = grid.getHeight();
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int x = 0; x < width; x++) {
            for (int y = 1; y <= height; y++) {
                sb.append(grid.getCell(new Coordinate2D(x, height - y)));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
