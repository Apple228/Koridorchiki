package server_data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Grid implements Serializable {
    private int gridSize;   
    private List<List<Point>> points;
    private int numLine = 0;
    private int maxNumLine;
        
    public Grid(int _gridSize) {
        gridSize = _gridSize;
        maxNumLine = 2 * gridSize * (gridSize - 1);
        pointsInit();
        adjacentPointsInit();
    }
    
    private void pointsInit() {
        points = new ArrayList<>();
        for (int i = 0; i < gridSize; ++i) {
            List<Point> row = new ArrayList<>();
            for (int j = 0; j < gridSize; ++j) {
                Point p = new Point(i, j);
                row.add(p);
            }
            points.add(row);
        }
    }
    
    private void adjacentPointsInit() {
        for (int i = 0; i < gridSize; ++i) {
            for (int j = 0; j < gridSize - 1; ++j) {
                Point a = points.get(i).get(j);
                Point b = points.get(i).get(j+1);
                
                a.getAdjacentPoints().add(b);
                b.getAdjacentPoints().add(a);                
            }
        }
        
        for (int j = 0; j < gridSize; ++j) {
            for (int i = 0; i < gridSize - 1; ++i) {
                Point a = points.get(i).get(j);
                Point b = points.get(i+1).get(j);
                
                a.getAdjacentPoints().add(b);
                b.getAdjacentPoints().add(a);
            }
        }
    }
    
    public boolean isFinished() {
        return numLine == maxNumLine;
    }
    
    public List<List<Point>> getPoints() {
        return points;
    }
    
    public int checkSquare(Point a, Point b, int clientID) {
        int square = 0;
        if (isHorizontal(a, b)) {
            if (a.x >= 1 & b.x >= 1)
                if (a.isConnectedStatus(points.get(a.x - 1).get(a.y), clientID) &&  
                    b.isConnectedStatus(points.get(b.x - 1).get(b.y), clientID) &&
                    points.get(a.x - 1).get(a.y).isConnectedStatus(points.get(b.x - 1).get(b.y), clientID))
                        square++;
            if (a.x < gridSize - 1 & b.x < gridSize - 1)
                if (a.isConnectedStatus(points.get(a.x + 1).get(a.y), clientID) &&  
                    b.isConnectedStatus(points.get(b.x + 1).get(b.y), clientID) &&
                    points.get(a.x + 1).get(a.y).isConnectedStatus(points.get(b.x + 1).get(b.y), clientID))
                        square++;
        } else {
            if (a.y >= 1 & b.y >= 1)
                if (a.isConnectedStatus(points.get(a.x).get(a.y - 1), clientID) &&  
                    b.isConnectedStatus(points.get(b.x).get(b.y - 1), clientID) &&
                    points.get(a.x).get(a.y - 1).isConnectedStatus(points.get(b.x).get(b.y - 1), clientID))
                        square++;
            if (a.y < gridSize - 1 & b.y < gridSize - 1)
                if (a.isConnectedStatus(points.get(a.x).get(a.y + 1), clientID) &&  
                    b.isConnectedStatus(points.get(b.x).get(b.y + 1), clientID) &&
                    points.get(a.x).get(a.y + 1).isConnectedStatus(points.get(b.x).get(b.y + 1), clientID))
                        square++;
        }
        return square;   
    }
    
    public boolean isHorizontal(Point a, Point b) {
        return a.x == b.x;
    }
    
    public void addNumLine() {
        numLine++;
    }
}
