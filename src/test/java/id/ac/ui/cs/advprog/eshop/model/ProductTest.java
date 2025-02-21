package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductConstructor() {
        Product product = new Product("Laptop", "High-performance laptop", 1500.0, 5);

        assertNotNull(product.getProductId()); // Ensure productId is generated
        assertEquals("Laptop", product.getProductName());
        assertEquals("High-performance laptop", product.getDescription());
        assertEquals(1500.0, product.getPrice());
        assertEquals(5, product.getProductQuantity());
    }

    @Test
    void testSetProductNameValid() {
        Product product = new Product();
        product.setProductName("Smartphone");

        assertEquals("Smartphone", product.getProductName());
    }

    @Test
    void testSetProductNameInvalid() {
        Product product = new Product();

        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> product.setProductName(null));
        assertEquals("Product name cannot be empty!", exception1.getMessage());

        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> product.setProductName(""));
        assertEquals("Product name cannot be empty!", exception2.getMessage());
    }

    @Test
    void testSetDescriptionValid() {
        Product product = new Product();
        product.setDescription("A great product");

        assertEquals("A great product", product.getDescription());
    }

    @Test
    void testSetDescriptionInvalid() {
        Product product = new Product();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setDescription("   "));
        assertEquals("Product description cannot be empty!", exception.getMessage());
    }

    @Test
    void testSetProductQuantityValid() {
        Product product = new Product();
        product.setProductQuantity(10);

        assertEquals(10, product.getProductQuantity());
    }

    @Test
    void testSetProductQuantityInvalid() {
        Product product = new Product();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setProductQuantity(-5));
        assertEquals("Product quantity cannot be negative!", exception.getMessage());
    }

    @Test
    void testEqualsMethod() {
        Product product1 = new Product("Tablet", "A lightweight tablet", 800.0, 2);
        Product product2 = new Product("Tablet", "A lightweight tablet", 800.0, 2);

        // Ensure the product IDs are the same for comparison
        product2.setProductId(product1.getProductId());

        assertEquals(product1, product2);
    }

    @Test
    void testToStringMethod() {
        Product product = new Product("Mouse", "Wireless mouse", 25.99, 3);
        String expected = "Product{" +
                "productId='" + product.getProductId() + '\'' +
                ", productName='Mouse'" +
                ", description='Wireless mouse'" +
                ", price=25.99" +
                ", productQuantity=3" +
                '}';

        assertEquals(expected, product.toString());
    }

    @Test
    void testHashCodeMethod() {
        Product product1 = new Product("Tablet", "A lightweight tablet", 800.0, 2);
        Product product2 = new Product("Tablet", "A lightweight tablet", 800.0, 2);

        // Ensure both products have the same ID for proper hashCode comparison
        product2.setProductId(product1.getProductId());

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    @Test
    void testSetEmptyProductNameThrowsException() {
        Product product = new Product();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setProductName(""));
        assertEquals("Product name cannot be empty!", exception.getMessage());
    }

    @Test
    void testSetNullProductNameThrowsException() {
        Product product = new Product();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setProductName(null));
        assertEquals("Product name cannot be empty!", exception.getMessage());

    }

    @Test
    void testValidDescription() {
        Product product = new Product();
        product.setDescription("A powerful gaming laptop");
        assertEquals("A powerful gaming laptop", product.getDescription());
    }

    @Test
    void testInvalidDescriptionEmpty() {
        Product product = new Product();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setDescription(""));
        assertEquals("Product description cannot be empty!", exception.getMessage());
    }

    @Test
    void testInvalidDescriptionNull() {
        Product product = new Product();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setDescription(null));
        assertEquals("Product description cannot be empty!", exception.getMessage());
    }

    @Test
    void testEqualsSameObject() {
        Product product = new Product("Phone", "A smartphone", 500.0, 10);
        assertEquals(product, product); // Same reference should be equal
    }

    @Test
    void testEqualsDifferentObjectsSameValues() {
        Product product1 = new Product("Phone", "A smartphone", 500.0, 10);
        Product product2 = new Product("Phone", "A smartphone", 500.0, 10);

        // Set same ID to simulate real object comparison
        product2.setProductId(product1.getProductId());

        assertEquals(product1, product2);
    }

    @Test
    void testNotEqualsDifferentProductId() {
        Product product1 = new Product("Phone", "A smartphone", 500.0, 10);
        Product product2 = new Product("Phone", "A smartphone", 500.0, 10);

        // Different UUIDs mean they should not be equal
        assertNotEquals(product1, product2);
    }

    @Test
    void testNotEqualsDifferentName() {
        Product product1 = new Product("Phone", "A smartphone", 500.0, 10);
        Product product2 = new Product("Tablet", "A smartphone", 500.0, 10);

        product2.setProductId(product1.getProductId()); // Ensure only the name differs
        assertNotEquals(product1, product2);
    }

    @Test
    void testNotEqualsDifferentDescription() {
        Product product1 = new Product("Phone", "A smartphone", 500.0, 10);
        Product product2 = new Product("Phone", "A different smartphone", 500.0, 10);

        product2.setProductId(product1.getProductId()); // Ensure only the description differs
        assertNotEquals(product1, product2);
    }

    @Test
    void testNotEqualsDifferentPrice() {
        Product product1 = new Product("Phone", "A smartphone", 500.0, 10);
        Product product2 = new Product("Phone", "A smartphone", 600.0, 10);

        product2.setProductId(product1.getProductId()); // Ensure only the price differs
        assertNotEquals(product1, product2);
    }

    @Test
    void testNotEqualsDifferentQuantity() {
        Product product1 = new Product("Phone", "A smartphone", 500.0, 10);
        Product product2 = new Product("Phone", "A smartphone", 500.0, 5);

        product2.setProductId(product1.getProductId()); // Ensure only the quantity differs
        assertNotEquals(product1, product2);
    }

    @Test
    void testEqualsWithNull() {
        Product product = new Product("Phone", "A smartphone", 500.0, 10);
        assertNotEquals(product, null); // Should return false
    }

    @Test
    void testEqualsWithDifferentClass() {
        Product product = new Product("Phone", "A smartphone", 500.0, 10);
        String notAProduct = "I am not a product";
        assertNotEquals(product, notAProduct); // Should return false
    }

    @Test
    void testSetPriceValid() {
        Product product = new Product();
        product.setPrice(999.99);

        assertEquals(999.99, product.getPrice());
    }

    @Test
    void testSetPriceInvalid() {
        Product product = new Product();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> product.setPrice(-50.0));
        assertEquals("Product price cannot be negative!", exception.getMessage());
    }

}
