package rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

import client.PointListener;
import client.ServerListener;
import client.UserInterface.CreateWindow;



public class Client
{
    private Client() {}
    

    public static void main(String[] args) throws RemoteException, NotBoundException, InterruptedException
    {
        String host = "localhost";
        int port = 8080;

        Registry registry = LocateRegistry.getRegistry(host, port);

        Corridors stub = (Corridors) registry.lookup ("server");
            

        int clientID = stub.getClientID();
            

        int ID = -1;
        while(ID == -1) {
            ID = stub.getOpponentID(clientID);
        }
          
        
        stub.start(clientID);
        CreateWindow clientGUI = new CreateWindow("Коридорчики ", clientID); // Собираем интерфейс


        PointListener.stub = stub;
        PointListener.clientID = clientID;
        PointListener.gameField = clientGUI;
            

        ServerListener stepListener = new ServerListener(stub, clientID, ID);
        ServerListener.mutex = new Object();
        ServerListener.gameField = clientGUI;
        Thread thread = stepListener.StartServerListener();
        thread.start();
        
        clientGUI.setVisible(true);

        thread.join();
        System.out.println("END");
    }

}
