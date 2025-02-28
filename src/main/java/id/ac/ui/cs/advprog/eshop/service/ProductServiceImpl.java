package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements CreateProduct, UpdateProduct, DeleteProduct {
    private final List<Product> productList = new ArrayList<>();

    @Override
    public Product create(Product product) {
        productList.add(product);
        return product;
    }

    @Override
    public void update(Product product) {
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductId().equals(product.getProductId())) {
                productList.set(i, product);
                return;
            }
        }
    }

    @Override
    public void delete(String id) {
        productList.removeIf(product -> product.getProductId().equals(id));
    }

    public List<Product> findAll() {
        return productList;
    }

    public Product findById(String id) {
        return productList.stream()
                .filter(product -> product.getProductId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
