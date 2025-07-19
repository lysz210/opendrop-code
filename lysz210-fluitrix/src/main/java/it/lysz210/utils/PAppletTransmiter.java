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
        int width = electrodes.length;
        int height = electrodes[0].length;
        applet.fill(170);
        for (int y = 0; y < width; y++) {
            for (int x = 1; x <= height; x++) {
                int offsetX = offset.x() + ((x - 1) * cellSize);
                int offsetY = offset.y() + (y * cellSize);
                if (electrodes[y][height - x]) {
                    applet.rect(offsetX, offsetY, cellSize, cellSize);
                }
            }
        }

        applet.fill(255, 1, 255);
        int offsetX = offset.x();
        int offsetY = offset.y() + (3 * cellSize);
        applet.line(offsetX, offsetY, offsetX + (height * cellSize), offsetY);
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
