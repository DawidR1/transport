package pl.dawid.transportapp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
    private final FileStorageService storeFile;

    @Autowired
    public DriverService(DriverRepository repository, FileStorageService storeFile) {
        this.repository = repository;
        this.storeFile = storeFile;
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

    @Transactional
    public void update(DriverDto driverDto, Long id) { //TODO dorobic testy
        Driver driverDb = repository.findById(id).orElseThrow(() -> new ExistInDataBase("Driver not found"));
        repository.findByPesel(driverDto.getPesel()).ifPresent(driver -> {
            if (!driver.getId().equals(driverDb.getId())) {
                throw new ExistInDataBase("Driver with pesel: " + driverDb.getPesel() + " already exist");//FIXME Dawid redundancja, przeniesc do messages
            }
        });
        driverDto.setId(id);
        driverDto.setImageName(driverDb.getImageName());        //TODO przetestowac
        Driver driver = convertToEntity(driverDto);
        repository.save(driver);
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

    public void updateDriver(Long id, MultipartFile multipartFile) {
        Driver driver = repository.findById(id).orElseThrow(() -> new NotFoundException("Driver Not Found"));
        String fileName = storeFile.storeFile(multipartFile, id);
        driver.setImageName(fileName);
        repository.save(driver);
    }
}
