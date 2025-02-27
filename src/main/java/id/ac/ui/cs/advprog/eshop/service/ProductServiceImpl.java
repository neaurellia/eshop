package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        return productRepository.create(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll(); // No need for additional conversion
    }

    @Override
    public Product findById(String productId) {
        return productRepository.findById(productId);
    }

    @Override
    public void update(Product product) {
        if (product == null || product.getProductId() == null) {
            throw new IllegalArgumentException("Product and product ID must not be null.");
        }

        Product existingProduct = productRepository.findById(product.getProductId());
        if (existingProduct == null) {
            throw new IllegalArgumentException("Update failed: Product not found.");
        }

        // Update fields
        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setProductQuantity(product.getProductQuantity());

        // Persist changes
        productRepository.update(existingProduct);
    }


    @Override
    public void delete(String productId) {
        productRepository.delete(productId);
    }
}
