package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setProductId("123");
        product.setProductName("Sample Product");
        product.setProductQuantity(10);

        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals("123", createdProduct.getProductId());
        assertEquals("Sample Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProductId("101");
        product1.setProductName("Product A");
        product1.setProductQuantity(5);

        Product product2 = new Product();
        product2.setProductId("102");
        product2.setProductName("Product B");
        product2.setProductQuantity(15);

        List<Product> mockProducts = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(mockProducts);

        List<Product> products = productService.findAll();

        assertEquals(2, products.size());
        assertEquals("101", products.get(0).getProductId());
        assertEquals("102", products.get(1).getProductId());
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("555");
        product.setProductName("Special Product");
        product.setProductQuantity(20);

        when(productRepository.findById("555")).thenReturn(product);

        Product foundProduct = productService.findById("555");

        assertNotNull(foundProduct);
        assertEquals("555", foundProduct.getProductId());
        assertEquals("Special Product", foundProduct.getProductName());
    }

    @Test
    void testUpdateProduct() {
        // Step 1: Create and store product
        Product product = new Product("Smartphone X", "A great smartphone", 999.0, 10);
        when(productRepository.create(any(Product.class))).thenReturn(product);
        productService.create(product);

        // Step 2: Mock repository to return the stored product
        when(productRepository.findById(product.getProductId())).thenReturn(product);

        // Step 3: Update the product
        Product updatedProduct = new Product(product.getProductId(), "Smartphone X", "Upgraded version with better battery", 1099.0, 15);
        productService.update(updatedProduct);

        // Step 4: Retrieve and verify update
        when(productRepository.findById(product.getProductId())).thenReturn(updatedProduct);
        Product retrievedProduct = productService.findById(product.getProductId());

        assertNotNull(retrievedProduct);
        assertEquals("Upgraded version with better battery", retrievedProduct.getDescription());
        assertEquals(1099.0, retrievedProduct.getPrice());
    }



    @Test
    void testDeleteProduct() {
        productService.delete("200");
        verify(productRepository, times(1)).delete("200");
    }

    @Test
    void testUpdateExistingProduct() {
        // Step 1: Create and add a product to repository
        Product product = new Product("P001", "Laptop", "Gaming Laptop", 1500.0, 10);

        // Mock repository behavior
        when(productRepository.create(product)).thenReturn(product);
        when(productRepository.findById("P001")).thenReturn(product);

        productService.create(product);

        // Step 2: Modify product details
        Product updatedProduct = new Product("P001", "Laptop Pro", "Better gaming laptop", 1800.0, 10);

        // Ensure repository returns the existing product when searching
        when(productRepository.findById("P001")).thenReturn(updatedProduct);

        productService.update(updatedProduct);

        // Step 3: Retrieve and verify update
        Product retrievedProduct = productService.findById("P001");
        assertNotNull(retrievedProduct, "Updated product should not be null");
        assertEquals("Laptop Pro", retrievedProduct.getProductName());
        assertEquals("Better gaming laptop", retrievedProduct.getDescription());
        assertEquals(1800.0, retrievedProduct.getPrice());
        assertEquals(10, retrievedProduct.getProductQuantity());
    }



    @Test
    void testUpdateNonExistentProduct() {
        Product nonExistentProduct = new Product("P999", "Nonexistent", "This product doesn't exist", 999.0, 1);

        // Mock repository to return null when looking for this product
        when(productRepository.findById("P999")).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.update(nonExistentProduct);
        });

        assertEquals("Cannot update: Product not found!", exception.getMessage());
    }

    @Test
    void testUpdateNonExistingProductThrowsException() {
        Product nonExistentProduct = new Product("Gaming Laptop", "Powerful", 2000.0, 5);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.update(nonExistentProduct);
        });

        assertEquals("Cannot update: Product not found!", exception.getMessage());
    }

    @Test
    void testUpdateProduct_NullProduct() {
        // When null is passed to update, an IllegalArgumentException should be thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.update(null);
        });
        assertEquals("Product or product ID cannot be null", exception.getMessage());
    }

    @Test
    void testUpdateProduct_NullProductId() {
        // Create a product normally
        Product product = new Product("Test Product", "Test description", 100.0, 5);
        // Force the product ID to be null
        product.setProductId(null);

        // When updating a product with a null ID, an IllegalArgumentException should be thrown
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.update(product);
        });
        assertEquals("Product or product ID cannot be null", exception.getMessage());
    }

}
