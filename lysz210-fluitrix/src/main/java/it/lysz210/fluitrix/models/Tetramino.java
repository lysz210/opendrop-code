package it.lysz210.fluitrix.models;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
        Coordinate2D source = movement.source();
        Coordinate2D destination = movement.destination();
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
            return Arrays.asList();
        }
        return Stream.concat(
                internalInitializationSequence(),
                Stream.of(() -> this.initialized = true)
        ).collect(Collectors.toList());
    }
    public List<Action> getRotationSequence() {
        return Stream.concat(
                internalRotationSequence(),
                Stream.of(this::nextOrientation)
        ).collect(Collectors.toList());
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

    static class T extends Tetramino {

        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(
                    () -> incDroplet(Arrays.asList(
                            TOP_CENTER,
                            CENTER_CENTER,
                            BOTTOM_CENTER,
                            TOP_LEFT
                    )),
                    () -> moveDroplet(Arrays.asList(
                            TOP_LEFT.moveDown(),
                            TOP_CENTER.moveDown()
                    )),
                    () -> moveDroplet(CENTER_CENTER.moveRight())
            );
        }

        @Override
        protected Stream<Action> internalRotationSequence() {
            switch (getOrientation()) {
                case UP:
                    return Stream.of(
                        () -> moveDroplet(CENTER_RIGHT.moveLeft()),
                        () -> moveDroplet(CENTER_CENTER.moveUp())
                );
                case RIGHT:
                    return Stream.of(
                        () -> moveDroplet(BOTTOM_CENTER.moveUp()),
                        () -> moveDroplet(CENTER_CENTER.moveRight())
                );
                case DOWN:
                    return Stream.of(
                        () -> moveDroplet(CENTER_LEFT.moveRight()),
                        () -> moveDroplet(CENTER_CENTER.moveDown())
                );
                case LEFT:
                    return Stream.of(
                        () -> moveDroplet(TOP_CENTER.moveDown()),
                        () -> moveDroplet(CENTER_CENTER.moveLeft())
                );
                default:
                    throw new IllegalStateException("Unexpected value: " + getOrientation());
            }
        }
    }
    static class J extends Tetramino {

        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(
                    () -> incDroplet(Arrays.asList(
                            TOP_CENTER,
                            CENTER_CENTER,
                            BOTTOM_CENTER
                    )),
                    () -> incDroplet(BOTTOM_LEFT)
            );
        }

        @Override
        protected Stream<Action> internalRotationSequence() {
            switch (getOrientation()) {
                case UP:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                BOTTOM_LEFT.moveUp(),
                                TOP_CENTER.moveLeft(),
                                BOTTOM_CENTER.moveUp()
                        )),
                        () -> moveDroplet(CENTER_CENTER.moveRight())
                );
                case RIGHT:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                TOP_LEFT.moveRight(),
                                CENTER_RIGHT.moveUp(),
                                CENTER_LEFT.moveRight()
                        )),
                        () -> moveDroplet(CENTER_CENTER.moveDown())
                );
                case DOWN:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                BOTTOM_CENTER.moveRight(),
                                TOP_RIGHT.moveDown(),
                                TOP_CENTER.moveDown()
                        )),
                        () -> moveDroplet(CENTER_CENTER.moveLeft())
                );
                case LEFT:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                CENTER_LEFT.moveDown(),
                                BOTTOM_RIGHT.moveLeft(),
                                CENTER_RIGHT.moveLeft()
                        )),
                        () -> moveDroplet(CENTER_CENTER.moveUp())
                );
                default:
                    throw new IllegalStateException("Unexpected value: " + getOrientation());
            }
        }
    }
    static class O extends Tetramino {
        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(
                    () -> incDroplet(Arrays.asList(
                            TOP_CENTER, TOP_LEFT
                    )),
                    () -> incDroplet(Arrays.asList(
                            CENTER_CENTER, CENTER_LEFT
                    )),
                    () -> moveDroplet(Arrays.asList(
                            TOP_CENTER.moveDown(),
                            TOP_LEFT.moveDown()
                    )),
                    () -> moveDroplet(Arrays.asList(
                            CENTER_CENTER.moveDown(),
                            CENTER_LEFT.moveDown()
                    ))
            );
        }

        @Override
        protected Stream<Action> internalRotationSequence() {
            return Stream.empty();
        }
    }
    static class Z extends Tetramino {
        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(
                    () -> incDroplet(Arrays.asList(
                            TOP_CENTER,
                            CENTER_CENTER,
                            BOTTOM_CENTER,
                            TOP_LEFT
                    )),
                    () -> moveDroplet(Arrays.asList(
                            TOP_LEFT.moveDown(),
                            TOP_CENTER.moveDown(),
                            CENTER_CENTER.moveDown(),
                            BOTTOM_CENTER.moveRight()
                    ))
            );
        }

        @Override
        protected Stream<Action> internalRotationSequence() {
            switch (getOrientation()) {
                case UP:
                case DOWN:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                BOTTOM_CENTER.moveUp(),
                                BOTTOM_RIGHT.moveLeft()
                        )),
                        () -> moveDroplet(Arrays.asList(
                                BOTTOM_CENTER.moveLeft(),
                                CENTER_CENTER.moveUp()
                        ))
                );
                case RIGHT:
                case LEFT:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                TOP_CENTER.moveDown(),
                                BOTTOM_LEFT.moveRight()
                        )),
                        () -> moveDroplet(Arrays.asList(
                                BOTTOM_CENTER.moveRight(),
                                CENTER_CENTER.moveDown()
                        ))
                );
                default:
                    throw new IllegalStateException("Unexpected value: " + getOrientation());
            }
        }
    }
    static class S extends Tetramino {
        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(
                    () -> incDroplet(Arrays.asList(
                            TOP_CENTER,
                            CENTER_CENTER,
                            BOTTOM_CENTER,
                            TOP_RIGHT
                    )),
                    () -> moveDroplet(Arrays.asList(
                            TOP_RIGHT.moveDown(),
                            TOP_CENTER.moveDown(),
                            CENTER_CENTER.moveDown(),
                            BOTTOM_CENTER.moveLeft()
                    ))
            );
        }

        @Override
        protected Stream<Action> internalRotationSequence() {
            switch (getOrientation()) {
                case UP:
                case DOWN:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                BOTTOM_LEFT.moveUp(),
                                CENTER_RIGHT.moveLeft()
                        )),
                        () -> moveDroplet(Arrays.asList(
                                CENTER_LEFT.moveUp(),
                                CENTER_CENTER.moveLeft()
                        ))
                );
                case RIGHT:
                case LEFT:
                    return Stream.of(
                        () -> moveDroplet(Arrays.asList(
                                TOP_LEFT.moveDown(),
                                CENTER_LEFT.moveRight()
                        )),
                        () -> moveDroplet(Arrays.asList(
                                CENTER_LEFT.moveDown(),
                                CENTER_CENTER.moveRight()
                        ))
                );
                default:
                    throw new IllegalStateException("Unexpected value: " + getOrientation());
            }
        }
    }

    /**
     * It will be a 3x1 to fit inside the grid
     */
    static class I extends Tetramino {

        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(() -> incDroplet(Arrays.asList(
                    TOP_CENTER,
                    CENTER_CENTER,
                    BOTTOM_CENTER
            )));
        }

        @Override
        protected Stream<Action> internalRotationSequence(){
            switch (this.getOrientation()) {
                case UP:
                case DOWN:
                    return Stream.of(
                        () -> this.moveDroplet(Arrays.asList(
                                    TOP_CENTER.moveDown(),
                                    BOTTOM_CENTER.moveUp()
                            )),
                        () -> this.moveDroplet(Arrays.asList(
                                    CENTER_CENTER.moveLeft(),
                                    CENTER_CENTER.moveRight()
                            ))
                );
                case RIGHT:
                case LEFT:
                    return Stream.of(
                        () -> this.moveDroplet(Arrays.asList(
                                CENTER_LEFT.moveRight(),
                                CENTER_RIGHT.moveLeft()
                        )),
                        () -> this.moveDroplet(Arrays.asList(
                                CENTER_CENTER.moveUp(),
                                CENTER_CENTER.moveDown()
                            ))
                );
                default:
                    throw new IllegalStateException("Unexpected value: " + getOrientation());
            }
        }
    }

    static class L extends Tetramino {

        @Override
        protected Stream<Action> internalInitializationSequence() {
            return Stream.of(
                () -> incDroplet(Arrays.asList(
                    TOP_CENTER,
                    CENTER_CENTER,
                    BOTTOM_CENTER
                )),
                () -> incDroplet(BOTTOM_RIGHT)
            );
        }

        @Override
        protected Stream<Action> internalRotationSequence() {
            switch (this.getOrientation()) {
                case UP:
                    return Stream.of(
                        () -> this.moveDroplet(Arrays.asList(
                                BOTTOM_RIGHT.moveUp(),
                                BOTTOM_CENTER.moveLeft(),
                                TOP_CENTER.moveDown()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveLeft())
                );
                case RIGHT:
                    return Stream.of(
                        () -> this.moveDroplet(Arrays.asList(
                                CENTER_LEFT.moveUp(),
                                BOTTOM_LEFT.moveRight(),
                                CENTER_RIGHT.moveLeft()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveUp())
                );
                case DOWN:
                    return Stream.of(
                        () -> this.moveDroplet(Arrays.asList(
                                TOP_LEFT.moveDown(),
                                TOP_CENTER.moveRight(),
                                BOTTOM_CENTER.moveUp()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveRight())
                );
                case LEFT:
                    return Stream.of(
                        () -> this.moveDroplet(Arrays.asList(
                                TOP_RIGHT.moveLeft(),
                                CENTER_RIGHT.moveDown(),
                                CENTER_LEFT.moveRight()
                        )),
                        () -> this.moveDroplet(CENTER_CENTER.moveDown())
                );
                default:
                    throw new IllegalStateException("Unexpected value: " + getOrientation());
            }
        }
    }

    public static Tetramino createI() {
        return new I();
    }

    public static Tetramino createL() {
        return new L();
    }
    public static Tetramino createJ() {
        return new J();
    }

    public static Tetramino createT() {
        return new T();
    }

    public static Tetramino createO() {
        return new O();
    }

    public static Tetramino createZ() {
        return new Z();
    }

    public static Tetramino createS() {
        return new S();
    }

    private static final List<Supplier<Tetramino>> PRODUCERS = Arrays.asList(
            Tetramino::createI,
            Tetramino::createL,
            Tetramino::createJ,
            Tetramino::createT,
            Tetramino::createO,
            Tetramino::createZ,
            Tetramino::createS
    );
    public static Tetramino createRandom() {
        Random rnd = new Random();
        return PRODUCERS.get(rnd.nextInt(PRODUCERS.size())).get();
    }

}
