package it.lysz210;

import it.lysz210.fluitrix.models.Action;
import it.lysz210.fluitrix.models.Tetramino;
import it.lysz210.fluitrix.utils.GridToStringMapper;

import java.util.function.Consumer;

public class MainForTetramino {

    public static void main(String[] args) {
        GridToStringMapper toString = new GridToStringMapper();
        Tetramino tetramino = Tetramino.createS();
        Consumer<Action> executeAndPrint = action -> {
            action.execute();
            System.out.printf(
                    "Dir: %s%s\n",
                    tetramino.getOrientation().symbol,
                    toString.apply(tetramino)
            );
        };
        System.out.println("Init");
        tetramino.getInitializationSequence().forEach(executeAndPrint);

        System.out.println("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
        System.out.println("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
        System.out.println("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
        System.out.println("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
    }
}
