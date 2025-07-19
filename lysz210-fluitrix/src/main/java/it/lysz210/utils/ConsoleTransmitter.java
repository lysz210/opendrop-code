package it.lysz210.utils;

import com.opendrop.commons.Transmitter;

public class ConsoleTransmitter implements Transmitter {
    @Override
    public void transmit(boolean[][] electrodes) {
        StringBuilder sb = new StringBuilder();
        sb.append(">>>>>>>>>>>>>>>>>>>>");
        sb.append("\n");
        for (boolean[] row : electrodes) {
            for (boolean electrode : row) {
                sb.append(electrode ? '#' : '_');
            }
            sb.append("\n");
        }
        sb.append("<<<<<<<<<<<<<<<<<<<<");
        sb.append("\n");
        System.out.println(sb.toString());
    }

    @Override
    public void clear() {
        System.out.println("Clearing board");
    }

    @Override
    public void close() {
        clear();
    }
}
