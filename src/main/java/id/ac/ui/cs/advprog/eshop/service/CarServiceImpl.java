package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarServiceImpl {
    private final List<Car> carList = new ArrayList<>();

    public Car create(Car car) {
        carList.add(car);
        return car;
    }

    public void update(String id, Car updatedCar) {
        for (int i = 0; i < carList.size(); i++) {
            if (carList.get(i).getCarId().equals(id)) {
                carList.set(i, updatedCar);
                return;
            }
        }
    }

    public void deleteCarById(String id) {
        carList.removeIf(car -> car.getCarId().equals(id));
    }

    public List<Car> findAll() {
        return new ArrayList<>(carList);
    }

    public Car findById(String id) {
        return carList.stream()
                .filter(car -> car.getCarId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
