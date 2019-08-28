package pl.dawid.transportapp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.exception.ExistInDataBase;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.repository.CarRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class CarService implements DtoConverter<CarDto, Car> {

    private final CarRepository repository;

    @Autowired
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Optional<CarDto> findById(long id) {
        return repository.findById(id)
                .map(this::convertToDto);
    }

    public List<CarDto> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    @Transactional
    public Long addCar(CarDto carDto) {
        repository.findByPlate(carDto.getPlate())
                .ifPresent((car) -> {
                    throw new ExistInDataBase("Car with plate: " + car.getPlate() + " already exist");
                });
        Car car = convertToEntity(carDto);
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
        Car car = convertToEntity(carDto);
        repository.save(car);
    }

    @Override
    public CarDto convertToDto(Car car) {
        CarDto carDto = new CarDto();
        BeanUtils.copyProperties(car, carDto);
        return carDto;
    }

    @Override
    public Car convertToEntity(CarDto carDto) {
        Car car = new Car();
        BeanUtils.copyProperties(carDto, car);
        return car;
    }
}
