package it.lysz210;

import it.lysz210.fluitrix.models.Action;
import it.lysz210.fluitrix.models.Tetramino;
import it.lysz210.fluitrix.utils.GridToStringMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class MainForTetramino {
    private static final Logger logger = LoggerFactory.getLogger(MainForTetramino.class);

    public static void main(String[] args) {
        var toString = new GridToStringMapper();
        var tetramino = Tetramino.createS();
        Consumer<Action> executeAndPrint = action -> {
            action.execute();
            logger.info(
                    "\nDir: {}{}",
                    tetramino.getOrientation().symbol,
                    toString.apply(tetramino)
            );
        };
        logger.info("Init");
        tetramino.getInitializationSequence().forEach(executeAndPrint);

        logger.info("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
        logger.info("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
        logger.info("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
        logger.info("rotate");
        tetramino.getRotationSequence().forEach(executeAndPrint);
    }
}
