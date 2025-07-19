package it.lysz210.fluitrix.models;

import it.lysz210.fluitrix.utils.GridMerger;
import it.lysz210.fluitrix.utils.GridToElectrodsMapper;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {

    public enum Phase {
        INITIALIZATION(GameCommand.PROCESS_STEP),
        INIT_TETRAMINO(GameCommand.PROCESS_STEP),
        TETRAMINO_INPUT(GameCommand.ROTATE, GameCommand.MOVE_LEFT, GameCommand.MOVE_RIGHT, GameCommand.DROP_DOWN),
        CLEAR_LINES(GameCommand.PROCESS_STEP),
        CHECK(GameCommand.PROCESS_STEP),
        ACTION(GameCommand.PROCESS_STEP),
        GAME_OVER(GameCommand.RESET);

        public final Set<GameCommand> acceptedCommands;

        Phase(GameCommand ...acceptedCommands){
            Stream.Builder<GameCommand> builder = Stream.builder();
            for (GameCommand command: acceptedCommands) {
                builder.add(command);
            }
            this.acceptedCommands = builder.build().collect(Collectors.toSet());
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
            if (currentPhase == Phase.GAME_OVER) {
                System.out.println("==== Game over ====");
            }
            return;
        }
        switch (command) {
            case PROCESS_STEP:
                process();
                break;
            case ROTATE:
            case DROP_DOWN:
            case MOVE_LEFT:
            case MOVE_RIGHT:
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
                tetramino = null;
                currentPhase = Phase.CLEAR_LINES;
                return;
            }
            actionsQueue.add(() -> tetramino.move(Orientation.DOWN));
            actionsQueue.add(this);
        }
    }

    private void processInput(GameCommand command) {
        System.out.printf("Processing input command %s\n", command);
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
            case INITIALIZATION:
                initTetramino();
                return;
            case CLEAR_LINES:
                clearLines();
                return;
            case CHECK:
                check();
                return;
            default:
                // continue and check for actions
        }
        if (actionsQueue.isEmpty()) {
            return;
        }
        Action action = actionsQueue.remove();
        action.execute();
    }

    private void clearLines() {
        actionsQueue.addAll(board.clearLines());
        actionsQueue.add(() -> currentPhase = Phase.CHECK);
        currentPhase = Phase.ACTION;
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
