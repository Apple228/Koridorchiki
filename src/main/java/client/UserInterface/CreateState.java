package client.UserInterface;

import java.awt.Color;

import static java.awt.Color.*;

public enum CreateState {
    PLAYER1(green),
    PLAYER2(orange),
    UNUSED_POINT(gray),

    MISSED_POINT(red),
    UNUSED_LINE(black),

    CHOOSING_FIRST_PLAYER(cyan);
    private final Color color;
    public Color getColor() {
        return color;
    }


    CreateState(Color color) {
        this.color = color;
    }


}
