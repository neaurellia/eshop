package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;

    @Override
    public Car create(Car car) {
        return carRepository.create(car); // Persist and return created car
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll(); // FIX: `Car`, not `car`
        List<Car> allCars = new ArrayList<>();
        carIterator.forEachRemaining(allCars::add);
        return allCars;
    }

    @Override
    public Car findById(String carId) {
        return carRepository.findById(carId);
    }

    @Override
    public void update(String carId, Car car) {
        if (car == null || carId == null) {
            throw new IllegalArgumentException("Car and Car ID must not be null.");
        }

        Car existingCar = carRepository.findById(carId);
        if (existingCar == null) {
            throw new IllegalArgumentException("Update failed: Car not found.");
        }

        // Update fields
        existingCar.setCarName(car.getCarName());
        existingCar.setDescription(car.getDescription());
        existingCar.setPrice(car.getPrice());
        existingCar.setCarQuantity(car.getCarQuantity());

        // Persist changes
        carRepository.update(carId, existingCar);
    }

    @Override
    public void deleteCarById(String carId) {
        carRepository.delete(carId);
    }
}
