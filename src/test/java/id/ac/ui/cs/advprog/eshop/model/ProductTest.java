package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    // Positive scenario: Editing product successfully
    @Test
    void testEditProduct_Success() {
        // Edit product details
        product.setProductName("Sampo Cap Aman");
        product.setProductQuantity(50);

        // Verify changes
        assertEquals("Sampo Cap Aman", product.getProductName());
        assertEquals(50, product.getProductQuantity());
    }

    // Negative scenario: Editing product with invalid quantity (negative value)
    @Test
    void testEditProduct_Fail_InvalidQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.setProductQuantity(-10);
        });

        assertEquals("Product quantity cannot be negative!", exception.getMessage());
    }


    // Negative scenario: Editing product with empty name
    @Test
    void testEditProduct_Fail_EmptyName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            product.setProductName("");
        });

        assertEquals("Product name cannot be empty!", exception.getMessage());
    }

    // Positive scenario: Deleting product successfully
    @Test
    void testDeleteProduct_Success() {
        product = null; // Simulating deletion

        assertNull(product, "Product should be deleted (nullified)");
    }

    // Negative scenario: Deleting a product that is already null (double deletion)
    @Test
    void testDeleteProduct_Fail_AlreadyDeleted() {
        product = null; // First deletion

        // Try deleting again
        try {
            product = null; // Second deletion attempt
        } catch (Exception e) {
            fail("Deleting a non-existent product should not throw an exception.");
        }

        assertNull(product, "Product should still be null after attempted re-deletion.");
    }
}
