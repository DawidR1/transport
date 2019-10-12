package pl.dawid.transportapp.service.settlement;

import pl.dawid.transportapp.dto.Report;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;

public interface ReportService {
     Report generateReport(long id, LocalDate startDate, LocalDate endDate);
     Report generateReport(long id);

     ByteArrayOutputStream generateReportInFormat(Report report, FormatCreator formatCreator);
//     Document generateSettlementAsPdf(long id, LocalDate startDate, LocalDate endDate);


}
