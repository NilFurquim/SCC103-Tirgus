package tirgus.net;

import javafx.beans.property.SimpleObjectProperty;
import tirgus.app.client.ClientApplication;
import tirgus.model.Market;
import tirgus.model.Product;
import tirgus.model.User;
import tirgus.net.message.*;
import tirgus.serialization.CSVSerializer;

import java.io.IOException;
import java.net.Socket;

/**
 * Client side market
 */
public class ClientMarket extends Market
{
    private SimpleObjectProperty<User> currentUser;
    private TirgusConnection connection;

    /**
     * Constructor
     * @param host
     * @param port
     * @throws IOException
     */
    public ClientMarket(String host, int port) throws IOException
    {
        currentUser = new SimpleObjectProperty<>(null);
        connection = new TirgusConnection(new Socket(host, port), this::clientMessageCallback);
    }

    /**
     * callback for messages from server
     * @param connection
     * @param message
     * @return
     */
    private boolean clientMessageCallback(TirgusConnection connection, TirgusMessage message)
    {
        if (message instanceof NewProductMessage)
        {
            NewProductMessage m = (NewProductMessage) message;
            ClientApplication.getMarket().newProduct(m.getProduct());
            return true;
        } else if (message instanceof QuantityMessage)
        {
            QuantityMessage m = (QuantityMessage) message;
            Product product =
                    ClientApplication.getMarket().getProducts()
                            .filtered(p -> p.getId() == m.getProductId()).get(0);
            product.setQuantity(m.getNewQuantity());
            return true;
        }

        return false;
    }

    /**
     * Register user
     * @param user
     * @return
     */
    public boolean newUser(User user)
    {
        return connection.sendMessageAndWaitBoolean(new NewUserMessage(user));
    }

    @Override
    public void stop()
    {
        connection.stop();
    }

    /**
     * Request salt for password encryption
     * @param login
     * @return
     */
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

    /**
     * Send login data
     * @param login
     * @param encryptedPassword
     * @return
     */
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

    /**
     * Buy a product
     * @param productId
     * @param quantity
     * @return
     */
    public boolean buyProduct(int productId, int quantity)
    {
        connection.sendMessage(new BuyMessage(productId, quantity, getCurrentUser().getId()));
        BooleanResponseMessage response = (BooleanResponseMessage) connection.waitForResponse();
        return response != null && response.successful();
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
