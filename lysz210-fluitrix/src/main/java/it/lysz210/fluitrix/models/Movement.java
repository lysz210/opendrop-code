package it.lysz210.fluitrix.models;

public class Movement {
    private final Coordinate2D source;
    public Coordinate2D source() {
        return this.source;
    }
    private final Coordinate2D destination;
    public  Coordinate2D destination() {
        return this.destination;
    }

    public Movement(Coordinate2D source, Coordinate2D destination) {
        this.source = source;
        this.destination = destination;
    }
    public Movement(Coordinate2D source, Orientation direction) {
        this.source = source;
        switch (direction) {
            case UP:
                destination = new Coordinate2D(source.x() - 1, source.y());
                break;
            case DOWN:
                destination = new Coordinate2D(source.x() + 1, source.y());
                break;
            case LEFT:
                destination = new Coordinate2D(source.x(), source.y() + 1);
                break;
            case RIGHT:
                destination = new Coordinate2D(source.x(), source.y() - 1);
                break;
            default:
                    throw  new IllegalArgumentException("Invalid direction");
        }
    }
}
