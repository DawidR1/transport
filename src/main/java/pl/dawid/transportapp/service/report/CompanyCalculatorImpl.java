package pl.dawid.transportapp.service.report;

import org.springframework.stereotype.Component;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.model.Trip;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class CompanyCalculatorImpl implements CompanyCalculator {

    @Override
    public BigDecimal calculateDriverSalaryBasedOnTrips(List<Trip> trips) {
        return trips.stream()
                .map(Trip::getDriverSalary)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateCompanyProfit(List<TripDto> tripDtos) {
        BigDecimal cost = calculateCosts(tripDtos);
        return tripDtos.stream()
                .map(TripDto::getIncome)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(cost);
    }

    private BigDecimal calculateCosts(List<TripDto> tripDtos) {
        return tripDtos.stream()
                .map(TripDto::getCost)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
