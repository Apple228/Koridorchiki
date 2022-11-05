package client.UserInterface;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JComponent;

public class CreateLine extends JComponent {
    private final CreatePoint startPoint;
    private final CreatePoint lastPoint;

    private int toLastX;
    private int toLastY;
    private final Stroke stroke = new BasicStroke(10.0f);


    private CreateState state = CreateState.UNUSED_LINE;
    private final int fromStartX;
    private final int fromStartY;
    public CreateLine(CreatePoint from, CreatePoint to) {
        super();

        int x0 = Math.min(from.get_X(), to.get_X());
        int y0 = Math.min(from.get_Y(), to.get_Y());

        setLocation(x0, y0);
        setSize(Math.max(Math.abs(from.get_X() - to.get_X()), 10), Math.max(Math.abs( from.get_Y() - to.get_Y()), 10));

        this.fromStartX = from.get_X() - x0;
        this.fromStartY = from.get_Y() - y0;

        this.toLastX = to.get_X() - x0;
        this.toLastY = to.get_Y() - y0;
        startPoint = from;
        lastPoint = to;
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(state.getColor());
        g2d.setStroke(stroke);
        g2d.drawLine(fromStartX, fromStartY, toLastX, toLastY);
    }

    public void setState(CreateState state) {
        this.state = state;
        repaint();
    }
    
    public CreateState getState() {
        return state;
    }
    
    public CreatePoint getStartPoint() {
        return startPoint;
    }
    
    public CreatePoint getLastPoint() {
        return lastPoint;
    }
}
