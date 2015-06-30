package tirgus.net;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import tirgus.app.server.ServerApplication;
import tirgus.model.Market;
import tirgus.model.Product;
import tirgus.model.Sale;
import tirgus.model.User;
import tirgus.net.message.*;
import tirgus.serialization.CSVSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Server side market
 */
public class ServerMarket extends Market
{
    private final ObservableList<User> users;
    private final ObservableList<Sale> sales;

    private TirgusServer server;

    /**
     * Constructor
     * @param port
     * @throws IOException
     */
    public ServerMarket(int port) throws IOException
    {
        users = FXCollections.observableArrayList();
        sales = FXCollections.observableArrayList();
        server = new TirgusServer(port, this::serverMessageCallback);
    }

    /**
     * Register product
     * @param product
     * @return
     */
    @Override
    public boolean newProduct(Product product)
    {
        super.newProduct(product);
        for (TirgusConnection connection : server.getConnections())
        {
            connection.sendMessage(new NewProductMessage(product));
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
        boolean alreadyExists = users.stream().anyMatch(user1 -> user1.getLogin().equals(user.getLogin()));
        if (!alreadyExists)
        {
            users.add(user);
            return true;
        }

        return false;
    }

    /**
     * Add to stock
     * @param product
     * @param addition
     */

    public void addToStock(Product product, int addition)
    {
        product.setQuantity(product.getQuantity() + addition);
        for (TirgusConnection connection : server.getConnections())
        {
            connection.sendMessage(new QuantityMessage(product.getId(), product.getQuantity()));
        }
    }

    /**
     * callback for messages from client
     * @param connection
     * @param message
     * @return
     */
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
                connection.sendMessage(new ResponseMessage(filtered.get(0).getPassword().getEncodedSalt()));
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

                sales.add(new Sale(LocalDate.now(), m.getUserId(), product.getId(), m.getQuantity()));
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

    /**
     * When closing application
     */
    public void onCloseRequest()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Export to CSV");
        alert.setHeaderText("Save your data");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.OK))
        {
            ServerApplication.closingExport("Products.csv", ServerApplication.getMarket().getProducts());
            ServerApplication.closingExport("Users.csv", ServerApplication.getMarket().getUsers());
            ServerApplication.closingExport("Sales.csv", ServerApplication.getMarket().getSales());
        }
    }

    public ObservableList<Sale> getSales() {
        return sales;
    }
}
