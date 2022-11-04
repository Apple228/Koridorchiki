package rmi;

import server_data.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface Corridors extends Remote {    
    int getClientID() throws RemoteException;
    int getOpponentID(int clientID) throws RemoteException;
    void start(int clientID) throws RemoteException;
    boolean isStepAllowed(int clientID) throws RemoteException;
    boolean isLineAllowed(int clientID, int x1, int y1, int x2, int y2) throws RemoteException;
    void addLine(int clientID, int x1, int y1, int x2, int y2) throws RemoteException;
    Vector<Point> getOpponentStep(int clientID)throws RemoteException;
    int getScore(int clientID) throws RemoteException;
    boolean isFinished(int clientID) throws RemoteException;
}