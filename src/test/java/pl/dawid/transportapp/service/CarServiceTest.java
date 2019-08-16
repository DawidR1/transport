package pl.dawid.transportapp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.exception.ExistInDataBase;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.repository.CarRepository;
import pl.dawid.transportapp.tool.ObjectTestGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CarServiceTest {
    
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;
    private List<Car> fakeCars;

    @BeforeEach
    void init() {
        Car car = ObjectTestGenerator.getCorrectCar(1);
        Car car2 = ObjectTestGenerator.getCorrectCar(2);
        fakeCars = Arrays.asList(car,car2);

        when(carRepository.findAll()).thenReturn(fakeCars);
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(fakeCars.get(0)));
    }


    @Test
    void shouldFindAndMapToDtoWhenRequest() {
        Optional<CarDto> carDto = carService.findById(1L);

        assertEquals(fakeCars.get(0).getId(), carDto.get().getId());
    }

    @Test
    void shouldFindAllAndMapToDtoWhenRequest() {
        List<CarDto> cars = carService.findAll();
        assertTrue(cars.size() > 1);
    }

    @Test
    void shouldThrowConfictWhenObjectExist() {
        CarDto car = new CarDto();
        car.setPlate("plate");
        when(carRepository.findByPlate(anyString())).thenReturn(Optional.of(new Car()));
        Assertions.assertThrows(ExistInDataBase.class, () -> carService.addCar(car));
    }

    @Test
    void shouldThrowNotFoundWhenDeleteNotExistObject() {
        CarDto car = new CarDto();
        car.setPlate("plate");
        when(carRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> carService.removeCar(1L));
    }
}