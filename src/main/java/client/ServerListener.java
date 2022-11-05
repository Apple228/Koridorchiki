package client;

import client.UserInterface.CreateState;
import client.UserInterface.CreateWindow;
import client.UserInterface.CreateLine;
import client.UserInterface.CreatePoint;
import server.Point;
import java.rmi.RemoteException;
import java.util.Vector;

import rmi.Corridors;


public class ServerListener {
    public static Object mutex;
    public static CreateWindow gameField;
    
    private static Corridors stub;
    private static int opponentID;


    public ServerListener (Corridors _stub,  int _opponentID) {
        stub = _stub;
        opponentID = _opponentID;
    }



    public void printLine(Vector<Point> v)
    {
        CreatePoint a = null;
        CreatePoint b = null;
        for(int i = 0; i < 2; i++)
        {
            if(i == 0)
            {
                a = CreateWindow.grid.getPoints_list().get(v.get(i).y).get(v.get(i).x);
            }
            else
            {
                b = CreateWindow.grid.getPoints_list().get(v.get(i).y).get(v.get(i).x);
            }
        }

        CreateLine connectionLine = a.getConnection(b);

        if(CreateState.UNUSED_LINE == connectionLine.getState())
        {
            connectionLine.setState(CreateState.PLAYER2);
            a.setState(CreateState.PLAYER2);
            b.setState(CreateState.PLAYER2);
        }
    }
    
    public Thread StartServerListener()
    {
        return new Thread(() -> {
            try {
                Vector<Point> vector;
                
                while (!stub.isFinished(opponentID)) {
                    vector = stub.getOpponentStep(opponentID);
                    if (vector.get(0).x != -10)
                    {
                        synchronized(mutex) {
                            printLine(vector);
                        }


                    }
                }
                
                vector = stub.getOpponentStep(opponentID);
                synchronized(mutex) {
                    printLine(vector);
                }


            } catch (RemoteException e) {
                System.err.println("Error! " + e.getMessage());

            }
        }
        );
    }
}
