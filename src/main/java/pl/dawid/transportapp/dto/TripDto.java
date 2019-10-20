package pl.dawid.transportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.enums.TripStatus;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.model.Employee;
import pl.dawid.transportapp.model.LoadingPlace;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.util.Mappings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private TripStatus status;
    private Long id;
    private Location destination;
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateStart;
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateFinish;
    private Location placeFinish;
    private Car car;
    private Employee employee;
    private BigDecimal income;
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private Location placeStart;
    private List<LoadingPlace> loadingPlaces;
    private Integer distance;
    private BigDecimal cost;
    private Integer fuel;
    private BigDecimal driverSalary;

    public Optional<Location> getPlaceFinish() {
        return Optional.ofNullable(placeFinish);
    }

    public Optional<BigDecimal> getDriverSalary() {
        return Optional.ofNullable(driverSalary);
    }
}
