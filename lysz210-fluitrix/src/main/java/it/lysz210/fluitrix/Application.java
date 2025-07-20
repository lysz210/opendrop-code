package it.lysz210.fluitrix;

import com.opendrop.commons.Transmitter;
import com.opendrop.processing.NoSerialPortException;
import com.opendrop.processing.SerialTransmitter;
import it.lysz210.fluitrix.models.Coordinate2D;
import it.lysz210.fluitrix.models.Game;
import it.lysz210.fluitrix.models.GameCommand;
import it.lysz210.utils.ConsoleTransmitter;
import it.lysz210.utils.MultiTransmitter;
import it.lysz210.utils.PAppletTransmiter;
import processing.core.PApplet;
import processing.core.PImage;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

public class Application extends PApplet {
    public static final Coordinate2D BOARD_OFFSET = new Coordinate2D(132, 225);
    public static final int CELL_SIZE = 25;

    public Game game;
    private Transmitter transmitter;
    PImage img;

    @Override
    public void settings(){
        URL imageFile = this.getClass().getClassLoader().getResource("OpenDropFrame.png");
        try {
            transmitter = new MultiTransmitter(
                    // uncomment SerialTransmitter to send signals to OpenDrop board
//                    new SerialTransmitter(this, 115200),
                    new PAppletTransmiter(this, BOARD_OFFSET, CELL_SIZE)
            );
            img = loadImage(imageFile.toURI().getPath());
            size(img.width, img.height);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (NoSerialPortException ex) {
            println(ex.getMessage());
            showMessageDialog(null, "No serial (COM) ports found.\nPlease ensure your OpenDrop device is connected and check your drivers.", "Serial Port Error", ERROR_MESSAGE);
            exit();
        }
    }

    @Override
    public void setup() {
        game = new Game(14, 8);
    }

    @Override
    public void draw(){
        background(255, 250, 240);
        image(img, 0, 0, img.width, img.height);
        game.process(GameCommand.PROCESS_STEP);
        boolean[][] electrods = game.electrode();
        transmitter.transmit(electrods);
    }

    @Override
    public void keyPressed(){
        Optional<GameCommand> command = GameCommand.fromKey(key);
        if (!command.isPresent()) {
            return;
        }
        game.process(command.get());
    }
}
