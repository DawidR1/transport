package pl.dawid.transportapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.dawid.transportapp.dto.LocationDto;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.repository.LocationRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    public Page<LocationDto> findAll(Pageable pageable) {
        Page<Location> page = repository.findAll(pageable);
        List<LocationDto> content = page.getContent().stream()
                .map(entity -> convertToDto(entity, new LocationDto()))
                .collect(toList());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @Transactional
    public Long addLocation(LocationDto locationDto) {
        Location location = convertToEntity(locationDto, new Location());
        repository.save(location);
        return location.getId();
    }

    @Transactional
    public Long updateLocation(LocationDto locationDto) {
        return addLocation(locationDto);
    }
}
