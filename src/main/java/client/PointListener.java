package client;

import client.UserInterface.CreateState;
import client.UserInterface.CreateWindow;
import client.UserInterface.CreateLine;
import client.UserInterface.CreatePoint;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import rmi.Corridors;


public class PointListener implements MouseListener
{
    
    public static final PointListener instance = new PointListener();
    private CreatePoint startPoint;
    private CreatePoint lastMissedPoint;
    private CreateState aPrevState;
    private CreateState missedPointPrevState;
    public static CreateWindow gameField;
    public static Corridors stub;
    public static int clientID;
    
    public PointListener() {}

    public boolean checkStopRun(Corridors stub) throws RemoteException {
        if (stub.isFinished(clientID) || !stub.isStepAllowed(clientID))
        {
            return true;
        }

        return false;
    }
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        try {

            if (checkStopRun(stub))
            {
                return;
            }
            

            if(lastMissedPoint != null) {
                lastMissedPoint.setState(missedPointPrevState);
                lastMissedPoint = null;
            }

            CreatePoint lastPoint;
            if(mouseEvent.getSource() instanceof CreatePoint p) {

                if(startPoint == null) {
                    startPoint = p;
                    aPrevState = startPoint.getState();
                    startPoint.setState(CreateState.CHOOSING_FIRST_PLAYER);
                } else {
                    lastPoint = p;



                    if (stub.isLineAllowed(
                            clientID, startPoint.getIndexX(), 
                            startPoint.getIndexY(), 
                            lastPoint.getIndexX(),  
                            lastPoint.getIndexY())
                    ) 
                    {
                        startPoint.setState(CreateState.PLAYER1);
                        lastPoint.setState(CreateState.PLAYER1);
                        CreateLine connectionLine = startPoint.getConnection(lastPoint);
                        connectionLine.setState(CreateState.PLAYER1);
                        
                        stub.addLine(
                                clientID,
                                startPoint.getIndexX(), 
                                startPoint.getIndexY(), 
                                lastPoint.getIndexX(),  
                                lastPoint.getIndexY()
                        );
                        startPoint = null;
                        lastPoint = null;
                    } else {
                        lastMissedPoint = p;
                        missedPointPrevState = lastMissedPoint.getState();
                        lastMissedPoint.setState(CreateState.MISSED_POINT);
                        startPoint.setState(aPrevState);
                        startPoint = null;
                        lastPoint = null;
                    }
                }
            } else {
                startPoint = null;
                lastPoint = null;
            }   
        } catch (Exception e) {
            System.err.println("Point Listener error: " + e.toString());

        }  
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}


    


}
