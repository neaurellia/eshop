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
        carRepository.create(car); // Persist car in repository
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = carRepository.findAll();
        List<Car> allCar = new ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
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
        existingCar.setCarColor(car.getCarColor());
        existingCar.setCarQuantity(car.getCarQuantity());

        carRepository.update(carId, existingCar); // Persist changes
    }

    @Override
    public void deleteCarById(String carId) {
        if (carId == null) {
            throw new IllegalArgumentException("Car ID must not be null.");
        }

        Car car = carRepository.findById(carId);
        if (car == null) {
            throw new IllegalArgumentException("Delete failed: Car not found.");
        }

        carRepository.delete(carId); // Delete car from repository
    }
}
