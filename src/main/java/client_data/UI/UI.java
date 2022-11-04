package client_data.UI;

import rmi.Server;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;

    public class UI extends JFrame {
        public static int GAME_FIELD_SIZE_X;
        public static int GAME_FIELD_SIZE_Y;
        public static final int OFFSET = 50;

        public static UIGrid grid;
        public static UIScore score;

        public UI(int clientID) {
            super("Коридорчики " + clientID);

            int gridSize = grid.DELTA * (Server.gridSize - 1);
            GAME_FIELD_SIZE_X = gridSize + 2 * OFFSET;
            GAME_FIELD_SIZE_Y = gridSize + 3 * OFFSET;

            getContentPane().setBackground(Color.DARK_GRAY); 
            setBounds(1000 + (clientID  % 2) * 320,  100, GAME_FIELD_SIZE_X, GAME_FIELD_SIZE_Y);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
            //setLocationRelativeTo(null);

            grid = new UIGrid(GAME_FIELD_SIZE_X);
            getContentPane().add(grid);

            score = new UIScore();
            getContentPane().add(score, BorderLayout.CENTER);
        }
    }