package tirgus.net;

import tirgus.app.server.ServerApplication;
import tirgus.model.Product;
import tirgus.net.message.NewProductMessage;
import tirgus.net.message.TirgusMessageCallback;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TirgusServer implements Runnable
{
    private TirgusMessageCallback messageCallback;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private List<TirgusConnection> connections;
    private int port;

    public TirgusServer(int port, TirgusMessageCallback messageCallback) throws IOException
    {
        connections = new ArrayList<>();
        this.port = port;

        this.messageCallback = messageCallback;

        serverSocket = new ServerSocket(port);
        serverThread = new Thread(this);
        serverThread.start();
    }

    @Override
    public void run()
    {
        boolean isRunning = true;
        while (isRunning)
        {
            try
            {
                Socket socket = serverSocket.accept();
                TirgusConnection connection = new TirgusConnection(socket, messageCallback);
                connections.add(connection);
                for (Product product : ServerApplication.getMarket().getProducts())
                {
                    connection.sendMessage(new NewProductMessage(product));
                }
            } catch (Exception ex)
            {
                isRunning = false;
            }
        }

    }

    public void stop()
    {
        serverThread.interrupt();
        try
        {
            serverSocket.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        for (TirgusConnection connection : connections)
        {
            connection.stop();
        }
    }

    public List<TirgusConnection> getConnections()
    {
        return connections;
    }
}
