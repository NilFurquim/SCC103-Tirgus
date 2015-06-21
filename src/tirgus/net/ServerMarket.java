package tirgus.net;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import tirgus.model.Market;
import tirgus.model.Product;
import tirgus.model.User;
import tirgus.net.message.*;
import tirgus.serialization.CSVSerializer;

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
    public boolean newProduct(Product product)
    {
        super.newProduct(product);
        for (TirgusConnection connection : server.getConnections())
        {
            connection.newProductMessage(product);
        }

        return false;
    }

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
            boolean success = newUser(m.getUser());
            connection.sendMessage(new BooleanResponseMessage(success));
            return true;
        } else if (message instanceof NewProductMessage)
        {
            NewProductMessage m = (NewProductMessage) message;
            boolean success = newProduct(m.getProduct());
            connection.sendMessage(new BooleanResponseMessage(success));
            return true;
        } else if (message instanceof RequestSaltMessage)
        {
            RequestSaltMessage m = (RequestSaltMessage) message;
            FilteredList<User> filtered = users.filtered(user -> user.getLogin().equals(m.getUsername()));
            if (filtered.isEmpty())
            {
                connection.sendMessage(new BooleanResponseMessage(false));
            } else
            {
                connection.sendMessage(new ResponseMessage(users.get(0).getPassword().getEncodedSalt()));
            }

            return true;
        } else if (message instanceof LoginMessage)
        {
            LoginMessage m = (LoginMessage) message;
            FilteredList<User> list = users.filtered(user ->
                    user.getLogin().equals(m.getLogin())
                            && user.getPassword().getEncryptedPassword().equals(m.getEncryptedPassword()));
            if (list.isEmpty())
            {
                connection.sendMessage(new BooleanResponseMessage(false));
            } else
            {
                connection.sendMessage(new ResponseMessage(CSVSerializer.write(list.get(0))));
            }

            return true;
        } else if (message instanceof BuyMessage)
        {
            BuyMessage m = (BuyMessage) message;
            Product product = getProducts().filtered(p -> p.getId() == m.getProductId()).get(0);
            if (product.getQuantity() >= m.getQuantity())
            {
                product.setQuantity(product.getQuantity() - m.getQuantity());
                connection.sendMessage(new BooleanResponseMessage(true));

                for (TirgusConnection conn : server.getConnections())
                {
                    conn.sendMessage(new QuantityMessage(product.getId(), product.getQuantity()));
                }
            } else
            {
                connection.sendMessage(new BooleanResponseMessage(false));
            }

            return true;
        }

        return false;
    }

    @Override
    public void stop()
    {
        server.stop();
    }

    public ObservableList<User> getUsers() {
        return users;
    }

    public void addToStock(Product product, int addition)
    {
        product.setQuantity(product.getQuantity() + addition);
        for (TirgusConnection connection : server.getConnections())
        {
            connection.sendMessage(new QuantityMessage(product.getId(), product.getQuantity()));
        }
    }
}
