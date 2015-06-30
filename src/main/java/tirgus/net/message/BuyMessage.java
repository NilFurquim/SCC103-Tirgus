package tirgus.net.message;


public class BuyMessage extends TirgusMessage
{
    private int productId;
    private int quantity;

    public BuyMessage(String body)
    {
        super(body);

        String parts[] = body.split(",\\s");
        productId = Integer.valueOf(parts[0]);
        quantity = Integer.valueOf(parts[1]);
    }

    public BuyMessage(int productId, int quantity)
    {
        super(productId + ", " + quantity);

        this.productId = productId;
        this.quantity = quantity;
    }

    public int getProductId()
    {
        return productId;
    }

    public int getQuantity()
    {
        return quantity;
    }
}
