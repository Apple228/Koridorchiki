package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.Serializable;
import java.util.ArrayList;

public class Point implements Serializable {
    public int x;
    public int y;
    private final List<Point> adjacentPoints;
    private final Map<Point, Integer> connectedPoints;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        adjacentPoints = new ArrayList<>(4);
        connectedPoints = new HashMap<>(4);
    }
    
    public List<Point> getAdjacentPoints() {
        return adjacentPoints;
    }
    
    public Map<Point, Integer> getConnectedPoints() {
        return connectedPoints;
    }
    public boolean isConnected(Point p)
    {
        for (Point i : connectedPoints.keySet())
            if (i.eq(p))
                return true;
        return false;
    }
    public boolean eq(Point p)
    {
        if (x == p.x && y == p.y)
            return true;
        else
            return false;
    }
    
    public boolean isBeside(Point p)
    {
        for (Point i : adjacentPoints)
            if (i.eq(p))
                return true;
        return false;
    }
    

    

}
