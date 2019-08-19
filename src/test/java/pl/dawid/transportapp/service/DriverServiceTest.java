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
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.exception.ExistInDataBase;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.repository.DriverRepository;
import pl.dawid.transportapp.tool.ObjectTestGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    private List<Driver> fakeDrivers;

    @BeforeEach
    void init() {
        Driver driver = ObjectTestGenerator.getCorrectDriver(1);
        Driver driver2 = ObjectTestGenerator.getCorrectDriver(2);
        fakeDrivers = Arrays.asList(driver, driver2);
        when(driverRepository.findAll()).thenReturn(fakeDrivers);
        when(driverRepository.findById(anyLong())).thenReturn(Optional.of(fakeDrivers.get(0)));
    }


    @Test
    void shouldFindAndMapToDtoWhenRequest() {
        Optional<DriverDto> driverDto = driverService.findById(1L);

        assertEquals(fakeDrivers.get(0).getId(), driverDto.get().getId());
    }

    @Test
    void shouldFindAllAndMapToDtoWhenRequest() {
        List<DriverDto> drivers = driverService.findAll();
        assertTrue(drivers.size() > 1);
    }

    @Test
    void shouldThrowConfictWhenObjectExist() {
        DriverDto driver = new DriverDto();
        driver.setPesel("pesel");
        when(driverRepository.findByPesel(anyString())).thenReturn(Optional.of(new Driver()));
        Assertions.assertThrows(ExistInDataBase.class, () -> driverService.addDriver(driver));
    }

    @Test
    void shouldThrowNotFoundWhenDeleteNotExistObject() {
        DriverDto driver = new DriverDto();
        driver.setPesel("pesel");
        when(driverRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class, () -> driverService.removeDriver(1L));
    }
}