package it.lysz210.fluitrix.models;

import java.awt.event.KeyEvent;
import java.util.Optional;

public enum GameCommand {
    DROP_DOWN('⇊'),
    ROTATE('↻'),
    MOVE_RIGHT('→'),
    MOVE_LEFT('←');

    public final char symbol;
    GameCommand(char symbol) {
        this.symbol = symbol;
    }

    public Optional<GameCommand> fromKey(int key) {
        return Optional.ofNullable(switch (key) {
            case KeyEvent.VK_UP, 'w', 'W' -> ROTATE;
            case KeyEvent.VK_DOWN, 's', 'S', ' ', '\n' -> DROP_DOWN;
            case KeyEvent.VK_RIGHT, 'd', 'D' -> MOVE_RIGHT;
            case KeyEvent.VK_LEFT, 'a', 'A' -> MOVE_LEFT;
            default -> null;
        });
    }
}
