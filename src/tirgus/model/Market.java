package tirgus.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Market
{
    private final ObservableList<Product> products;

    public Market()
    {
        products = FXCollections.observableArrayList();
    }

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
