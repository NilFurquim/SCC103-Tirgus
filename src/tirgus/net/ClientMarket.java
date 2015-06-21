package tirgus.net;

import tirgus.model.Market;
import tirgus.model.User;
import tirgus.net.message.NewProductMessage;
import tirgus.net.message.NewUserMessage;
import tirgus.net.message.TirgusMessage;

import java.io.IOException;
import java.net.Socket;

public class ClientMarket extends Market
{

    private TirgusConnection connection;

    public ClientMarket(String host, int port) throws IOException
    {
        connection = new TirgusConnection(new Socket(host, port), this::clientMessageCallback);
    }

    private boolean clientMessageCallback(TirgusConnection connection, TirgusMessage message)
    {
        if (message instanceof NewUserMessage)
        {
            NewUserMessage m = (NewUserMessage) message;
            Market.instance().newUser(m.getUser());
            return true;
        } else if (message instanceof NewProductMessage)
        {
            NewProductMessage m = (NewProductMessage) message;
            Market.instance().newProduct(m.getProduct());
            return true;
        }

        return false;
    }

    @Override
    public boolean newUser(User user)
    {
        if (connection.newUserToServer(user))
        {
            return super.newUser(user);
        } else
        {
            return false;
        }
    }

    @Override
    public void stop()
    {
        connection.stop();
    }
}
