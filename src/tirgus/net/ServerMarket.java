package tirgus.net;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tirgus.model.Market;
import tirgus.model.Product;
import tirgus.model.User;
import tirgus.net.message.NewProductMessage;
import tirgus.net.message.NewUserMessage;
import tirgus.net.message.TirgusMessage;

import java.io.IOException;

public class ServerMarket extends Market
{
    private final ObservableList<User> users;
    private TirgusServer server;

    public ServerMarket(int port) throws IOException
    {
        users = FXCollections.observableArrayList();
        server = new TirgusServer(port, this::serverMessageCallback);
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
    public boolean newUser(User user)
    {
        boolean alreadyExists = users.stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin()));
        if (!alreadyExists)
        {
            users.add(user);
            return true;
        }

        return false;
    }

    private boolean serverMessageCallback(TirgusConnection connection, TirgusMessage message)
    {
        if (message instanceof NewUserMessage)
        {
            NewUserMessage m = (NewUserMessage) message;
            boolean success = Market.instance().newUser(m.getUser());
            connection.sendResponse(success);
            return success;
        } else if (message instanceof NewProductMessage)
        {
            NewProductMessage m = (NewProductMessage) message;
            Market.instance().newProduct(m.getProduct());
            return true;
        }

        return false;
    }


    @Override
    public void stop()
    {
        server.stop();
    }
}
