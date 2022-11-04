package client_data.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

public class UIScore extends JComponent {
    public static final int FONT_SIZE = 30; 
    public int clientScore = 0;
    public int opponentScore = 0;

    public UIScore () {
        super();
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setFont(new Font("MyFont", Font.BOLD, FONT_SIZE));
        int x, y;

        x = y = UI.OFFSET;
        g.setColor(Color.GREEN);
        g.drawString("You: " + clientScore, x, y);
        
        x = UI.GAME_FIELD_SIZE_X - FONT_SIZE * 5;
        y = UI.OFFSET;
        g.setColor(Color.MAGENTA);
        g.drawString("Rival: " + opponentScore, x, y); 
    }
}

