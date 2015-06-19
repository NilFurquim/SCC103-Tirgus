package tirgus.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Market
{
    private final ObservableList<Product> products;
    private final ObservableList<User> users;

    private static final Market _instance = new Market();
    public static Market instance()
    {
        return _instance;
    }

    public void newProduct(Product product)
    {
        products.add(product);
    }

    public void newUser(User user)
    {
        users.add(user);
    }

    private Market()
    {
        products = FXCollections.observableArrayList();
        users = FXCollections.observableArrayList();
    }

    public ObservableList<Product> getProducts()
    {
        return products;
    }

    public ObservableList<User> getUsers()
    {
        return users;
    }
}
