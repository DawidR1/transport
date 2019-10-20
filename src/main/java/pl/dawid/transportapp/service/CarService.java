package pl.dawid.transportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.exception.ExistInDataBase;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.repository.CarRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class CarService implements DtoConverter<CarDto, Car> {

    private final CarRepository repository;

    @Autowired
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Optional<CarDto> findDtoById(long id) {
        return repository.findById(id)
                .map(entity -> convertToDto(entity, new CarDto()));
    }

    public Optional<Car> findById(long id) {
        return repository.findById(id);
    }

    public List<CarDto> findAll() {
        return repository.findAll().stream()
                .map(entity -> convertToDto(entity, new CarDto()))
                .collect(toList());
    }

    @Transactional
    public Long addCar(CarDto carDto) {
        repository.findByPlate(carDto.getPlate())
                .ifPresent((car) -> {
                    throw new ExistInDataBase("Car with plate: " + car.getPlate() + " already exist");
                });
        Car car = convertToEntity(carDto, new Car());
        repository.save(car);
        return car.getId();
    }

    @Transactional
    public void removeCar(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new NotFoundException("Car not found");
        });
    }

    @Transactional
    public void update(CarDto carDto, Long id) { //TODO dorobic testy
        Car carDb = repository.findById(id).orElseThrow(() -> new ExistInDataBase("Car not found"));
        repository.findByPlate(carDto.getPlate()).ifPresent(car -> {
            if (!car.getId().equals(carDb.getId())) {
                throw new ExistInDataBase("Car with plate: " + car.getPlate() + " already exist");//FIXME Dawid redundancja, przeniesc do messages
            }
        });
        carDto.setId(id);
        Car car = convertToEntity(carDto, new Car());
        repository.save(car);
    }

    public Map<Long, String> getIdByName() {
        return repository.findAll().stream()
                .collect(Collectors.toMap(Car::getId, Car::getPlate));
    }
}
