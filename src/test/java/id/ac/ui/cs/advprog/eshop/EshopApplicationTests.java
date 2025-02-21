package id.ac.ui.cs.advprog.eshop;

import id.ac.ui.cs.advprog.eshop.controller.ProductController;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EshopApplicationTests {

    @Autowired
    private ProductController productController;

    @Autowired
    private ProductService productService;

    @Test
    void contextLoads() {
        // Check if the Spring context loads successfully
        assertThat(productController).isNotNull();
        assertThat(productService).isNotNull();
    }

    @Test
    void testMainMethod() {
        // Simulate running the main method for coverage
        EshopApplication.main(new String[]{});
    }
}
