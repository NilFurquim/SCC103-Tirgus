package tirgus.model;

import org.junit.Test;
import tirgus.serialization.CSVSerializer;

import java.time.Period;

import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void testSerialization() {
        Product product = new Product("name", 1.99, 7, Period.ofDays(19), "prodiver");
        String output = CSVSerializer.write(product);
        Product replicated = CSVSerializer.read(output, Product.class);

        assertEquals(product.getName(), replicated.getName());
        assertEquals(product.getPrice(), replicated.getPrice(), 0.0);
        assertEquals(product.getQuantity(), replicated.getQuantity());
        assertEquals(product.getValidity(), replicated.getValidity());
        assertEquals(product.getProvider(), replicated.getProvider());
    }
}