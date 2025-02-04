package pl.dawid.transportapp.service.report;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.stereotype.Component;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.dto.TripReport;
import pl.dawid.transportapp.service.report.tool.CreatorPdf;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Optional;

import static pl.dawid.transportapp.service.report.tool.ReportConst.*;

@Component
@CreatorPdf(type = CreatorPdf.CreatorPdfType.COMPANY_CREATOR)
public class PdfCompanyCreator implements PdfCreator {

    private TripReport tripReport;

    @Override
    public ByteArrayOutputStream create() {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArray);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument, PageSize.A4);
        appendContentToDocument(document);
        document.close();
        return byteArray;
    }

    private void appendContentToDocument(Document document) {
        addTitle(document);
        addSummary(document, tripReport);
        addDriverContent(document, tripReport);
        addTableContent(document, tripReport);
    }

    private void addTitle(Document document) {
        Paragraph paragraph = new Paragraph(COMPANY_REPORT);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setFontSize(14);
        paragraph.setBold();
        document.add(paragraph);
    }

    private void addTableContent(Document document, TripReport tripReport) {
        float[] point = {116f, 116f, 116f, 116f, 116f};
        Table table = new Table(point);
        Paragraph paragraph = new Paragraph(LIST_OF_ALL_TRIPS);
        paragraph.setBold();
        document.add(paragraph);
        table.addHeaderCell("#");
        table.addHeaderCell(DESTINATION);
        table.addHeaderCell(DATE_START);
        table.addHeaderCell(DATE_FINISH);
        table.addHeaderCell(STATUS);
        tripReport.getReportDrivers().stream()
                .flatMap(report -> report.getTrips().stream())
                .forEach(trip -> {
                    table.addCell(trip.getId().toString());
                    table.addCell(trip.getDestination().getCountry());
                    table.addCell(trip.getDateStart().toString());
                    trip.getDateFinish().ifPresentOrElse(date -> table.addCell(date.toString()), () -> table.addCell("empty"));
                    table.addCell(trip.getStatus().toString());
                });
        document.add(table);
    }

    private void addSummary(Document document, TripReport tripReport) {
        Paragraph paragraph = new Paragraph();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(PERIOD + "       ")
                .append(tripReport.getStart())
                .append(" - ")
                .append(tripReport.getEnd())
                .append("\n")
                .append(NUMBER_OF_TRIPS)
                .append(tripReport.getCompanyNumberOfTrips())
                .append("           " + INCOME)
                .append(tripReport.getCompanyIncome());
        paragraph.add(stringBuilder.toString());
        document.add(paragraph);
    }

    private void addDriverContent(Document document, TripReport tripReport) {
        Paragraph paragraph = new Paragraph("List of drivers");
        paragraph.setBold();
        document.add(paragraph);
        tripReport.getReportDrivers().stream()
                .map(this::populateContentWithDriver)
                .forEach(document::add);
    }

    private Paragraph populateContentWithDriver(ReportDriver reportDriver) {
        DriverDto dto = reportDriver.getDriverDto();
        BigDecimal salary = reportDriver.getTrips().stream()
                .map(TripDto::getDriverSalary)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        StringBuilder content = new StringBuilder()
                .append(NAME)
                .append(dto.getFirstName())
                .append(" ")
                .append(dto.getLastName())
                .append("\n")
                .append(PESEL)
                .append(dto.getPesel())
                .append("\n")
                .append(NUMBER_OF_TRIPS)
                .append(reportDriver.getNumberOfTrips())
                .append("\n")
                .append(SALARY)
                .append(salary);
        Paragraph paragraph = new Paragraph(content.toString());
        paragraph.setBorder(new SolidBorder(0.5f));
        return paragraph;
    }

    public void setTripReport(TripReport tripReport) {
        this.tripReport = tripReport;
    }
}
