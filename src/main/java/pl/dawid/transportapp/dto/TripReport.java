package pl.dawid.transportapp.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class TripReport extends Report {

    private BigDecimal companyIncome;

    private List<ReportDriver> reportDrivers;

    private int companyNumberOfTrips;
}
