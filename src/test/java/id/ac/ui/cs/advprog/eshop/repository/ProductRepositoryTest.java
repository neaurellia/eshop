package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository(); // Fresh instance for each test
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product("Sampo Cap Bambang", "Shampoo", 10.0, 100);
        productRepository.create(product);

        List<Product> productList = productRepository.findAll();
        assertFalse(productList.isEmpty());
        Product savedProduct = productList.get(0);
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Product> productList = productRepository.findAll();
        assertTrue(productList.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product("Sampo Cap Bambang", "Shampoo", 10.0, 100);
        productRepository.create(product1);

        Product product2 = new Product("Sampo Cap Usep", "Shampoo", 15.0, 50);
        productRepository.create(product2);

        List<Product> productList = productRepository.findAll();

        assertEquals(2, productList.size());
        assertEquals(product1.getProductId(), productList.get(0).getProductId());
        assertEquals(product2.getProductId(), productList.get(1).getProductId());
    }

    @Test
    void testDuplicateProductId() {
        Product product1 = new Product("Sampo Cap Bambang", "Shampoo", 10.0, 100);
        productRepository.create(product1);

        Product product2 = new Product("Sampo Cap Usep", "Shampoo", 15.0, 50);
        product2.setProductId(product1.getProductId()); // Same ID

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.create(product2));
        assertEquals("Duplicate productId is not allowed", exception.getMessage());
    }

    @Test
    void testFindById() {
        Product product = new Product("Body Wash", "Liquid soap", 20.0, 10);
        productRepository.create(product);

        Product foundProduct = productRepository.findById(product.getProductId());

        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
        assertEquals(product.getProductName(), foundProduct.getProductName());
    }

    @Test
    void testFindByIdNotFound() {
        Product foundProduct = productRepository.findById("nonexistent-id");

        assertNull(foundProduct);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product("Conditioner", "Hair care", 30.0, 15);
        productRepository.create(product);

        productRepository.delete(product.getProductId());

        assertNull(productRepository.findById(product.getProductId()));
    }

    @Test
    void testDeleteNonExistentProduct() {
        assertDoesNotThrow(() -> productRepository.delete("nonexistent-id"));
    }

    @Test
    void testUpdateExistingProduct() {
        // Create and add a product to the repository
        Product product = new Product("Laptop", "Gaming Laptop", 1500.0, 5);
        productRepository.create(product);

        // Modify product details
        Product updatedProduct = new Product(product.getProductId(), "Ultrabook", "Lightweight Laptop", 1200.0, 3);

        // Update the product in repository
        productRepository.update(updatedProduct);

        // Retrieve updated product
        Product foundProduct = productRepository.findById(product.getProductId());

        // Assertions
        assertNotNull(foundProduct);
        assertEquals("Ultrabook", foundProduct.getProductName());
        assertEquals("Lightweight Laptop", foundProduct.getDescription());
        assertEquals(1200.0, foundProduct.getPrice());
        assertEquals(3, foundProduct.getProductQuantity());
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product nonExistentProduct = new Product("NonExistent", "Does not exist", 50.0, 1);

        // Ensure exception is thrown when updating a non-existent product
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productRepository.update(nonExistentProduct));

        assertEquals("Cannot update: Product not found!", exception.getMessage());
    }

}
