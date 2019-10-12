package pl.dawid.transportapp.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.dto.Report;
import pl.dawid.transportapp.enums.Format;
import pl.dawid.transportapp.service.settlement.FormatCreator;
import pl.dawid.transportapp.service.settlement.ReportService;

import java.time.LocalDate;
import java.util.Optional;

import static pl.dawid.transportapp.util.Mappings.*;

@RestController
public class ReportController {
    private final ReportService reportService;
    private final FormatCreator formatCreator;

    public ReportController(ReportService reportService, FormatCreator formatCreator) {
        this.reportService = reportService;
        this.formatCreator = formatCreator;
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = ID_PATH + SETTLEMENT_URL)
    public ResponseEntity getReportDriverById(@PathVariable Long id, @RequestParam Optional<LocalDate> start, @RequestParam Optional<LocalDate> end, @RequestParam Format format) {
        Report report = null;
        if (start.isPresent() && end.isPresent()) {
            report = reportService.generateReport(id, start.get(), end.get());
        } else {
            report = reportService.generateReport(id);
        }
        return format.equals(Format.PDF) ? convertToResponsePdf(report) : ResponseEntity.ok(report);
    }

    private ResponseEntity convertToResponsePdf(Report report) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(reportService.generateReportInFormat(report, formatCreator).toByteArray());
    }
}
