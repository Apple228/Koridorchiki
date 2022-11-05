package client;

import client.UserInterface.CreateState;
import client.UserInterface.CreateWindow;
import client.UserInterface.CreateLine;
import client.UserInterface.CreatePoint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import rmi.Corridors;


public class PointListener implements MouseListener
{
    
    public static final PointListener instance = new PointListener();
    private CreatePoint startPoint;
    private CreatePoint lastPoint;
    private CreatePoint lastMissedPoint;
    private CreateState aPrevState;
    private CreateState missedPointPrevState;
    public static CreateWindow gameField;
    public static Corridors stub;
    public static int clientID;
    
    public PointListener() {}
    private void clearLinks() {
        startPoint = null;
        lastPoint = null;
    }
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
        
            if(mouseEvent.getSource() instanceof CreatePoint p) {

                if(startPoint == null) {
                    startPoint = p;
                    aPrevState = startPoint.getState();
                    startPoint.setState(CreateState.CHOOSING_FIRST_PLAYER);
                } else {
                    lastPoint = p;

                    int a_x = startPoint.getIndexX();
                    int a_y = startPoint.getIndexY();
                    int b_x = lastPoint.getIndexX();
                    int b_y = lastPoint.getIndexY();

                    if (stub.isLineAllowed(clientID, a_x, a_y, b_x, b_y)) {
                        startPoint.setState(CreateState.ACTIVE_FIRST_PLAYER);
                        lastPoint.setState(CreateState.ACTIVE_FIRST_PLAYER);
                        CreateLine connectionLine = startPoint.getConnection(lastPoint);
                        connectionLine.setState(CreateState.ACTIVE_FIRST_PLAYER);
                        
                        stub.addLine(clientID, a_x, a_y, b_x, b_y);
                        clearLinks();
                    } else {
                        lastMissedPoint = p;
                        missedPointPrevState = lastMissedPoint.getState();
                        lastMissedPoint.setState(CreateState.MISSED_POINT);
                        startPoint.setState(aPrevState);
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
    


}
