package tirgus.model;

import tirgus.serialization.OrderedSerializable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Sale extends OrderedSerializable {

    private LocalDate date;
    private int userId;
    private int productId;
    private int quantity;

    public Sale()
    {
        this.date = LocalDate.now();
        this.userId= 0;
        this.productId= 0;
        this.quantity = 0;
    }

    public Sale(LocalDate date, int userId, int productId, int quantity) {
        this.date = date;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    protected List<String> customOutputData() {
        return Arrays.asList(
                Integer.toString(userId),
                Integer.toString(productId),
                Integer.toString(quantity),
                date.toString()
        );
    }

    @Override
    public void customInputData(Iterator<String> itr) {
        userId = Integer.parseInt(itr.next());
        productId = Integer.parseInt(itr.next());
        quantity = Integer.valueOf(itr.next());
        LocalDate.parse(itr.next());
    }
}
