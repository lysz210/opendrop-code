package it.lysz210.fluitrix.models;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class Tetramino implements Grid {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    public static final int INDEX_TOP = 0;
    public static final int INDEX_BOTTOM = 2;
    public static final int INDEX_CENTER = 1;
    public static final int INDEX_LEFT = 2;
    public static final int INDEX_RIGHT = 0;

    public static final Coordinate2D TOP_LEFT = new Coordinate2D(INDEX_TOP, INDEX_LEFT);
    public static final Coordinate2D TOP_CENTER = new Coordinate2D(INDEX_TOP, INDEX_CENTER);
    public static final Coordinate2D TOP_RIGHT = new Coordinate2D(INDEX_TOP, INDEX_RIGHT);

    public static final Coordinate2D CENTER_LEFT = new Coordinate2D(INDEX_CENTER, INDEX_LEFT);
    public static final Coordinate2D CENTER_CENTER = new Coordinate2D(INDEX_CENTER, INDEX_CENTER);
    public static final Coordinate2D CENTER_RIGHT = new Coordinate2D(INDEX_CENTER, INDEX_RIGHT);

    public static final Coordinate2D BOTTOM_LEFT = new Coordinate2D(INDEX_BOTTOM, INDEX_LEFT);
    public static final Coordinate2D BOTTOM_CENTER = new Coordinate2D(INDEX_BOTTOM, INDEX_CENTER);
    public static final Coordinate2D BOTTOM_RIGHT = new Coordinate2D(INDEX_BOTTOM, INDEX_RIGHT);

    private Coordinate2D position;
    private final byte[][] grid;
    private Orientation orientation;
    private boolean initialized;
    protected Tetramino() {
        this.grid = new byte[WIDTH][HEIGHT];
        this.orientation = Orientation.UP;
        this.position = new Coordinate2D(0, 0);
        this.initialized = false;
    }

    protected void incDroplet(Coordinate2D position) {
        this.grid[position.x()][position.y()]++;
    }

    protected void incDroplet(List<Coordinate2D> positions) {
        positions.forEach(this::incDroplet);
    }

    protected void decDroplet(Coordinate2D position) {
        if (this.grid[position.x()][position.y()] > 0) {
            this.grid[position.x()][position.y()]--;
        }
    }

    protected void decDroplet(List<Coordinate2D> positions) {
        positions.forEach(this::decDroplet);
    }

    protected void moveDroplet(Movement movement) {
        var source = movement.source();
        var destination = movement.destination();
        if (getCell(source) > 0) {
            decDroplet(source);
            incDroplet(destination);
        }
    }

    protected void moveDroplet(List<Movement> moves) {
        moves.forEach(this::moveDroplet);
    }

    protected abstract Stream<Action> internalInitializationSequence();
    protected abstract Stream<Action> internalRotationSequence();

    public List<Action> getInitializationSequence() {
        if (this.initialized) {
            return List.of();
        }
        return Stream.concat(
                internalInitializationSequence(),
                Stream.of(() -> this.initialized = true)
        ).toList();
    }
    public List<Action> getRotationSequence() {
        return Stream.concat(
                internalRotationSequence(),
                Stream.of(this::nextOrientation)
        ).toList();
    }

    @Override
    public Coordinate2D getPosition() {
        return new Coordinate2D(this.position);
    }
    @Override
    public Coordinate2D move(Orientation direction) {
        this.position = this.position.move(direction).destination();
        return this.getPosition();
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    protected void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    protected void nextOrientation() {
        this.setOrientation(this.orientation.next());
    }

    public byte getCell(Coordinate2D position) {
        return this.grid[position.x()][position.y()];
    }

//    class T extends Tetramino {}
//    class J extends Tetramino {}
//    class O extends Tetramino {}
//    class S extends Tetramino {}
//    class Z extends Tetramino {}

    /**
     * It will be a 3x1 to fit inside the grid
     */
    static class I extends Tetramino {

        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(() -> incDroplet(List.of(
                    TOP_CENTER,
                    CENTER_CENTER,
                    BOTTOM_CENTER
            )));
        }

        @Override
        protected Stream<Action> internalRotationSequence(){
            return switch (this.getOrientation()) {
                case UP, DOWN -> Stream.of(
                        () -> this.moveDroplet(List.of(
                                    TOP_CENTER.moveDown(),
                                    BOTTOM_CENTER.moveUp()
                            )),
                        () -> this.moveDroplet(List.of(
                                    CENTER_CENTER.moveLeft(),
                                    CENTER_CENTER.moveRight()
                            ))
                );
                case RIGHT, LEFT -> Stream.of(
                        () -> this.moveDroplet(List.of(
                                CENTER_LEFT.moveRight(),
                                CENTER_RIGHT.moveLeft()
                        )),
                        () -> this.moveDroplet(List.of(
                                CENTER_CENTER.moveUp(),
                                CENTER_CENTER.moveDown()
                            ))
                );
            };
        }
    }

    static class L extends Tetramino {

        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(
                () -> incDroplet(List.of(
                    TOP_CENTER,
                    CENTER_CENTER,
                    BOTTOM_CENTER
                )),
                () -> incDroplet(BOTTOM_RIGHT)
            );
        }

        @Override
        protected Stream<Action> internalRotationSequence() {
            return switch (this.getOrientation()) {
                case UP -> Stream.of(
                        () -> this.moveDroplet(List.of(
                                BOTTOM_RIGHT.moveUp(),
                                BOTTOM_CENTER.moveLeft(),
                                TOP_CENTER.moveDown()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveLeft())
                );
                case RIGHT -> Stream.of(
                        () -> this.moveDroplet(List.of(
                                CENTER_LEFT.moveUp(),
                                BOTTOM_LEFT.moveRight(),
                                CENTER_RIGHT.moveLeft()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveUp())
                );
                case DOWN -> Stream.of(
                        () -> this.moveDroplet(List.of(
                                TOP_LEFT.moveDown(),
                                TOP_CENTER.moveRight(),
                                BOTTOM_CENTER.moveUp()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveRight())
                );
                case LEFT -> Stream.of(
                        () -> this.moveDroplet(List.of(
                                TOP_RIGHT.moveLeft(),
                                CENTER_RIGHT.moveDown(),
                                CENTER_LEFT.moveRight()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveDown())
                );
            };
        }
    }

    public static Tetramino createI() {
        return new I();
    }

    public static Tetramino createL() {
        return new L();
    }

    private static final List<Supplier<Tetramino>> PRODUCERS = List.of(
            Tetramino::createI,
            Tetramino::createL
    );
    public static Tetramino createRandom() {
        var rnd = new Random();
        return PRODUCERS.get(rnd.nextInt(PRODUCERS.size())).get();
    }

}
