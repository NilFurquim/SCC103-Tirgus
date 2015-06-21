package tirgus.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TirgusServer implements Runnable
{
    private List<TirgusConnection> connections;
    private int port;

    public TirgusServer(int port)
    {
        connections = new ArrayList<>();
        this.port = port;

        new Thread(this).start();
    }

    @Override
    public void run()
    {
        try
        {
            ServerSocket server = new ServerSocket(port);
            while (true)
            {
                Socket socket = server.accept();
                connections.add(new TirgusConnection(socket));
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public List<TirgusConnection> getConnections()
    {
        return connections;
    }
}
