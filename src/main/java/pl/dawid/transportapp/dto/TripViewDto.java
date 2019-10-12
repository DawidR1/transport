package pl.dawid.transportapp.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.model.Employee;
import pl.dawid.transportapp.model.LoadingPlace;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.util.Mappings;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TripViewDto extends TripDto {

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
}
