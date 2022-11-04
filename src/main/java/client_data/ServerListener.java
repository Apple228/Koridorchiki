package client_data;

import client_data.UI.UIState;
import client_data.UI.UI;
import client_data.UI.UILine;
import client_data.UI.UIPoint;
import server_data.Point;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Corridors;


public class ServerListener {
    public static Object mutex;
    public static UI gameField;
    
    private static Corridors stub;
    private static int opponentID;
    private static int clientID;
    
    
    public ServerListener (Corridors _stub, int _clientID,  int _opponentID) {
        stub = _stub;
        clientID = _clientID;
        opponentID = _opponentID;
    }
    
    public void printLine(Vector<Point> v) {
        UIPoint a = gameField.grid.getPoints().get(v.get(0).y).get(v.get(0).x);
        UIPoint b = gameField.grid.getPoints().get(v.get(1).y).get(v.get(1).x);
        UILine connectionLine = a.getConnection(b);
        
        if(connectionLine.getState() == UIState.NOT_ACTIVE_LINE) {
            connectionLine.setState(UIState.ACTIVE_SECOND_PLAYER);
            a.setState(UIState.ACTIVE_SECOND_PLAYER);
            b.setState(UIState.ACTIVE_SECOND_PLAYER);
        }
    }
    
    public void updateScore(int clientScore, int opponentScore) {
        gameField.score.clientScore = clientScore; 
        gameField.score.opponentScore = opponentScore; 
        gameField.score.repaint();
    }
    
    public Thread StartServerListener() {
        return new Thread(() -> {
            try {
                Vector<Point> v;  
                
                while (!stub.isFinished(opponentID)) {
                    v = stub.getOpponentStep(opponentID);
                    if (v.get(0).x != -10) {
                        synchronized(mutex) {
                            printLine(v);
                        }
                        int clientScore  = stub.getScore(clientID);
                        int opponentScore  = stub.getScore(opponentID);
                        synchronized(mutex) {
                            updateScore(clientScore, opponentScore);
                        }
                    }
                }
                
                v = stub.getOpponentStep(opponentID);
                synchronized(mutex) {
                   printLine(v);
                }
                int clientScore  = stub.getScore(clientID);
                int opponentScore  = stub.getScore(opponentID);
                synchronized(mutex) {
                    updateScore(clientScore, opponentScore);
                } 
            } catch (RemoteException e) {
                System.err.println("Prowhisper Error! " + e.getMessage());
                Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        );
    }
}
