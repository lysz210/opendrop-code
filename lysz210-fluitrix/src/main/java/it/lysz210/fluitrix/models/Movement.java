package it.lysz210.fluitrix.models;

public record Movement(
        Coordinate2D source,
        Coordinate2D destination
) {
    public static Movement of(Coordinate2D source, Orientation direction) {
        var destination = switch (direction) {
            case UP -> new Coordinate2D(source.x() - 1, source.y());
            case DOWN -> new Coordinate2D(source.x() + 1, source.y());
            case LEFT -> new Coordinate2D(source.x(), source.y() - 1);
            case RIGHT -> new Coordinate2D(source.x(), source.y() + 1);
        };
        return new Movement(source, destination);
    }
}
