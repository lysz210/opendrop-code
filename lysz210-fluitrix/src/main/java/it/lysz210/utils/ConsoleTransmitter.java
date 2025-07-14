package it.lysz210.utils;

import com.opendrop.commons.Transmitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleTransmitter implements Transmitter {
    public static final Logger logger = LoggerFactory.getLogger(ConsoleTransmitter.class);
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
        logger.info(sb.toString());
    }

    @Override
    public void clear() {
        logger.info("Clearing board");
    }

    @Override
    public void close() {
        clear();
    }
}
