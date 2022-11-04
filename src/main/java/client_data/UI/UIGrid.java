
package client_data.UI;

import java.util.ArrayList;
import java.util.List;
import rmi.Server;
import javax.swing.JComponent;


public class UIGrid extends JComponent {    
    public static int GRID_SIZE; 
    public static final int DELTA = 100;
    private static final int DIM = Server.gridSize;
    private List<List<UIPoint>> points;
    
    public UIGrid(int gridSize) {
        super();
        GRID_SIZE = gridSize;
        setSize(GRID_SIZE, GRID_SIZE);
        
        points = new ArrayList<>(DIM);
        
        setPoints();
        setLines();
    }
    
    public List<List<UIPoint>> getPoints() { return points; }

    private void setPoints() {
        int x = UI.OFFSET;
        int y = UI.OFFSET + UIScore.FONT_SIZE;
        
        for(int i = 0; i < DIM; i++) {
            List<UIPoint> row = new ArrayList<>(DIM);

            for(int j = 0; j < DIM; j++) {
                UIPoint uiPoint = new UIPoint(x, y, UIPoint.POINT_RADIUS);
                uiPoint.setIndexVert(i);
                uiPoint.setIndexHor(j);

                row.add(uiPoint);
                add(uiPoint);

                x += DELTA;
            }

            points.add(row);
            x = UI.OFFSET;
            y += DELTA;
        }
    }

    private void setLines() {
        for(int i = 0; i < DIM; i++) {
            for(int j = 0; j < DIM; j++) {
                if(j < DIM - 1) {
                    UILine lineHorizontal = new UILine(points.get(i).get(j), points.get(i).get(j + 1));
                    add(lineHorizontal);
                   
                    points.get(i).get(j).getConnections().add(lineHorizontal);
                    points.get(i).get(j + 1).getConnections().add(lineHorizontal);
                }

                if(i < DIM - 1) {
                    UILine lineVertical = new UILine(points.get(i).get(j), points.get(i + 1).get(j));
                    add(lineVertical);
                    
                    points.get(i).get(j).getConnections().add(lineVertical);
                    points.get(i + 1).get(j).getConnections().add(lineVertical);
                }
            }
        }
    }
}

