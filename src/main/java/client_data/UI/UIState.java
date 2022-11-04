package client_data.UI;

import java.awt.Color;

public enum  UIState {
    NOT_ACTIVE_POINT(Color.GRAY),
    NOT_ACTIVE_LINE(Color.BLACK),
    ACTIVE_FIRST_PLAYER(Color.GREEN),
    ACTIVE_SECOND_PLAYER(Color.MAGENTA),
    CHOOSING_FIRST_PLAYER(Color.CYAN),
    CHOOSING_SECOND_PLAYER(Color.MAGENTA),
    MISSED_POINT(Color.RED);

    private Color color;

    UIState(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
