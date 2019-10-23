package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.dto.TripReport;
import pl.dawid.transportapp.enums.Format;
import pl.dawid.transportapp.service.report.PDFCreator;
import pl.dawid.transportapp.service.report.PdfCompanyCreator;
import pl.dawid.transportapp.service.report.PdfDriverCreator;
import pl.dawid.transportapp.service.report.ReportServiceImpl;

import java.time.LocalDate;

import static pl.dawid.transportapp.util.Mappings.*;

@RestController
public class ReportController {


    private final ReportServiceImpl reportService;
    private final PdfDriverCreator driverPdfCreator;
    private final PdfCompanyCreator companyPdfCreator;

    @Autowired
    public ReportController(ReportServiceImpl reportService, PdfDriverCreator driverPdfCreator
            , PdfCompanyCreator companyPdfCreator) {
        this.reportService = reportService;
        this.driverPdfCreator = driverPdfCreator;
        this.companyPdfCreator = companyPdfCreator;
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = DRIVER_URL + ID_PATH + REPORT_URL)
    public ResponseEntity getReportDriverById(@PathVariable Long id, @RequestParam LocalDate start,
                                              @RequestParam LocalDate end, @RequestParam Format format) {
        ReportDriver report = reportService.generateReportForDriver(id, start, end);
        driverPdfCreator.setReportDriver(report);
        return format.equals(Format.PDF) ? convertToResponsePdf(driverPdfCreator) : ResponseEntity.ok(report);
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = COMPANY_URL + REPORT_URL)
    public ResponseEntity getReportCompanyById(@RequestParam LocalDate start, @RequestParam LocalDate end,
                                               @RequestParam Format format) {
        TripReport report = reportService.generateReportForCompany(start, end);
        companyPdfCreator.setTripReport(report);
        return format.equals(Format.PDF) ? convertToResponsePdf(companyPdfCreator) : ResponseEntity.ok(report);
    }

    private ResponseEntity convertToResponsePdf(PDFCreator format) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(reportService.generateReportInPdf(format).toByteArray());
    }
}
