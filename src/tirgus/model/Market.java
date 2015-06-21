package tirgus.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Market
{
    private final ObservableList<Product> products;

    private static Market _instance = null;

    public static Market setInstance(Market market)
    {
        if(_instance != null)
            throw new RuntimeException("instance was already set");
        else
        {
            _instance = market;
        }

        return _instance;
    }
    public static Market instance()
    {
        return _instance;
    }

    public void newProduct(Product product)
    {
        products.add(product);
    }

    public boolean newUser(User user)
    {
        return true;
    }

    protected Market()
    {
        products = FXCollections.observableArrayList();
    }

    public ObservableList<Product> getProducts()
    {
        return products;
    }


    public abstract void stop();
}
