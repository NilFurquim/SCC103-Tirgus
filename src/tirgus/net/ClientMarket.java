package tirgus.net;

import javafx.beans.property.SimpleObjectProperty;
import tirgus.app.client.ClientApplication;
import tirgus.model.Market;
import tirgus.model.User;
import tirgus.net.message.*;
import tirgus.serialization.CSVSerializer;

import java.io.IOException;
import java.net.Socket;

public class ClientMarket extends Market
{
    private SimpleObjectProperty<User> currentUser;
    private TirgusConnection connection;

    public ClientMarket(String host, int port) throws IOException
    {
        currentUser = new SimpleObjectProperty<>(null);
        connection = new TirgusConnection(new Socket(host, port), this::clientMessageCallback);
    }

    private boolean clientMessageCallback(TirgusConnection connection, TirgusMessage message)
    {
        if (message instanceof NewProductMessage)
        {
            NewProductMessage m = (NewProductMessage) message;
            ClientApplication.getMarket().newProduct(m.getProduct());
            return true;
        }

        return false;
    }

    public boolean newUser(User user)
    {
        return connection.newUserToServer(user);
    }

    @Override
    public void stop()
    {
        connection.stop();
    }

    public String requestSalt(String login)
    {
        connection.sendMessage(new RequestSaltMessage(login));
        ResponseMessage response = connection.waitForResponse();

        if (response instanceof BooleanResponseMessage)
        {
            return null;
        }

        return response.getBody();
    }

    public User sendLogin(String login, String encryptedPassword)
    {
        connection.sendMessage(new LoginMessage(login, encryptedPassword));
        ResponseMessage response = connection.waitForResponse();
        if (response instanceof BooleanResponseMessage || response == null)
        {
            return null;
        }

        return CSVSerializer.read(response.getBody(), User.class);
    }

    public User getCurrentUser()
    {
        return currentUser.get();
    }

    public SimpleObjectProperty<User> currentUserProperty()
    {
        return currentUser;
    }

    public void setCurrentUser(User currentUser)
    {
        this.currentUser.set(currentUser);
    }
}
