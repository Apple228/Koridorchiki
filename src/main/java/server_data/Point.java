package server_data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Point implements Serializable {
    public int x, y;
    private static int maxAdjacentPoints = 4;
    private List<Point> adjacentPoints;
    private Map<Point, Integer> connectedPoints;

    public Point(int _x, int _y) {
        x = _x;
        y = _y;
        adjacentPoints = new ArrayList<>(maxAdjacentPoints);
        connectedPoints = new HashMap<>(maxAdjacentPoints);
    }
    
    public List<Point> getAdjacentPoints() {
        return adjacentPoints;
    }
    
    public Map<Point, Integer> getConnectedPoints() {
        return connectedPoints;
    }
    
    public boolean eq(Point p) {
        return x == p.x && y == p.y;
    }
    
    public boolean isAdjacent(Point p) {
        for (Point _p : adjacentPoints)
            if (_p.eq(p))
                return true;
        return false;
    }
    
    public boolean isConnected(Point p) {
        for (Point _p : connectedPoints.keySet())
            if (_p.eq(p))
                return true;
        return false;
    }
    
    public boolean isConnectedStatus(Point p, int clientID) {
        for (Point _p : connectedPoints.keySet())
            if (_p.eq(p) && connectedPoints.get(_p) == clientID)
                return true;
        return false;
    }
}
