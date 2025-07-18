package it.lysz210;

import it.lysz210.fluitrix.models.Action;
import it.lysz210.fluitrix.models.Tetramino;
import it.lysz210.fluitrix.utils.GridToStringMapper;

public class MainForTetramino {

    public static void main(String[] args) {
        var toString = new GridToStringMapper();
        var tetramino = Tetramino.createRandom();
        tetramino.getInitializationSequence().forEach(Action::execute);
        System.out.println("------------");
        System.out.println(toString.apply(tetramino));

        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println("Dir: " + tetramino.getOrientation().symbol);
            System.out.println(toString.apply(tetramino));
        }
        System.out.println("------------");
        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println("Dir: " + tetramino.getOrientation().symbol);
            System.out.println(toString.apply(tetramino));
        }
        System.out.println("------------");
        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println("Dir: " + tetramino.getOrientation().symbol);
            System.out.println(toString.apply(tetramino));
        }
        System.out.println("------------");
        for (Action action : tetramino.getRotationSequence()) {
            System.out.println("------------");
            action.execute();
            System.out.println("Dir: " + tetramino.getOrientation().symbol);
            System.out.println(toString.apply(tetramino));
        }
        System.out.println("------------");
    }
}
