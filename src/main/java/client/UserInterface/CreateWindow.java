package client.UserInterface;

import java.awt.Color;
import javax.swing.JFrame;

import rmi.Server;
import java.awt.BorderLayout;


    public class CreateWindow extends JFrame {
        public static final int OFFSET = 50;

        public static CreateGrid grid;


        public CreateWindow(String title, int clientID) {
            super(title + clientID);

            int gridSize = CreateGrid.DELTA * (Server.fieldSize - 1);


            getContentPane().setBackground(Color.WHITE);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setBounds(1000 + (clientID  % 2) * 320,  100, gridSize + 2 * OFFSET,  gridSize + 3 * OFFSET);

            setLayout(new BorderLayout());

            grid = new CreateGrid(gridSize + 2 * OFFSET);
            getContentPane().add(grid);

        }
    }