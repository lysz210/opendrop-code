package it.lysz210.utils;

import com.opendrop.commons.Transmitter;

public class ConsoleTransmitter implements Transmitter {

    @Override
    public void transmit(boolean[][] electrodes) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>");
        for (boolean[] row : electrodes) {
            StringBuilder sb = new StringBuilder();
            for (boolean electrode : row) {
                sb.append(electrode ? '#' : '_');
            }
            System.out.println(sb);
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<");
    }

    @Override
    public void clear() {
        System.out.println("Clearing board");
    }

    @Override
    public void close() {
        // do nothing
    }
}
