package it.lysz210.fluitrix;

import com.opendrop.commons.Transmitter;
import it.lysz210.fluitrix.models.Board;
import it.lysz210.fluitrix.models.Coordinate2D;
import it.lysz210.fluitrix.models.Game;
import it.lysz210.fluitrix.models.GameCommand;
import it.lysz210.fluitrix.utils.GridToElectrodsMapper;
import it.lysz210.utils.PAppletTransmiter;
import processing.core.PApplet;
import processing.core.PImage;

import java.net.URISyntaxException;

public class Application extends PApplet {
    public static final Coordinate2D BOARD_OFFSET = new Coordinate2D(132, 225);
    public static final int CELL_SIZE = 25;

    public Game game;
    private Transmitter transmitter;
    PImage img;

    @Override
    public void settings(){
        var imageFile = this.getClass().getClassLoader().getResource("OpenDropFrame.png");
        try {
            transmitter = new PAppletTransmiter(this, BOARD_OFFSET, CELL_SIZE);
            img = loadImage(imageFile.toURI().getPath());
            size(img.width, img.height);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
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
        var electrods = game.electrode();
        transmitter.transmit(electrods);
    }

    @Override
    public void keyPressed(){
        var command = GameCommand.fromKey(key);
        if (command.isEmpty()) {
            return;
        }
        game.process(command.get());
    }
}
