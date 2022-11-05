package rmi;

import server.Grid;
import server.Point;

import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.LocateRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Server implements Corridors
{
    private int clientsCount;
    private final Boolean[] clientsStepAllowed;
    private final Integer[] clientsScore;

    public static int fieldSize = 2;
    private static final int maxClientsCount = 20;
    
    private final Point[] lastStepA;
    private final Point[] lastStepB;
    
    private final List<Grid> grids;
    
    
    
    public Server()
    {
        clientsCount = 0;
        
        clientsStepAllowed = new Boolean[maxClientsCount];
        clientsScore = new Integer[maxClientsCount];
        for (int i = 0; i < maxClientsCount; ++i) {
            clientsStepAllowed[i] = Boolean.FALSE;
            clientsScore[i] = 0;
        }
         
        grids = new ArrayList<Grid>();
        for (int i = 0; i <  maxClientsCount/2; ++i)
            grids.add(new Grid(fieldSize));
        
        lastStepA = new Point[maxClientsCount/2];
        lastStepB = new Point[maxClientsCount/2];
        Point p = new Point(-10, -10);
        for (int i = 0; i <  maxClientsCount/2; ++i) {
            lastStepA[i] = p;
            lastStepB[i] = p;
        }
    }
   
    public int getClientID()
    {
        if (clientsCount < maxClientsCount) {
            int clientID = clientsCount;
            clientsCount++;
            System.out.println("Подключен новый клиент " + clientID);
            System.out.println("Всего игроков " + clientsCount);
            return clientID;
        }
        else {
            System.err.println("Сервер переполнен, попробуйте позже");
            return -1;
        }

    }
    
    public int getOpponentID(int clientID)
    {
        if (clientsCount % 2 == 0)
        {
            if (clientID % 2 == 0)
            {
                int opponent = clientID + 1;
                System.out.println("Игрок " + clientID + " подключается в сражение с игроком " + opponent);
                return opponent;
            }
            else
            {
                int opponent = clientID - 1;
                System.out.println("Игрок " + clientID + " подключается в сражение с игроком " + opponent);
                return opponent;
            }


        }
        else return -1;
    }
        
    public void start(int clientID)
    {
        setStepAllow(clientID);
        System.out.println("Игрок " + clientID + " готов");
    }
        
    public boolean isStepAllowed(int clientID)
    {
      return clientsStepAllowed[clientID];
    }
        
    public boolean isLineAllowed(int clientID, int x1, int y1, int x2, int y2)
    {
        if (x1 == x2 && y1 == y2)
            return false;
        int gridID = getGridID(clientID);
        Point a = grids.get(gridID).getPoints().get(x1).get(y1); 
        Point b = grids.get(gridID).getPoints().get(x2).get(y2);
        if (a.isBeside(b) && !a.isConnected(b))
        {
            return true;
        }
        return false;
    }
    
    public void addLine(int clientID, int x1, int y1, int x2, int y2)
    {
        int gridID = getGridID(clientID);     
        Point a = grids.get(gridID).getPoints().get(x1).get(y1); 
        Point b = grids.get(gridID).getPoints().get(x2).get(y2);  
        
        a.getConnectedPoints().put(b, clientID);
        b.getConnectedPoints().put(a, clientID);

        
        lastStepA[gridID]  = a;
        lastStepB[gridID] = b;
        
        grids.get(gridID).addNumLine();
        if (clientID % 2 == 0)
        {
            int opponentID = clientID + 1;
            changeStepAllow(clientID);
            changeStepAllow(opponentID);
        }
        else
        {
            int opponentID = clientID - 1;
            changeStepAllow(clientID);
            changeStepAllow(opponentID);
        }


    }
    
    public Vector<Point> getOpponentStep(int clientID)
    {
        int gridID = getGridID(clientID);
        
        Vector<Point> v = new Vector<>();
        v.add(lastStepA[gridID]);
        v.add(lastStepB[gridID]);

        return v;
    }

    public void changeStepAllow(int clientID)
    {
        if (clientsStepAllowed[clientID])
        {
            clientsStepAllowed[clientID] = Boolean.FALSE;
        }
        else
        {
            clientsStepAllowed[clientID] = Boolean.TRUE;
        }

    }


    public int getScore(int clientID)
    {
        return clientsScore[clientID];
    }
       
    public boolean isFinished(int clientID) {
        return grids.get(getGridID(clientID)).isFinished();
    }
    
    private int getGridID(int clientID)
    {
        int gridID = clientID - clientID % 2;

        return gridID;
    }
    
    private void setStepAllow(int clientID)
    {
        if(clientID % 2 == 0)
        {
            clientsStepAllowed[clientID] =Boolean.TRUE;
        }
        else
        {
            clientsStepAllowed[clientID] =Boolean.FALSE;
        }

    }
    
    // Запуск и регистрация в сервисе имен сервера 
    public static void main(String[] args)
    {
        int port = 8080;
        try {
            // Создание экземпляра своего класса
            Server obj = new Server();
            // Преобразование локальной сссылки к удаленной
            Corridors stub = (Corridors) UnicastRemoteObject.exportObject(obj, 0);
            System.out.println(stub);
            // Получаем ссылку на сервис имен и кладем в него наш server
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("server", stub);

            System.out.println("Сервер запущен!");
        } catch (Exception e) {
            System.err.println("Сервер упал: " + e.toString());
            e.printStackTrace();
        }
    }
}