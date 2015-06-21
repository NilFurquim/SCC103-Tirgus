package tirgus.net;

import tirgus.model.Market;
import tirgus.model.Product;
import tirgus.model.User;

import java.io.IOException;
import java.net.Socket;
import java.time.Period;

public class ClientMarket extends Market
{

    private TirgusConnection connection;

    public ClientMarket(String host, int port) throws IOException
    {
        connection = new TirgusConnection(new Socket(host, port));

//        newUser(new User("name", "address", "telephone", "email", "login", new Password("password")));
        newProduct(new Product("name", 1.99, 3, Period.ofDays(53), "provider"));
    }

    @Override
    public void newUser(User user)
    {
        connection.newUserMessage(user);
        super.newUser(user);
    }

    @Override
    public void stop()
    {
        connection.stop();
    }
}
