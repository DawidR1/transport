package pl.dawid.transportapp.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.model.Trip;
import pl.dawid.transportapp.repository.TripRepository;
import pl.dawid.transportapp.service.DriverService;
import pl.dawid.transportapp.service.TripService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Qualifier("driverReport")
public class ReportDriverCreator {

    private final TripRepository tripRepository;
    private final TripService tripService;
    private final DriverService driverService;

    @Autowired
    public ReportDriverCreator(TripRepository tripRepository, TripService tripService, DriverService driverService) {
        this.tripRepository = tripRepository;
        this.tripService = tripService;
        this.driverService = driverService;
    }

    public ReportDriver createReport(long id, LocalDate startDate, LocalDate endDate) {
        Driver driver = driverService.findById(id).orElseThrow(() -> new NotFoundException("cannot found driver with id: " + id));
        List<Trip> trips = tripRepository.findAllByDriverAndDateStartBetween(driver, startDate, endDate);
        return getReport(trips, driver, startDate, endDate);
    }

    private ReportDriver getReport(List<Trip> trips, Driver driver, LocalDate startDate, LocalDate endDate) {
        ReportDriver report = new ReportDriver();

        BigDecimal salarySum = trips.stream()
                .map(Trip::getDriverSalary)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        report.setSalary(salarySum);
        report.setDriverDto(driverService.convertToDto(driver, new DriverDto()));
        List<TripDto> tripsDto = trips.stream().map(entity -> tripService.convertToDto(entity, new TripDto())).collect(Collectors.toList());
        report.setTrips(tripsDto);
        report.setNumberOfTrips(trips.size());
        report.setStart(startDate);
        report.setEnd(endDate);
        return report;
    }
}
