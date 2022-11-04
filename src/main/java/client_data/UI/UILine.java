package client_data.UI;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JComponent;

public class UILine extends JComponent {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;
    private final Stroke stroke = new BasicStroke(6.0f);
    private UIPoint a;
    private UIPoint b;

    private UIState state = UIState.NOT_ACTIVE_LINE;

    public UILine(UIPoint from, UIPoint to) {
        super();
        init(from.get_X(), from.get_Y(), to.get_X(), to.get_Y());
        a = from;
        b = to; 
    }

    private void init(int fromX, int fromY, int toX, int toY) {
        int x0 = Math.min(fromX, toX);
        int y0 = Math.min(fromY, toY);

        setLocation(x0, y0);
        setSize(Math.max(Math.abs(toX - fromX), 10), Math.max(Math.abs(toY - fromY), 10));

        this.fromX = fromX - x0;
        this.fromY = fromY - y0;

        this.toX = toX - x0;
        this.toY = toY - y0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(state.getColor());
        g2d.setStroke(stroke);
        g2d.drawLine(fromX, fromY, toX, toY);
    }

    public UILine setState(UIState state) {
        this.state = state;
        repaint();
        return this;
    }
    
    public UIState getState() {
        return state;
    }
    
    public UIPoint getA() {
        return a;
    }
    
    public UIPoint getB() {
        return b;
    }
}
