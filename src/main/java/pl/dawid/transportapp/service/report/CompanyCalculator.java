package pl.dawid.transportapp.service.report;

import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.model.Trip;

import java.math.BigDecimal;
import java.util.List;

public interface CompanyCalculator {

    BigDecimal calculateDriverSalaryBasedOnTrips(List<Trip> trips);
    BigDecimal calculateCompanyProfit(List<TripDto> tripDtos);
}
