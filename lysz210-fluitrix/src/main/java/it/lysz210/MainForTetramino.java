package it.lysz210;

import it.lysz210.fluitrix.models.Action;
import it.lysz210.fluitrix.models.Tetramino;

public class MainForTetramino {

    public static void main(String[] args) {
        var tetramino = Tetramino.createL();
        tetramino.getInitializationSequence().forEach(Action::execute);
        System.out.println("------------");
        System.out.println(tetramino);

        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(tetramino);
        }
        System.out.println("------------");
        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(tetramino);
        }
        System.out.println("------------");
        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(tetramino);
        }
        System.out.println("------------");
        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println(tetramino);
        }
        System.out.println("------------");
    }
}
