package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawid.transportapp.model.Car;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByPlate(String plate);
}
