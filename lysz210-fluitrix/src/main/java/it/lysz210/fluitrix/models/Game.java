package it.lysz210.fluitrix.models;

import it.lysz210.fluitrix.utils.GridMerger;
import it.lysz210.fluitrix.utils.GridToElectrodsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    public enum Phase {
        INITIALIZATION(Set.of(GameCommand.PROCESS_STEP)),
        INIT_TETRAMINO(Set.of(GameCommand.PROCESS_STEP)),
        TETRAMINO_INPUT(Set.of(GameCommand.ROTATE, GameCommand.MOVE_LEFT, GameCommand.MOVE_RIGHT, GameCommand.DROP_DOWN)),
        CHECK(Set.of(GameCommand.PROCESS_STEP)),
        ACTION(Set.of(GameCommand.PROCESS_STEP)),
        GAME_OVER(Set.of(GameCommand.RESET));

        public final Set<GameCommand> acceptedCommands;

        Phase(Set<GameCommand> acceptedCommands){
            this.acceptedCommands = acceptedCommands;
        }

        public boolean accept(GameCommand command){
            return acceptedCommands.contains(command);
        }
    }
    private Board board;
    private Tetramino tetramino;
    private Phase currentPhase;
    public final GridToElectrodsMapper toElectrodsMapper = new GridToElectrodsMapper();
    public final GridMerger gridMerger = new GridMerger();
    private Queue<Action> actionsQueue;
    public Game(int boardWidth, int boardHeight) {
        this.board = new Board(boardWidth, boardHeight);
        this.currentPhase = Phase.INITIALIZATION;
        actionsQueue = new LinkedList<>();
    }

    private void reset() {
        actionsQueue.clear();
        board = new Board(this.board.getWidth(), this.board.getHeight());
        actionsQueue.add(() -> {});
        actionsQueue.add(() -> {});
        actionsQueue.add(() -> {});
        actionsQueue.add(() -> {});
        actionsQueue.add(() -> {});
        actionsQueue.add(() -> currentPhase = Phase.INITIALIZATION);
        currentPhase = Phase.ACTION;
    }

    public boolean[][] electrode() {
        if (tetramino == null) {
            return toElectrodsMapper.apply(board);
        }
        return toElectrodsMapper.apply(gridMerger.apply(board, tetramino));
    }

    public void process(GameCommand command) {
        if (!currentPhase.accept(command)) {
//            LOGGER.warn("Command {} not valid in game phase {}", command, currentPhase);
            return;
        }
        switch (command) {
            case PROCESS_STEP:
                process();
                break;
            case ROTATE, DROP_DOWN, MOVE_LEFT, MOVE_RIGHT:
                processInput(command);
                break;
            case RESET:
                reset();
                break;
        }
    }

    class DropDown implements Action {

        @Override
        public void execute() {
            if (board.isTouchingFloor(tetramino)) {
                board.merge(tetramino);
                currentPhase = Phase.CHECK;
                return;
            }
            actionsQueue.add(() -> tetramino.move(Orientation.DOWN));
            actionsQueue.add(this);
        }
    }

    private void processInput(GameCommand command) {
        LOGGER.info("Processing input command {}", command);
        switch (command) {
            case ROTATE:
                actionsQueue.addAll(tetramino.getRotationSequence());
                actionsQueue.add(() -> this.currentPhase = Phase.TETRAMINO_INPUT);
                break;
            case MOVE_LEFT:
                actionsQueue.add(() -> tetramino.move(Orientation.LEFT));
                actionsQueue.add(() -> this.currentPhase = Phase.TETRAMINO_INPUT);
                break;
            case MOVE_RIGHT:
                actionsQueue.add(() -> tetramino.move(Orientation.RIGHT));
                actionsQueue.add(() -> this.currentPhase = Phase.TETRAMINO_INPUT);
                break;
            case DROP_DOWN:
                actionsQueue.add(new DropDown());
                break;
            default:
                return;
        }
        this.currentPhase = Phase.ACTION;
    }

    private void initTetramino () {
        tetramino = Tetramino.createRandom();
        actionsQueue.addAll(tetramino.getInitializationSequence());
        actionsQueue.add(() -> this.currentPhase = Phase.TETRAMINO_INPUT);
        this.currentPhase = Phase.ACTION;
    }

    private void process() {
        switch (currentPhase) {
            case Phase.INITIALIZATION:
                initTetramino();
                return;
            case Phase.CHECK:
                check();
                return;
            default:
                // continue and check for actions
        }
        if (actionsQueue.isEmpty()) {
            return;
        }
        var action = actionsQueue.remove();
        action.execute();
    }

    private void check() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                if (board.getCell(new Coordinate2D(x, y)) > 0) {
                    this.currentPhase = Phase.GAME_OVER;
                    return;
                }
            }
        }
        // not over yet
        this.initTetramino();
    }
}
