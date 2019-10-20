package pl.dawid.transportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.dto.TripPostDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.model.Trip;
import pl.dawid.transportapp.repository.TripRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class TripService implements DtoConverter<TripDto, Trip> {

    private TripRepository repository;
    private DriverService driverService;
    private CarService carService;
    private final LocationService locationService;

    @Autowired
    public TripService(TripRepository tripRepository, DriverService driverService, CarService carService, LocationService locationService) {
        this.repository = tripRepository;
        this.driverService = driverService;
        this.carService = carService;
        this.locationService = locationService;
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

    private Long saveTrip(Trip trip) {
        return repository.save(trip).getId();
    }

    @Transactional
    public Long addTrip(TripPostDto tripDto) {
        Driver driver = driverService.findById(tripDto.getEmployeeId())
                .orElseThrow(() -> new NotFoundException("Driver with id:" + tripDto.getEmployeeId() + "not found"));
        Car car = carService.findById(tripDto.getCarId())
                .orElseThrow(() -> new NotFoundException("Car with id:" + tripDto.getCarId() + "not found"));
        Location destination = locationService.findById(tripDto.getDestinationId())
                .orElseThrow(() -> new NotFoundException("Location destination with id:" + tripDto.getDestinationId() + "not found"));
        Location placeStart = locationService.findById(tripDto.getPlaceStartId())
                .orElseThrow(() -> new NotFoundException("Location place start with id:" + tripDto.getDestinationId() + "not found"));
        Optional<Location> placeFinish = tripDto.getPlaceFinishId().flatMap(locationService::findById);

        Trip trip = new Trip();
        trip.setDestination(destination);
        trip.setPlaceStart(placeStart);
        trip.setFuel(tripDto.getFuel());
        trip.setCar(car);
        trip.setEmployee(driver);
        trip.setDateStart(tripDto.getDateStart());
        trip.setDateFinish(tripDto.getDateFinish());
        trip.setIncome(tripDto.getIncome());
        trip.setDriverSalary(tripDto.getDriverSalary());
        placeFinish.ifPresent(trip::setPlaceFinish);
        trip.setStatus(tripDto.getStatus());
        trip.setLoadingPlaces(tripDto.getLoadingPlaces());
        trip.setCost(tripDto.getCost());

        return saveTrip(trip);
    }
}
