package it.lysz210.utils;

import com.opendrop.commons.Transmitter;
import it.lysz210.fluitrix.models.Coordinate2D;
import processing.core.PApplet;

public class PAppletTransmiter implements Transmitter {
    private final PApplet applet;
    private final Coordinate2D offset;
    private final int cellSize;

    public PAppletTransmiter(PApplet applet, Coordinate2D offset, int cellSize) {
        this.offset = new Coordinate2D(offset);
        this.cellSize = cellSize;
        this.applet = applet;
    }

    @Override
    public void transmit(boolean[][] electrodes) {
        var width = electrodes.length;
        var height = electrodes[0].length;

        for (int y = 0; y < width; y++) {
            for (int x = 1; x <= height; x++) {
                var offsetX = offset.x() + ((x - 1) * cellSize);
                var offsetY = offset.y() + (y * cellSize);
                if (electrodes[y][height - x]) {
                    applet.rect(offsetX, offsetY, cellSize, cellSize);
                }
            }
        }
    }

    @Override
    public void clear() {
        // do nothing
    }

    @Override
    public void close() {
        // do nothing
    }
}
