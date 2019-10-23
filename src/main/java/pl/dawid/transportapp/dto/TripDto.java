package pl.dawid.transportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.enums.TripStatus;
import pl.dawid.transportapp.util.Mappings;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private Long id;

    @NotNull
    private TripStatus status; //FIXME dorobic optional

    @NotNull
    private LocationDto destination;

    @NotNull
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateStart;

    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateFinish;

    private LocationDto placeFinish;

    @NotNull
    private CarDto car;

    @NotNull
    private DriverDto driver;

    private BigDecimal income;

    @NotNull
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocationDto placeStart;

    private List<LoadingPlaceDto> loadingPlaces;

    private Integer distance;

    private BigDecimal cost;

    private Integer fuel;

    private BigDecimal driverSalary;

    public Optional<LocationDto> getPlaceFinish() {
        return Optional.ofNullable(placeFinish);
    }

    public Optional<BigDecimal> getDriverSalary() {
        return Optional.ofNullable(driverSalary);
    }


}
