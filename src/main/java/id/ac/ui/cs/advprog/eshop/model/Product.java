package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Product {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString(); // Generate unique ID
    }

    public Product(String productName, String description, double price, int productQuantity) {
        this.productId = UUID.randomUUID().toString(); // Generate unique ID
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.productQuantity = productQuantity;
    }
}
