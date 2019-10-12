package pl.dawid.transportapp.service.settlement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawid.transportapp.dto.Report;
import pl.dawid.transportapp.exception.NotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportCreator creator;

    @Autowired
    public ReportServiceImpl(ReportCreator creator) {
        this.creator = creator;
    }

    @Override
    public Report generateReport(long id, LocalDate startDate, LocalDate endDate){
        return creator.createReport(id, startDate, endDate);
    }

    @Override
    public Report generateReport(long id) {
        return creator.createReport(id);
    }

    @Override
    public ByteArrayOutputStream generateReportInFormat(Report report, FormatCreator formatCreator) {
        try {
            return formatCreator.create(report);
        } catch (IOException e) {
            throw new NotFoundException("cos"); //FIXME dorobic
        }
    }
}
