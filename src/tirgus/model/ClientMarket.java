package tirgus.model;

import tirgus.net.TirgusConnection;

import java.io.IOException;
import java.net.Socket;
import java.time.Period;

public class ClientMarket extends Market{

    private TirgusConnection connection;

    public ClientMarket(String host, int port) throws IOException
    {
        connection = new TirgusConnection(new Socket(host, port));
        connection.newProductMessage(new Product("name", 1.99, 3, Period.ofDays(33), "provider"));
    }
}
