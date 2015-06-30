package tirgus.net.message;


public class BuyMessage extends TirgusMessage
{
    private int productId;
    private int quantity;
    private int userId;

    public BuyMessage(String body)
    {
        super(body);

        String parts[] = body.split(",\\s");

        productId = Integer.valueOf(parts[0]);
        quantity = Integer.valueOf(parts[1]);
        userId = Integer.valueOf(parts[2]);
    }

    public BuyMessage(int productId, int quantity, int userId)
    {
        super(productId + ", " + quantity + ", " + userId);

        this.productId = productId;
        this.quantity = quantity;
        this.userId = userId;
    }

    public int getProductId()
    {
        return productId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public int getUserId() {
        return userId;
    }
}
