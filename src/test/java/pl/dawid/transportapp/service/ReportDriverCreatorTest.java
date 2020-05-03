package pl.dawid.transportapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.service.report.CompanyCalculator;
import pl.dawid.transportapp.service.report.ReportDriverCreator;
import pl.dawid.transportapp.tool.ObjectTestGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportDriverCreatorTest {

    @Mock
    private DriverService driverService;

    @Mock
    private TripService tripService;

    @Mock
    private CompanyCalculator companyCalculator;

    @InjectMocks
    private ReportDriverCreator service;

    @BeforeEach
    void init() {
        when(driverService.findById(anyLong()))
                .thenReturn(Optional.of(ObjectTestGenerator.getCorrectDriver(1)));
        when(tripService.findAllByDrivers(any(), any(), any()))
                .thenReturn(Arrays.asList(ObjectTestGenerator.getCorrectTrip(1), ObjectTestGenerator.getCorrectTrip(2)));
        when(companyCalculator.calculateDriverSalaryBasedOnTrips(any()))
                .thenReturn(BigDecimal.TEN);
    }

    @Test
    void shouldCountSalaryWhenRequested() {
        ReportDriver settlement =
                service.createReport(1, LocalDate.now(), LocalDate.of(2000, 2, 2));

        assertEquals(BigDecimal.TEN, settlement.getSalary());
    }

    @Test
    void shouldSetTripSizeWhenRequested() {
        ReportDriver settlement =
                service.createReport(1, LocalDate.now(), LocalDate.of(2000, 2, 2));

        assertEquals(2, settlement.getNumberOfTrips());
    }

}