package client.UserInterface;

import client.PointListener;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class CreatePoint extends JComponent {
    private int x;
    private int y;
    private int indexHor;
    private int indexVert;
    public static int POINT_RADIUS = 6;
    private int radius;
    private List<CreateLine> connections;
    private CreateState state = CreateState.NOT_ACTIVE_POINT;
    
    
    public CreatePoint(int _x, int _y, int _radius) {
        super();
        this.x = _x;
        this.y = _y;
        this.radius = _radius;
        this.connections = new ArrayList<>(4);

        setLocation(x - radius, y - radius);
        setSize(2 * radius, 2 * radius);
        addMouseListener(PointListener.instance);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(state.getColor());
        g.fillRoundRect(0, 0, 2 * radius, 2 * radius, 2 * radius, 2 * radius);
    }

    public void setState(CreateState state) {
        this.state = state;
        repaint();
    }

    public int get_X() {
        return x;
    }

    public int get_Y() {
        return y;
    }

    public CreateState getState() {
        return state;
    }

    public int getIndexHor() {
        return indexHor;
    }

    public void setIndexHor(int indexHor) {
        this.indexHor = indexHor;
    }

    public int getIndexVert() {
        return indexVert;
    }

    public void setIndexVert(int indexVert) {
        this.indexVert = indexVert;
    }
    
    public List<CreateLine> getConnections() {
        return connections;
    }
    
    public int getRadius() {
        return radius;
    }
    
    public CreateLine getConnection(CreatePoint p) {
        for(CreateLine connectedLine : connections)
            if(connectedLine.getStartPoint().equals(p) || connectedLine.getLastPoint().equals(p))
                return connectedLine;
        return null;
    }
}
