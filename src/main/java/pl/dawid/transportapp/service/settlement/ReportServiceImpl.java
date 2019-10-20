package pl.dawid.transportapp.service.settlement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.dto.TripReport;
import pl.dawid.transportapp.exception.NotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class ReportServiceImpl {

    private final ReportDriverCreator creatorDriver;
    private final ReportCompanyCreator creatorCompany;

    @Autowired
    public ReportServiceImpl(ReportDriverCreator creatorDriver, ReportCompanyCreator creatorCompany) {
        this.creatorDriver = creatorDriver;
        this.creatorCompany = creatorCompany;
    }

    public ReportDriver generateReportForDriver(long id, LocalDate startDate, LocalDate endDate) {
        return creatorDriver.createReport(id, startDate, endDate);
    }

    public TripReport generateReportForCompany(LocalDate startDate, LocalDate endDate) {
        return creatorCompany.createReport(startDate, endDate);
    }

    public ByteArrayOutputStream generateReportInPdf(PDFCreator creator) {
        try {
            return creator.create();
        } catch (IOException e) {
            throw new NotFoundException("cos"); //FIXME dorobic
        }
    }

}
