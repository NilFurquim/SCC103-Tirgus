package tirgus.net.message;

import tirgus.model.Product;
import tirgus.serialization.CSVSerializer;

public class NewProductMessage extends TirgusMessage
{
    private Product product;

    public NewProductMessage(String body)
    {
        super(body);
        this.product = CSVSerializer.read(body, Product.class);
    }

    public NewProductMessage(Product product)
    {
        this(CSVSerializer.write(product));
        this.product = product;
    }

    public Product getProduct()
    {
        return product;
    }
}
