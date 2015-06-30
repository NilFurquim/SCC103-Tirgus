package tirgus.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Market domain model
 */
public abstract class Market
{
    private final ObservableList<Product> products;

    /**
     * Constructor
     */
    public Market()
    {
        products = FXCollections.observableArrayList();
    }

    /**
     * Add new product
     * @param product
     * @return
     */
    public boolean newProduct(Product product)
    {
        boolean alreadyExists = products.stream().anyMatch(product1 ->
                product.getName().equals(product1.getName()) && product.getProvider().equals(product1.getProvider()));

        if (!alreadyExists)
        {
            products.add(product);
            return true;
        }

        return false;
    }

    public ObservableList<Product> getProducts()
    {
        return products;
    }

    public abstract void stop();
}
