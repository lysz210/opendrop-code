package it.lysz210;

import it.lysz210.fluitrix.models.Action;
import it.lysz210.fluitrix.models.Board;
import it.lysz210.fluitrix.models.Orientation;
import it.lysz210.fluitrix.models.Tetramino;
import it.lysz210.fluitrix.utils.GridToStringMapper;

public class MainForBoard {

    public static void main(String[] args) {
        final var WIDTH = 14;
        final var HEIGHT = 8;
        final var toString = new GridToStringMapper();

        final var board = new Board(WIDTH, HEIGHT);
        var tetramino = Tetramino.createI();
        tetramino.getInitializationSequence().forEach(Action::execute);

        System.out.println(toString.apply(tetramino));

        System.out.println(toString.apply(board));

        for (int i = 0; i < 11; i++) {
            tetramino.move(Orientation.DOWN);
        }
        board.merge(tetramino);
        tetramino = Tetramino.createI();

        tetramino.getInitializationSequence().forEach(Action::execute);
//        tetramino.getRotationSequence().forEach(Action::execute);
        for (int i = 0; i < 8; i++) {
            tetramino.move(Orientation.DOWN);
        }
//        tetramino.move(Orientation.LEFT);

        boolean isTouching = board.isTouchingFloor(tetramino);
        board.merge(tetramino);
        System.out.println(toString.apply(board));
        System.out.println(isTouching ? "Touching floor" : "Not Touching");

        for (int i = 0; i < 4; i++) {
            tetramino = Tetramino.createI();
            tetramino.getInitializationSequence().forEach(Action::execute);
            do {
                tetramino.move(Orientation.DOWN);
            } while (!board.isTouchingFloor(tetramino));
            board.merge(tetramino);
        }

        System.out.println(toString.apply(board));
    }
}
