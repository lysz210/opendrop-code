package it.lysz210.fluitrix.models;

import java.util.List;

public abstract class Tetramino implements Grid {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    private final DropletType[][] grid;
    private Orientation orientation;
    protected Tetramino() {
        this.grid = new DropletType[WIDTH][HEIGHT];
        this.orientation = Orientation.UP;
    }

    protected void add(Coordinate2D position) {
        this.grid[position.x()][position.y()] = DropletType.PIECE;
    }

    protected void add(List<Coordinate2D> positions) {
        positions.forEach(this::add);
    }

    protected void move(Movement move) {
        var source = move.source();
        var destination = move.destination();
        this.grid[destination.x()][destination.y()] = this.grid[source.x()][source.y()];
        this.grid[source.x()][source.y()] = null;
    }

    protected void delete(Coordinate2D position) {
        this.grid[position.x()][position.y()] = null;
    }

    protected void delete(List<Coordinate2D> positions) {
        positions.forEach(this::delete);
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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < WIDTH; i++) {
            for (DropletType droplet : this.grid[i]) {
                sb.append(droplet == null ? '_' : droplet.value);
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
            add(List.of(
                    new Coordinate2D(0, 1),
                    new Coordinate2D(1, 1),
                    new Coordinate2D(2, 1)
            ));
        }
        @Override
        public List<Action> getRotationSequence(){
            return switch (this.getOrientation()) {
                case UP, DOWN -> List.of(
                        () -> this.delete(List.of(
                                    new Coordinate2D(0, 1),
                                    new Coordinate2D(2, 1)
                            )),
                        () -> {
                            this.add(List.of(
                                    new Coordinate2D(1, 0),
                                    new Coordinate2D(1, 2)
                            ));
                            this.nextOrientation();
                        }
                );
                case RIGHT, LEFT -> List.of(
                        () -> this.delete(List.of(
                                new Coordinate2D(1, 0),
                                new Coordinate2D(1, 2)
                        )),
                        () -> {
                            this.add(List.of(
                                    new Coordinate2D(0, 1),
                                    new Coordinate2D(2, 1)
                            ));
                            this.nextOrientation();
                        }
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
