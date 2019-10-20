package pl.dawid.transportapp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.enums.TripStatus;
import pl.dawid.transportapp.model.LoadingPlace;
import pl.dawid.transportapp.util.Mappings;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Data
public class TripPostDto {


    private Long id;
    private TripStatus status;
    @Min(value = 0)
    private Long destinationId;
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateStart;
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateFinish;
    private Long placeFinishId;
    @Min(value = 0)
    private Long carId;
    @Min(value = 0)
    private Long employeeId;
    @Min(value = 0)
    private Long placeStartId;
    private BigDecimal income;
    private BigDecimal cost;
    private List<LoadingPlace> loadingPlaces;
    private int distance;
    private int fuel;
    private BigDecimal driverSalary;

    public Optional<Long> getPlaceFinishId() {
        return Optional.ofNullable(placeFinishId);
    }
}
