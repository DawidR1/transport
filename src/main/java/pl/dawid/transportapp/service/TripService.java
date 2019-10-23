package pl.dawid.transportapp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawid.transportapp.dto.*;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.*;
import pl.dawid.transportapp.repository.TripRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TripService implements DtoConverter<TripDto, Trip> {

    private final TripRepository repository;
    private final DriverService driverService;
    private final CarService carService;
    private final LocationService locationService;
    private final LoadingPlaceService loadingPlaceService;
    private final CargoService cargoService;

    @Autowired
    public TripService(TripRepository tripRepository, DriverService driverService, CarService carService,
                       LocationService locationService, LoadingPlaceService loadingPlaceService, CargoService cargoService) {
        this.repository = tripRepository;
        this.driverService = driverService;
        this.carService = carService;
        this.locationService = locationService;
        this.loadingPlaceService = loadingPlaceService;
        this.cargoService = cargoService;
    }

    public Optional<TripDto> getDtoByIdWithLoadingPlaces(long id) {
        return repository.findByIdWithLoadingPlaces(id)
                .map(entity -> convertToDto(entity, new TripDto()));
    }

    public List<TripDto> getAllWithChildren() {
        return repository.findWithLoadingPlaces().stream()
                .map(entity -> convertToDto(entity, new TripDto()))
                .collect(Collectors.toList());
    }

    public Optional<TripDto> getNarrowDtoById(long id) {
        return repository.findById(id)
                .map(entity -> convertToDto(entity, new TripDto()));
    }

    public List<TripDto> findAll() {
        return repository.findAll().stream()
                .map(entity -> convertToDto(entity, new TripDto()))
                .collect(toList());
    }

    public List<TripDto> findNarrowAll() {
        return repository.findAll().stream()
                .map(entity -> convertToDto(entity, new TripDto()))
                .collect(toList());
    }

    @Override
    public TripDto convertToDto(Trip trip, TripDto tripDto) {
        BeanUtils.copyProperties(trip, tripDto);
        DriverDto driverDto = driverService.convertToDto(trip.getDriver(), new DriverDto());
        CarDto carDto = carService.convertToDto(trip.getCar(), new CarDto());
        LocationDto destination = locationService.convertToDto(trip.getDestination(), new LocationDto());
        LocationDto placeStart = locationService.convertToDto(trip.getPlaceStart(), new LocationDto());
        List<LoadingPlaceDto> loadingPlaceDto = trip.getLoadingPlaces().stream()
                .map(entity -> loadingPlaceService.convertToDto(entity, new LoadingPlaceDto()))
                .collect(Collectors.toUnmodifiableList());
        trip.getPlaceFinish()
                .map(entity -> locationService.convertToDto(entity, new LocationDto())).ifPresent(tripDto::setPlaceFinish);
        tripDto.setPlaceStart(placeStart);
        tripDto.setDriver(driverDto);
        tripDto.setCar(carDto);
        tripDto.setDestination(destination);
        tripDto.setLoadingPlaces(loadingPlaceDto);
        trip.getDriverSalary().ifPresent(tripDto::setDriverSalary);

        return tripDto;
    }


    @Transactional
    public Long saveTrip(Trip trip) {
        return repository.save(trip).getId();
    }

    @Transactional
    public Long addTrip(TripDto tripDto) {
        Trip.Builder builder = new Trip.Builder();
        addValidatedEntities(tripDto, builder);
        addSimpleAttributes(tripDto, builder);
        return saveTrip(builder.build());
    }

    private void addSimpleAttributes(TripDto tripDto, Trip.Builder builder) {
        tripDto.getDriverSalary()
                .ifPresent(builder::driverSalary);
        tripDto.getPlaceFinish()
                .map(LocationDto::getId)
                .flatMap(locationService::findById)
                .ifPresent(builder::placeFinish);
        builder
                .id(tripDto.getId())
                .fuel(tripDto.getFuel())
                .dateStart(tripDto.getDateStart())
                .dateFinish(tripDto.getDateFinish())
                .income(tripDto.getIncome())
                .status(tripDto.getStatus())
                .cost(tripDto.getCost());

    }

    private void addValidatedEntities(TripDto tripDto, Trip.Builder builder) {
        Driver driver = driverService.findById(tripDto.getDriver().getId())
                .orElseThrow(() -> new NotFoundException("Driver with id:" + tripDto.getDriver().getId() + "not found"));
        Car car = carService.findById(tripDto.getCar().getId())
                .orElseThrow(() -> new NotFoundException("Car with id:" + tripDto.getCar().getId() + "not found"));
        Location destination = locationService.findById(tripDto.getDestination().getId())
                .orElseThrow(() -> new NotFoundException("Location destination with id:" + tripDto.getDestination().getId() + "not found"));
        Location placeStart = locationService.findById(tripDto.getPlaceStart().getId())
                .orElseThrow(() -> new NotFoundException("Location place start with id:" + tripDto.getPlaceStart().getId() + "not found"));
        List<LoadingPlace> loadingPlaces = tripDto.getLoadingPlaces().stream()
                .map(dto -> loadingPlaceService.convertToEntity(dto, new LoadingPlace()))
                .collect(Collectors.toList());
        loadingPlaces.forEach(loadingPlace -> {
            Location location = locationService.findById(loadingPlace.getLocation().getId())
                    .orElseThrow(() -> new NotFoundException("Location in Loading Place with id:" + loadingPlace.getId() + "not found"));
            loadingPlace.setLocation(location);
        });

        builder
                .car(car)
                .driver(driver)
                .destination(destination)
                .placeStart(placeStart)
                .loadingPlaces(loadingPlaces);
    }
}
