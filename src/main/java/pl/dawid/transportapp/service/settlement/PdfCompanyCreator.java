package pl.dawid.transportapp.service.settlement;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dawid.transportapp.dto.*;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Optional;

import static pl.dawid.transportapp.service.settlement.tool.ReportConst.*;

@Component
@Qualifier("companyPdf")
public class PdfCompanyCreator implements PDFCreator{

    private TripReport tripReport;

    @Override
    public ByteArrayOutputStream create() {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArray);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument, PageSize.A4);
        addTitle(document);
        addSummary(document, tripReport);
        addDriverContent(document, tripReport);
        addTableContent(document, tripReport);
        document.close();
        return byteArray;
    }

    private void addTitle(Document document) {

        Paragraph paragraph = new Paragraph(COMPANY_REPORT);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
    }

    private void addTableContent(Document document, TripReport tripReport) {
        float[] point = {116f, 116f, 116f, 116f, 116f};
        Table table = new Table(point);
        document.add(new Paragraph(LIST_OF_ALL_TRIPS));
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
                    table.addCell(trip.getDateFinish().toString());
                    table.addCell(trip.getStatus().toString());
                });
        document.add(table);
    }

    private void addSummary(Document document, TripReport tripReport) {
        Paragraph paragraph = new Paragraph();

        paragraph.add(SUMMARY);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);

        paragraph = new Paragraph();
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
        tripReport.getReportDrivers().stream()
                .map(this::populateContent)
                .forEach(document::add);
    }

    private Paragraph populateContent(ReportDriver reportDriver) {
        DriverDto dto = reportDriver.getDriverDto();
        BigDecimal salary = reportDriver.getTrips().stream()
                .map(TripDto::getDriverSalary)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        StringBuilder content = new StringBuilder();
        content
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
        paragraph.setBorder(new SolidBorder(1));
        return paragraph;
    }

    public void setTripReport(TripReport tripReport) {
        this.tripReport = tripReport;
    }
}
