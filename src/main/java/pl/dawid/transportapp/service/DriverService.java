package pl.dawid.transportapp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.exception.ExistInDataBase;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.repository.DriverRepository;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class DriverService implements DtoConverter<DriverDto, Driver> {

    private final DriverRepository repository;

    @Autowired
    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public Optional<DriverDto> findById(long id) {
        return repository.findById(id)
                .map(this::convertToDto);
    }

    public List<DriverDto> findAll() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(toList());
    }

    @Transactional
    public Long addDriver(DriverDto driverDto) {
        repository.findByPesel(driverDto.getPesel())
                .ifPresent((driver) -> {
                    throw new ExistInDataBase("Driver with Pesel: " + driver.getPesel() + " already exist");
                });
        Driver driver = convertToEntity(driverDto);
        repository.save(driver);
        return driver.getId();
    }

    @Transactional
    public void removeDriver(Long id) {
        repository.findById(id).ifPresentOrElse(repository::delete, () -> {
            throw new NotFoundException("Driver not found");
        });
    }

    @Override
    public DriverDto convertToDto(Driver driver) {
        DriverDto driverDto = new DriverDto();
        BeanUtils.copyProperties(driver, driverDto);
        return driverDto;
    }

    @Override
    public Driver convertToEntity(DriverDto driverDto) {
        Driver driver = new Driver();
        BeanUtils.copyProperties(driverDto, driver);
        return driver;
    }
}
