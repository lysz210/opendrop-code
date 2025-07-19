package it.lysz210;

import it.lysz210.fluitrix.models.Action;
import it.lysz210.fluitrix.models.Board;
import it.lysz210.fluitrix.models.Orientation;
import it.lysz210.fluitrix.models.Tetramino;
import it.lysz210.fluitrix.utils.GridToStringMapper;

import java.util.function.Consumer;

public class MainForBoard {
    static final int WIDTH = 14;
    static final int HEIGHT = 8;

    static final Board board = new Board(WIDTH, HEIGHT);
    static GridToStringMapper toString = new GridToStringMapper();
    static Consumer<Action> axecAndPrint = action -> {
        action.execute();
        System.out.printf("%s\n", toString.apply(board));
    };

    public static void main(String[] args) {

        testClear();

    }

    static void testClear() {
        board.clearLines().forEach(axecAndPrint);
    }

    static void test1() {

        Tetramino tetramino = Tetramino.createI();
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
