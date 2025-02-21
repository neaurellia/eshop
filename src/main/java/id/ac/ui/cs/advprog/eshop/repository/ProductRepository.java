package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {
    private Map<String, Product> productData = new LinkedHashMap<>(); // Preserve insertion order

    public Product create(Product product) {
        if (productData.containsKey(product.getProductId())) {
            throw new IllegalArgumentException("Duplicate productId is not allowed");
        }
        productData.put(product.getProductId(), product);
        return product;
    }

    public List<Product> findAll() {
        return new ArrayList<>(productData.values()); // Preserve order
    }

    public Product findById(String productId) {
        return productData.get(productId);
    }

    public void delete(String productId) {
        productData.remove(productId);
    }

    public void update(Product product) {
        if (!productData.containsKey(product.getProductId())) {
            throw new IllegalArgumentException("Cannot update: Product not found!");
        }
        productData.put(product.getProductId(), product); // Overwrites existing product
    }


}
