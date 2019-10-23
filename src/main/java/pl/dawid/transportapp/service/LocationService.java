package pl.dawid.transportapp.service;

import org.springframework.stereotype.Service;
import pl.dawid.transportapp.dto.LocationDto;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.repository.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationService implements DtoConverter<LocationDto, Location> {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public Optional<LocationDto> findDtoById(Long id) {
        return findById(id)
                .map(entity -> convertToDto(entity, new LocationDto()));
    }

    public Optional<Location> findById(Long id) {
        return repository.findById(id);
    }

    public List<LocationDto> findAll() {
        return repository.findAll().stream()
                .map(entity -> convertToDto(entity, new LocationDto()))
                .collect(Collectors.toList());
    }
}
