package com.opendrop.processing;

import com.opendrop.commons.Writer;
import processing.core.PApplet;
import processing.serial.Serial;

import static processing.core.PApplet.printArray;
import static processing.core.PApplet.println;

public class SerialWriter implements Writer {
    private final Serial serialPort;

    public SerialWriter(PApplet applet, int rate) throws NoSerialPortException {
        String[] portList = Serial.list();
        if (portList == null || portList.length == 0) {
            throw new NoSerialPortException("FATAL ERROR: No serial ports found.");
        }

        println("Available serial ports:");
        printArray(portList);

        String portName = portList[0];
        println("Connecting to: " + portName);
        serialPort = new Serial(applet, portName, rate);
    }

    @Override
    public void write(int value) {
        serialPort.write(value);
    }

    @Override
    public void stop() {
        serialPort.stop();
    }
}
