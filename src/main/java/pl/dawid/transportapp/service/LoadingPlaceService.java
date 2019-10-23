package pl.dawid.transportapp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawid.transportapp.dto.CargoDto;
import pl.dawid.transportapp.dto.LoadingPlaceDto;
import pl.dawid.transportapp.dto.LocationDto;
import pl.dawid.transportapp.model.Cargo;
import pl.dawid.transportapp.model.LoadingPlace;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.repository.LoadingPlaceRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoadingPlaceService implements DtoConverter<LoadingPlaceDto, LoadingPlace> {

    private final LoadingPlaceRepository repository;
    private final LocationService locationService;
    private final CargoService cargoService;

    @Autowired
    public LoadingPlaceService(LoadingPlaceRepository repository, LocationService locationService, CargoService cargoService) {
        this.repository = repository;
        this.locationService = locationService;
        this.cargoService = cargoService;
    }

    @Override
    public LoadingPlaceDto convertToDto(LoadingPlace loadingPlace, LoadingPlaceDto loadingPlaceDto) {
        BeanUtils.copyProperties(loadingPlace, loadingPlaceDto);
        List<CargoDto> cargoDtos = loadingPlace.getCargo().stream()
                .map(entity -> cargoService.convertToDto(entity, new CargoDto()))
                .collect(Collectors.toUnmodifiableList());
        LocationDto locationDto = locationService.convertToDto(loadingPlace.getLocation(), new LocationDto());
        loadingPlaceDto.setLocation(locationDto);
        loadingPlaceDto.setCargo(cargoDtos);
        return loadingPlaceDto;
    }

    @Override
    public LoadingPlace convertToEntity(LoadingPlaceDto loadingPlaceDto, LoadingPlace loadingPlace) {
        BeanUtils.copyProperties(loadingPlaceDto, loadingPlace);
        List<Cargo> cargos = loadingPlaceDto.getCargo().stream()
                .map(dto -> cargoService.convertToEntity(dto, new Cargo()))
                .collect(Collectors.toUnmodifiableList());
        Location location = locationService.convertToEntity(loadingPlaceDto.getLocation(), new Location());
        loadingPlace.setLocation(location);
        loadingPlace.setCargo(cargos);
        return loadingPlace;
    }

    public Optional<LoadingPlace> findById(long id) {
        return repository.findById(id);
    }

    public void save(LoadingPlace loadingPlace) {
        repository.save(loadingPlace);
    }
}
