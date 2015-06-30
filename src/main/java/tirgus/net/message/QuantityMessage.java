package tirgus.net.message;

public class QuantityMessage extends TirgusMessage
{
    private int productId;
    private int newQuantity;

    public QuantityMessage(String body)
    {
        super(body);

        String parts[] = body.split(",\\s");
        productId = Integer.parseInt(parts[0]);
        newQuantity = Integer.parseInt(parts[1]);
    }

    public QuantityMessage(int productId, int newQuantity)
    {
        super(productId + ", " + newQuantity);

        this.productId = productId;
        this.newQuantity = newQuantity;
    }

    public int getProductId()
    {
        return productId;
    }

    public int getNewQuantity()
    {
        return newQuantity;
    }
}
