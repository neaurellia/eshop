package id.ac.ui.cs.advprog.eshop.model;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Car extends Product{
    private String carId;
    private String carName;
    private String carColor;
    private int carQuantity;

    public Car() {
        super(); // Calls Product constructor if needed
    }

    @Override
    public String getProductType() {
        return "Car";
    }
}