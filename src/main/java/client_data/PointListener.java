package client_data;

import client_data.UI.UIState;
import client_data.UI.UI;
import client_data.UI.UILine;
import client_data.UI.UIPoint;
import server_data.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import rmi.Corridors;


public class PointListener implements MouseListener{
    
    public static final PointListener instance = new PointListener();
    private UIPoint a;
    private UIPoint b;
    private UIPoint lastMissedPoint;
    private UIState aPrevState;
    private UIState missedPointPrevState;
    public static UI gameField;
    public static Corridors stub;
    public static int clientID;
    
    public PointListener() {}
    
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        try {
            if (!stub.isStepAllowed(clientID)) return;
            
            if (stub.isFinished(clientID)) {
                 System.out.println("End game");
                 return;
            }
            
            if(lastMissedPoint != null) {
                lastMissedPoint.setState(missedPointPrevState);
                lastMissedPoint = null;
            }
        
            if(mouseEvent.getSource() instanceof UIPoint) { 
                UIPoint p = (UIPoint) mouseEvent.getSource();

                if(a == null) {
                    a = p;
                    aPrevState = a.getState();
                    a.setState(UIState.CHOOSING_FIRST_PLAYER);
                } else {
                    b = p;

                    int a_x = a.getIndexHor();
                    int a_y = a.getIndexVert();
                    int b_x = b.getIndexHor();
                    int b_y = b.getIndexVert();

                    if (stub.isLineAllowed(clientID, a_x, a_y, b_x, b_y)) {
                        a.setState(UIState.ACTIVE_FIRST_PLAYER);
                        b.setState(UIState.ACTIVE_FIRST_PLAYER);
                        UILine connectionLine = a.getConnection(b);
                        connectionLine.setState(UIState.ACTIVE_FIRST_PLAYER);
                        
                        stub.addLine(clientID, a_x, a_y, b_x, b_y);
                        clearLinks();
                    } else {
                        lastMissedPoint = p;
                        missedPointPrevState = lastMissedPoint.getState();
                        lastMissedPoint.setState(UIState.MISSED_POINT);
                        a.setState(aPrevState);
                        clearLinks();
                    }
                }
            } else {
                clearLinks();
            }   
        } catch (Exception e) {
            System.err.println("Point Listener error: " + e.toString());
            e.printStackTrace();
        }  
    }
    
    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
    
    private void clearLinks() {
        a = null;
        b = null;
    } 
    
    public void setStub(Corridors _stub) {
        stub = _stub;
    } 
    
    public void setClientID(int _clientID) {
        clientID = _clientID;
    } 
}
