package tirgus.model;

import tirgus.net.TirgusConnection;
import tirgus.net.TirgusServer;

import java.io.IOException;

public class ServerMarket extends Market {
    private TirgusServer server;

    public ServerMarket(int port) throws IOException
    {
        server = new TirgusServer(port);
    }

    @Override
    public void newProduct(Product product)
    {
        super.newProduct(product);
        for (TirgusConnection connection : server.getConnections())
        {
            connection.newProductMessage(product);
        }
    }
}
