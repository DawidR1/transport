package pl.dawid.transportapp.service.settlement;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.dto.Report;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.service.FileStorageService;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class PdfCreator implements FormatCreator {

    public static final String PDF_TITLE = "Settlement.pdf";

    private int x = 580;
    private int y = 850;

    private final FileStorageService fileStorageService;

    @Autowired
    public PdfCreator(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ByteArrayOutputStream create(Report report) {
        ReportDriver employeeReport = (ReportDriver) report;
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArray);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument, PageSize.A4);

        addTitle(document);
        addProfileImage(document, employeeReport);
        addPersonalInformation(document, employeeReport);
        addSummary(document, employeeReport);
        addTableContent(document, employeeReport);
        document.close();
        return byteArray;
    }

    private void addTableContent(Document document, ReportDriver employeeReport) {
        float[] point = {116f, 116f, 116f, 116f, 116f};
        Table table = new Table(point);
        table.setFixedPosition(50, 400, 400);
        table.addHeaderCell("#");
        table.addHeaderCell("Destination");
        table.addHeaderCell("Date Start");
        table.addHeaderCell("Date Finish");
        table.addHeaderCell("Status");
        for (TripDto trip : employeeReport.getTrips()) {
            table.addCell(trip.getId().toString());
            table.addCell(trip.getDestination().getCountry());
            table.addCell(trip.getDateStart().toString());
            table.addCell(trip.getDateFinish().toString());
            table.addCell(trip.getStatus().toString());
        }
        document.add(table);
    }

    private void addSummary(Document document, ReportDriver employeeReport) {
        Paragraph paragraph = new Paragraph();

        String summary = "Summary";
        paragraph.add(summary);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setFixedPosition(50, 520, 400);
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph.setFixedPosition(50, 450, 400);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Period: ")
                .append(Optional.ofNullable(employeeReport.getStart())
                        .map(LocalDate::toString)
                        .orElse("Undefined"))
                .append(" - ")
                .append(Optional.ofNullable(employeeReport.getEnd())
                        .map(LocalDate::toString)
                        .orElse("Undefined"))
                .append("\n")
                .append("Number of Trips: ")
                .append(employeeReport.getNumberOfTrips())
                .append("           Salary: ")
                .append(employeeReport.getSalary());
        paragraph.add(stringBuilder.toString());
        document.add(paragraph);
    }

    private void addPersonalInformation(Document document, ReportDriver employeeReport) {
        DriverDto employee = employeeReport.getDriverDto();
        StringBuilder informationBuilder = new StringBuilder();
        informationBuilder
                .append("Name: ")
                .append(employee.getFirstName())
                .append(" ")
                .append(employee.getLastName())
                .append("\n\n")
                .append("Pesel: ")
                .append(employee.getPesel())
                .append("\n\n")
                .append("Birth: ")
                .append(employee.getBirth());
        Paragraph paragraph = new Paragraph(informationBuilder.toString());
        paragraph.setFixedPosition(250, 620, 400);
        document.add(paragraph);
    }

    private void addTitle(Document document) {
        String head = "REPORT";
        Paragraph paragraph = new Paragraph(head);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
    }

    private void addProfileImage(Document document, ReportDriver employeeReport) {
        getImage(employeeReport).stream()
                .peek(imageData -> {
                    imageData.setHeight(100);
                    imageData.setWidth(100);
                })
                .map(Image::new)
                .peek(image -> image.setFixedPosition(50, 620)) //y 850 x 580
                .findAny()
                .ifPresent(document::add);
    }

    private Optional<ImageData> getImage(ReportDriver employeeReport) {
        Optional<Path> pathFile = Optional.ofNullable(employeeReport.getDriverDto().getImageName())
                .map(fileStorageService::getPathFile);

        if (!pathFile.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(ImageDataFactory.create(pathFile.get().toString()));
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
    }
}
