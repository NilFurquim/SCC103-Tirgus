package tirgus.net;

import tirgus.model.Market;
import tirgus.model.Product;

import java.io.IOException;

public class ServerMarket extends Market
{
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

    @Override
    public void stop()
    {
        server.stop();
    }
}
