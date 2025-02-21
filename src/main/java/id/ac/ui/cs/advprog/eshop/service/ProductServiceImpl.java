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
            throw new IllegalArgumentException("Product or product ID cannot be null");
        }

        Product existingProduct = productRepository.findById(product.getProductId());
        if (existingProduct == null) {
            throw new IllegalArgumentException("Cannot update: Product not found!");
        }

        existingProduct.setProductName(product.getProductName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setProductQuantity(product.getProductQuantity());

        productRepository.update(existingProduct); // Ensure repository updates it
    }

    @Override
    public void delete(String productId) {
        productRepository.delete(productId);
    }
}
