package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Product {
    private String productId;
    private String productName;
    private String description;
    private double price;
    private int productQuantity;

    // Default constructor (auto-generates productId)
    public Product() {
        this(UUID.randomUUID().toString(), "Unnamed Product", "No description", 0.0, 0);
    }

    // Constructor with specified productId
    public Product(String productId, String productName, String description, double price, int productQuantity) {
        this.productId = Objects.requireNonNullElse(productId, UUID.randomUUID().toString());
        setProductName(productName);
        setDescription(description);
        setPrice(price);
        setProductQuantity(productQuantity);
    }

    // Constructor that generates a random productId
    public Product(String productName, String description, double price, int productQuantity) {
        this(UUID.randomUUID().toString(), productName, description, price, productQuantity);
    }

    public void setProductQuantity(int productQuantity) {
        if (productQuantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative!");
        }
        this.productQuantity = productQuantity;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty!");
        }
        this.productName = productName.trim();
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Product description cannot be empty!");
        }
        this.description = description.trim();
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Product price cannot be negative!");
        }
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return Double.compare(product.price, price) == 0 &&
                productQuantity == product.productQuantity &&
                Objects.equals(productId, product.productId) &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, description, price, productQuantity);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", productQuantity=" + productQuantity +
                '}';
    }

}
