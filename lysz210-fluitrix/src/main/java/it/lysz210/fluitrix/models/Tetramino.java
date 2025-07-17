package it.lysz210.fluitrix.models;

import java.util.List;

public abstract class Tetramino implements Grid {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    public static final int INDEX_TOP = 0;
    public static final int INDEX_BOTTOM = 2;
    public static final int INDEX_CENTER = 1;
    public static final int INDEX_LEFT = 0;
    public static final int INDEX_RIGHT = 2;

    public static final Coordinate2D TOP_LEFT = new Coordinate2D(INDEX_TOP, INDEX_LEFT);
    public static final Coordinate2D TOP_CENTER = new Coordinate2D(INDEX_TOP, INDEX_CENTER);
    public static final Coordinate2D TOP_RIGHT = new Coordinate2D(INDEX_TOP, INDEX_RIGHT);

    public static final Coordinate2D CENTER_LEFT = new Coordinate2D(INDEX_CENTER, INDEX_LEFT);
    public static final Coordinate2D CENTER_CENTER = new Coordinate2D(INDEX_CENTER, INDEX_CENTER);
    public static final Coordinate2D CENTER_RIGHT = new Coordinate2D(INDEX_CENTER, INDEX_RIGHT);

    public static final Coordinate2D BOTTOM_LEFT = new Coordinate2D(INDEX_BOTTOM, INDEX_LEFT);
    public static final Coordinate2D BOTTOM_CENTER = new Coordinate2D(INDEX_BOTTOM, INDEX_CENTER);
    public static final Coordinate2D BOTTOM_RIGHT = new Coordinate2D(INDEX_BOTTOM, INDEX_RIGHT);

    private final byte[][] grid;
    private Orientation orientation;
    protected Tetramino() {
        this.grid = new byte[WIDTH][HEIGHT];
        this.orientation = Orientation.UP;
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

    public abstract List<Action> getRotationSequence();

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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("dir: ");
        sb.append(this.orientation.symbol);
        sb.append("\n");
        for (int i = 0; i < WIDTH; i++) {
            for (byte droplet : this.grid[i]) {
                sb.append(droplet);
            }
            sb.append('\n');
        }
        return sb.toString();
    }

//    class T extends Tetramino {}
//    class L extends Tetramino {}
//    class J extends Tetramino {}
//    class O extends Tetramino {}
//    class S extends Tetramino {}
//    class Z extends Tetramino {}

    /**
     * It will be a 3x1 to fit inside the grid
     */
    static class I extends Tetramino {
        protected I () {
            super();
            incDroplet(List.of(
                    TOP_CENTER,
                    CENTER_CENTER,
                    BOTTOM_CENTER
            ));
        }
        @Override
        public List<Action> getRotationSequence(){
            return switch (this.getOrientation()) {
                case UP, DOWN -> List.of(
                        () -> this.moveDroplet(List.of(
                                    TOP_CENTER.moveDown(),
                                    BOTTOM_CENTER.moveUp()
                            )),
                        () -> this.moveDroplet(List.of(
                                    CENTER_CENTER.moveLeft(),
                                    CENTER_CENTER.moveRight()
                            )),
                        this::nextOrientation
                );
                case RIGHT, LEFT -> List.of(
                        () -> this.moveDroplet(List.of(
                                CENTER_LEFT.moveRight(),
                                CENTER_RIGHT.moveLeft()
                        )),
                        () -> this.moveDroplet(List.of(
                                CENTER_CENTER.moveUp(),
                                CENTER_CENTER.moveDown()
                            )),
                        this::nextOrientation
                );
            };
        }
    }

    public static Tetramino createI() {
        return new I();
    }

    public static void main(String[] args) {
        var i = createI();
        System.out.println("------------");
        System.out.println(i);

        for (Action action : i.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(i);
        }
        System.out.println("------------");
        for (Action action : i.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(i);
        }
        System.out.println("------------");
        for (Action action : i.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(i);
        }
        System.out.println("------------");
        for (Action action : i.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(i);
        }
        System.out.println("------------");
    }

}
