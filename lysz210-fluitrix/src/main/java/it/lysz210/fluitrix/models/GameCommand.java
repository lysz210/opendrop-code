package it.lysz210.fluitrix.models;

import java.awt.event.KeyEvent;
import java.util.Optional;

public enum GameCommand {
    DROP_DOWN('⇊'),
    ROTATE('↻'),
    MOVE_RIGHT('→'),
    MOVE_LEFT('←'),
    PROCESS_STEP('▶'),
    RESET('⏮');

    public final char symbol;
    GameCommand(char symbol) {
        this.symbol = symbol;
    }

    public static Optional<GameCommand> fromKey(int key) {
        GameCommand command;
        switch (key) {
            case KeyEvent.VK_UP:
            case 'w':
            case 'W':
                command = ROTATE;
                break;
            case KeyEvent.VK_DOWN:
            case 's':
            case 'S':
            case ' ':
            case '\n':
                command = DROP_DOWN;
                break;
            case KeyEvent.VK_RIGHT:
            case 'd':
            case 'D':
                command = MOVE_RIGHT;
                break;
            case KeyEvent.VK_LEFT:
            case 'a':
            case 'A':
                command = MOVE_LEFT;
                break;
            case 'r':
            case  'R':
                command = RESET;
                break;
            default:
                command = null;
        }

        return Optional.ofNullable(command);
    }
}
