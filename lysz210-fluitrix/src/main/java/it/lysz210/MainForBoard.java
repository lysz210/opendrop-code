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
        final var tetramino = Tetramino.createI();
        tetramino.getInitializationSequence().forEach(Action::execute);

        System.out.println(toString.apply(tetramino));

        System.out.println(toString.apply(board));

        tetramino.move(Orientation.DOWN);
        board.merge(tetramino);

        System.out.println(toString.apply(board));
    }
}
