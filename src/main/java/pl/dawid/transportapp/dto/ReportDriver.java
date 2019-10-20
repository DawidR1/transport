package pl.dawid.transportapp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ReportDriver extends Report {

    private DriverDto driverDto;
    private int numberOfTrips;
    private List<TripDto> trips;
    private BigDecimal salary;
}