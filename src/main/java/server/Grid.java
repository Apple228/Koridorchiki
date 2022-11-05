package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Grid implements Serializable {
    private final List<List<Point>> points;
    private int numLine = 0;
    private final int maxNumLine;
        
    public Grid(int fieldSize) {
        maxNumLine = 2 * fieldSize * (fieldSize - 1);
        points = new ArrayList<>();
        for (int i = 0; i < fieldSize; ++i) {
            List<Point> row = new ArrayList<>();
            for (int j = 0; j < fieldSize; ++j) {
                Point p = new Point(i, j);
                row.add(p);
            }
            points.add(row);
        }
        for (int i = 0; i < fieldSize; ++i) {
            for (int j = 0; j < fieldSize - 1; ++j) {
                Point startPoint = points.get(i).get(j);
                Point lastPoint = points.get(i).get(j+1);

                startPoint.getAdjacentPoints().add(lastPoint);
                lastPoint.getAdjacentPoints().add(startPoint);
            }
        }

        for (int j = 0; j < fieldSize; ++j) {
            for (int i = 0; i < fieldSize - 1; ++i) {
                Point startPoint = points.get(i).get(j);
                Point lastPoint = points.get(i+1).get(j);

                startPoint.getAdjacentPoints().add(lastPoint);
                lastPoint.getAdjacentPoints().add(startPoint);
            }
        }
    }



    public void addNumLine()
    {
        numLine+=1;
    }



    public boolean isFinished()
    {
        if (numLine == maxNumLine)
            return true;
        else
            return false;
    }
    

    
    public List<List<Point>> getPoints()
    {
        return points;
    }
    

    

}
