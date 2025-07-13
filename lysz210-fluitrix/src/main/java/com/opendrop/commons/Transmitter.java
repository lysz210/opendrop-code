package com.opendrop.commons;

public interface Transmitter {

    void transmit(boolean[][] electrodes);
    void clear();
    void close();
}
