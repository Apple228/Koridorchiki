package client;

import client.UserInterface.CreateState;
import client.UserInterface.CreateWindow;
import client.UserInterface.CreateLine;
import client.UserInterface.CreatePoint;
import server.Point;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import rmi.Corridors;


public class ServerListener {
    public static Object mutex;
    public static CreateWindow gameField;
    
    private static Corridors stub;
    private static int opponentID;
    private static int clientID;
    
    
    public ServerListener (Corridors _stub, int _clientID,  int _opponentID) {
        stub = _stub;
        clientID = _clientID;
        opponentID = _opponentID;
    }
    
    public void printLine(Vector<Point> v) {
        CreatePoint a = CreateWindow.grid.getPoints_list().get(v.get(0).y).get(v.get(0).x);
        CreatePoint b = CreateWindow.grid.getPoints_list().get(v.get(1).y).get(v.get(1).x);
        CreateLine connectionLine = a.getConnection(b);
        
        if(connectionLine.getState() == CreateState.UNUSED_LINE) {
            connectionLine.setState(CreateState.ACTIVE_SECOND_PLAYER);
            a.setState(CreateState.ACTIVE_SECOND_PLAYER);
            b.setState(CreateState.ACTIVE_SECOND_PLAYER);
        }
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

                    }
                }
                
                v = stub.getOpponentStep(opponentID);
                synchronized(mutex) {
                   printLine(v);
                }
                int clientScore  = stub.getScore(clientID);
                int opponentScore  = stub.getScore(opponentID);

            } catch (RemoteException e) {
                System.err.println("Error! " + e.getMessage());
                Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        );
    }
}
