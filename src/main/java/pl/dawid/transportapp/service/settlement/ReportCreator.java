package pl.dawid.transportapp.service.settlement;

import pl.dawid.transportapp.dto.Report;

import java.time.LocalDate;

public interface ReportCreator {
    Report createReport(long id, LocalDate startDate, LocalDate endDate);
    Report createReport(long id);

}

