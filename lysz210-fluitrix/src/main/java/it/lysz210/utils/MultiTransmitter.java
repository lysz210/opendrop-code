package it.lysz210.utils;

import com.opendrop.commons.Transmitter;

import java.util.Arrays;
import java.util.List;

public class MultiTransmitter implements Transmitter {

    private final List<Transmitter> transmitters;

    public MultiTransmitter(Transmitter ...transmitters) {
        this.transmitters = Arrays.asList(transmitters);
    }


    @Override
    public void transmit(boolean[][] electrodes) {
        transmitters.forEach(transmitter -> transmitter.transmit(electrodes));
    }

    @Override
    public void clear() {
        transmitters.forEach(Transmitter::clear);
    }

    @Override
    public void close() {
        transmitters.forEach(Transmitter::close);
    }
}
