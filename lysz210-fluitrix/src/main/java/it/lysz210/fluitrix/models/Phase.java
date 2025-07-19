package it.lysz210.fluitrix.models;

import java.util.HashSet;
import java.util.Set;

public enum Phase {
    INITIALIZATION(GameCommand.PROCESS_STEP),
    INIT_TETRAMINO(GameCommand.PROCESS_STEP),
    TETRAMINO_INPUT(GameCommand.ROTATE, GameCommand.MOVE_LEFT, GameCommand.MOVE_RIGHT, GameCommand.DROP_DOWN),
    CLEAR_LINES(GameCommand.PROCESS_STEP),
    CHECK(GameCommand.PROCESS_STEP),
    ACTION(GameCommand.PROCESS_STEP),
    GAME_OVER(GameCommand.RESET);

    final Set<GameCommand> acceptedCommands;

    Phase(GameCommand... acceptedCommands) {
        this.acceptedCommands = new HashSet<>();
        for (GameCommand command : acceptedCommands) {
            this.acceptedCommands.add(command);
        }
    }

    public boolean accept(GameCommand command) {
        return acceptedCommands.contains(command);
    }
}
