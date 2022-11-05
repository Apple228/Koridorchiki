package client.UserInterface;

import client.PointListener;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class CreatePoint extends JComponent
{
    private int indexX;
    private int indexY;
    private final int x;
    private final int y;

    private final List<CreateLine> connections;
    private CreateState state = CreateState.UNUSED_POINT;
    
    
    public CreatePoint(int x, int y)
    {
        super();
        int radius = 6;
        this.connections = new ArrayList<>(4);
        this.x = x;
        this.y = y;


        setLocation(x - radius, y - radius);
        setSize(2 * radius, 2 * radius);
        addMouseListener(PointListener.instance);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(state.getColor());
        g.fillRoundRect(0, 0, 2 * 6, 2 * 6, 2 * 6, 2 * 6);
    }

    public void setState(CreateState state)
    {
        this.state = state;
        repaint();
    }
    public int getIndexY() {
        return indexY;
    }
    public int getIndexX() {
        return indexX;
    }
    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }
    public void setIndexX(int indexX) {
        this.indexX = indexX;
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


    public List<CreateLine> getConnections() {
        return connections;
    }
    

    
    public CreateLine getConnection(CreatePoint point)
    {
        for(CreateLine i : connections)
            if(i.getStartPoint().equals(point) || i.getLastPoint().equals(point))
                return i;
        return null;
    }
}
