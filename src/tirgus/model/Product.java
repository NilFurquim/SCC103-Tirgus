package tirgus.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.Period;

public class Product
{
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleObjectProperty<Period> validity;
    private SimpleStringProperty provider;

    public Product(String name, double price, Period validity, String provider)
    {
        setName(name);
        setPrice(price);
        setValidity(validity);
        setProvider(provider);
    }

    public String getName()
    {
        return name.get();
    }

    public SimpleStringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public double getPrice()
    {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price.set(price);
    }

    public Period getValidity()
    {
        return validity.get();
    }

    public SimpleObjectProperty<Period> validityProperty()
    {
        return validity;
    }

    public void setValidity(Period validity)
    {
        this.validity.set(validity);
    }

    public String getProvider()
    {
        return provider.get();
    }

    public SimpleStringProperty providerProperty()
    {
        return provider;
    }

    public void setProvider(String provider)
    {
        this.provider.set(provider);
    }
}
