package pl.dawid.transportapp.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.dto.TripReport;
import pl.dawid.transportapp.service.DriverService;
import pl.dawid.transportapp.util.Mappings;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier(Mappings.COMPANY_REPORT)
public class ReportCompanyCreator {

    private ReportDriverCreator reportDriverCreator;
    private DriverService driverService;


    @Autowired
    public ReportCompanyCreator(ReportDriverCreator reportDriverCreator, DriverService driverService) {
        this.reportDriverCreator = reportDriverCreator;
        this.driverService = driverService;
    }

    public TripReport createReport(LocalDate startDate, LocalDate endDate) {
        return getReport(startDate, endDate);
    }

    private TripReport getReport(LocalDate startDate, LocalDate endDate) {
        TripReport report = new TripReport();

        report.setStart(startDate);
        report.setEnd(endDate);

        List<ReportDriver> reportDrivers = driverService.findAllId().stream()
                .map(driverId -> reportDriverCreator.createReport(driverId, startDate, endDate))
                .collect(Collectors.toUnmodifiableList());
        report.setReportDrivers(reportDrivers);

        List<TripDto> tripDtos = reportDrivers.stream()
                .flatMap(rep -> rep.getTrips().stream()).collect(Collectors.toList());
        BigDecimal cost = tripDtos.stream().map(TripDto::getCost).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal companyIncome = tripDtos.stream()
                .map(TripDto::getIncome)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .subtract(cost);
        report.setCompanyIncome(companyIncome);
        report.setCompanyNumberOfTrips(tripDtos.size());

        return report;
    }
}
