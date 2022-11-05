package client.UserInterface;

import rmi.Server;
import javax.swing.JComponent;


import java.util.ArrayList;
import java.util.List;


public class CreateGrid extends JComponent {
    public static int DELTA = 100;
    private static int fieldSize = Server.fieldSize;
    private List<List<CreatePoint>> points_list;
    
    public CreateGrid(int gridSize) {
        super();

        setSize(gridSize, gridSize);
        
        points_list = new ArrayList<>(fieldSize);

        int x = CreateWindow.OFFSET;
        int y = CreateWindow.OFFSET;

        for(int i = 0; i < fieldSize; i++) {
            List<CreatePoint> row = new ArrayList<>(fieldSize);

            for(int j = 0; j < fieldSize; j++) {
                CreatePoint uiPoint = new CreatePoint(x, y, CreatePoint.POINT_RADIUS);
                uiPoint.setIndexVert(i);
                uiPoint.setIndexHor(j);

                row.add(uiPoint);
                add(uiPoint);

                x += DELTA;
            }

            points_list.add(row);
            x = CreateWindow.OFFSET;
            y += DELTA;
        }
        for(int i = 0; i < fieldSize; i++) {
            for(int j = 0; j < fieldSize; j++) {
                if(j < fieldSize - 1) {
                    CreateLine lineHorizontal = new CreateLine(points_list.get(i).get(j), points_list.get(i).get(j + 1));
                    add(lineHorizontal);
                    points_list.get(i).get(j + 1).getConnections().add(lineHorizontal);
                    points_list.get(i).get(j).getConnections().add(lineHorizontal);

                }

                if(i < fieldSize - 1) {
                    CreateLine lineVertical = new CreateLine(points_list.get(i).get(j), points_list.get(i + 1).get(j));
                    add(lineVertical);

                    points_list.get(i + 1).get(j).getConnections().add(lineVertical);
                    points_list.get(i).get(j).getConnections().add(lineVertical);

                }
            }
        }
    }

    public List<List<CreatePoint>> getPoints_list() { return points_list; }


}

