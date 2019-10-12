package pl.dawid.transportapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.repository.TripRepository;
import pl.dawid.transportapp.service.settlement.ReportDriverCreator;
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
    private TripRepository tripRepository;

    @InjectMocks
    private ReportDriverCreator service;

    @BeforeEach
    void init() {
        when(driverService.findById(anyLong()))
                .thenReturn(Optional.of(ObjectTestGenerator.getCorrectDriver(1)));
        when(tripRepository.findAllByEmployeeAndDateStartBetween(any(), any(), any()))
                .thenReturn(Arrays.asList(ObjectTestGenerator.getCorrectTrip(1), ObjectTestGenerator.getCorrectTrip(2)));
    }

    @Test
    void shouldCountSalaryWhenRequested() {
        ReportDriver settlement =
                service.createReport(1, LocalDate.now(), LocalDate.of(2000, 2, 2));

        assertEquals(BigDecimal.valueOf(200), settlement.getSalary());
    }

    @Test
    void shouldSetTripsIdWhenRequested() {
        ReportDriver settlement =
                service.createReport(1, LocalDate.now(), LocalDate.of(2000, 2, 2));

        assertEquals(Arrays.asList(1L, 2L), settlement.getTrips());
    }

    @Test
    void shouldSetTripSizeWhenRequested() {
        ReportDriver settlement =
                service.createReport(1, LocalDate.now(), LocalDate.of(2000, 2, 2));

        assertEquals(2, settlement.getNumberOfTrips());
    }

}