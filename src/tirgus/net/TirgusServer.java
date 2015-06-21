package tirgus.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TirgusServer implements Runnable
{
    private ServerSocket serverSocket;
    private Thread serverThread;
    private List<TirgusConnection> connections;
    private int port;

    public TirgusServer(int port) throws IOException
    {
        connections = new ArrayList<>();
        this.port = port;

        serverSocket = new ServerSocket(port);

        serverThread = new Thread(this);
        serverThread.start();
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                Socket socket = serverSocket.accept();
                connections.add(new TirgusConnection(socket));
            }
        } catch (IOException ignored)
        {
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
