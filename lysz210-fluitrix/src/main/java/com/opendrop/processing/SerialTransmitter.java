package com.opendrop.processing;

import com.opendrop.commons.Transmitter;
import com.opendrop.commons.Writer;
import processing.core.PApplet;

public class SerialTransmitter implements Transmitter {
    private final Writer writer;
    private final PApplet applet;

    public SerialTransmitter(PApplet applet, int rate) {
        this.writer = new SerialWriter(applet, rate);
        this.applet = applet;
    }

    @Override
    public void transmit(boolean[][] electrodes) {

        final int GRID_WIDTH = electrodes.length;

        // Build and send the 32-byte packet
        byte[] packet = new byte[32];

        // Main grid columns
        for (int x = 0; x < GRID_WIDTH; x++) {
            int columnByte = 0;
            final int GRID_HEIGHT = electrodes[x].length;
            for (int y = 0; y < GRID_HEIGHT; y++) {
                if (electrodes[x][y]) {
                    columnByte |= (1 << y);
                }
            }
            packet[x + 1] = (byte)columnByte;
        }

        // Send packet byte-by-byte
        for (int i = 0; i < packet.length; i++) {
            writer.write(packet[i]);
        }
    }

    @Override
    public void clear() {
        byte[] clearPacket = new byte[32];
        for (int i = 0; i < clearPacket.length; i++) {
            writer.write(clearPacket[i]);
        }
    }

    @Override
    public void close() {
        clear();
        applet.delay(50);
        writer.stop();
    }
}
