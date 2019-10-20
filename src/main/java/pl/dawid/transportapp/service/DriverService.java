package pl.dawid.transportapp.service;

import antlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.exception.ExistInDataBase;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.repository.DriverRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<DriverDto> findDtoById(long id) {
        return findById(id)
                .map(entity -> convertToDto(entity, new DriverDto()));
    }

    public Optional<Driver> findById(long id) {
        return repository.findById(id);
    }


    public List<DriverDto> findAll() {
        return repository.findAll().stream()
                .map(entity -> convertToDto(entity, new DriverDto()))
                .collect(toList());
    }

    public List<Long> findAllId(){
        return repository.findAllId();
    }

    @Transactional
    public Long addDriver(DriverDto driverDto) {
        repository.findByPesel(driverDto.getPesel())
                .ifPresent((driver) -> {
                    throw new ExistInDataBase("Driver with Pesel: " + driver.getPesel() + " already exist");
                });
        Driver driver = convertToEntity(driverDto, new Driver());
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
        Driver driver = convertToEntity(driverDto, new Driver());
        repository.save(driver);
    }

    public void updateDriver(Long id, MultipartFile multipartFile) {
        Driver driver = repository.findById(id).orElseThrow(() -> new NotFoundException("Driver Not Found"));
        String fileName = storeFile.storeFile(multipartFile, id);
        driver.setImageName(fileName);
        repository.save(driver);
    }

    public Map<Long, String> getIdByName() {
        return repository.findAll().stream()
                .collect(Collectors.toMap(
                        Driver::getId,
                        entity -> entity.getFirstName() + " " + entity.getLastName()));
    }
}
