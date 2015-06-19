package tirgus.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.Period;

public class Product
{
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty quantity;
    private SimpleObjectProperty<Period> validity;
    private SimpleStringProperty validityDescription;
    private SimpleStringProperty provider;

    public Product(String name, double price, int quantity, Period validity, String provider)
    {
        this.name = new SimpleStringProperty();
        setName(name);

        this.price = new SimpleDoubleProperty();
        setPrice(price);

        this.quantity = new SimpleIntegerProperty();
        setQuantity(quantity);

        this.validity = new SimpleObjectProperty<>();
        this.validityDescription = new SimpleStringProperty();
        setValidity(validity);

        this.provider = new SimpleStringProperty();
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
        setValidityDescription(validity);
    }

    public String getValidityDescription()
    {
        return validityDescription.get();
    }

    public SimpleStringProperty validityDescriptionProperty()
    {
        return validityDescription;
    }

    private void setValidityDescription(Period validity)
    {
        final int years = validity.getYears();
        final int months = validity.getMonths();
        final int weeks = validity.getDays() / 7;
        final int days = validity.getDays() - 7 * weeks;

        StringBuilder buffer = new StringBuilder();
        if (years > 0)
        {
            buffer.append(years);
            buffer.append(" Year(s) ");
        }
        if (months > 0)
        {
            buffer.append(months);
            buffer.append(" Month(s) ");
        }
        if (weeks > 0)
        {
            buffer.append(weeks);
            buffer.append(" Week(s) ");
        }
        if (days > 0)
        {
            buffer.append(days);
            buffer.append(" Day(s) ");
        }

        this.validityDescription.set(buffer.toString());
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

    public int getQuantity()
    {
        return quantity.get();
    }

    public SimpleIntegerProperty quantityProperty()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity.set(quantity);
    }
}
