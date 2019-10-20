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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.dto.ReportDriver;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.service.FileStorageService;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.Optional;

import static pl.dawid.transportapp.service.settlement.tool.ReportConst.*;

@Component
@Qualifier("driverPdf")
public class PdfDriverCreator implements PDFCreator {

    private int x = 580;
    private int y = 850;

    private final FileStorageService fileStorageService;
    private ReportDriver reportDriver;

    @Autowired
    public PdfDriverCreator(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Override
    public ByteArrayOutputStream create() {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArray);
        PdfDocument pdfDocument = new PdfDocument(writer);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument, PageSize.A4);
        populatePdfContent(document, reportDriver);
        document.close();
        return byteArray;
    }

    private void populatePdfContent(Document document, ReportDriver reportDriver) {
        addTitle(document);
        addProfileImage(document, reportDriver);
        addPersonalInformation(document, reportDriver);
        addSummary(document, reportDriver);
        addTableContent(document, reportDriver);
    }


    private void addTableContent(Document document, ReportDriver reportDriver) {
        float[] point = {116f, 116f, 116f, 116f, 116f};
        Table table = new Table(point);
        table.setFixedPosition(50, 400, 400);
        table.addHeaderCell("#");
        table.addHeaderCell(DESTINATION);
        table.addHeaderCell(DATE_START);
        table.addHeaderCell(DATE_FINISH);
        table.addHeaderCell(STATUS);
        for (TripDto trip : reportDriver.getTrips()) {
            table.addCell(trip.getId().toString());
            table.addCell(trip.getDestination().getCountry());
            table.addCell(trip.getDateStart().toString());
            table.addCell(trip.getDateFinish().toString());
            table.addCell(trip.getStatus().toString());
        }
        document.add(table);
    }

    private void addSummary(Document document, ReportDriver reportDriver) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(SUMMARY);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        paragraph.setFixedPosition(50, 520, 400);
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph.setFixedPosition(50, 450, 400);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(PERIOD)
                .append(reportDriver.getStart())
                .append(" - ")
                .append(reportDriver.getEnd())
                .append("\n")
                .append(NUMBER_OF_TRIPS)
                .append(reportDriver.getNumberOfTrips())
                .append("          " + SALARY)
                .append(reportDriver.getSalary());
        paragraph.add(stringBuilder.toString());
        document.add(paragraph);
    }

    private void addPersonalInformation(Document document, ReportDriver reportDriver) {
        DriverDto employee = reportDriver.getDriverDto();
        StringBuilder informationBuilder = new StringBuilder();
        informationBuilder
                .append(NAME)
                .append(employee.getFirstName())
                .append(" ")
                .append(employee.getLastName())
                .append("\n\n")
                .append(PESEL)
                .append(employee.getPesel())
                .append("\n\n")
                .append(BIRTH)
                .append(employee.getBirth());
        Paragraph paragraph = new Paragraph(informationBuilder.toString());
        paragraph.setFixedPosition(350, 650, 400);
        document.add(paragraph);
    }

    private void addTitle(Document document) {
        Paragraph paragraph = new Paragraph(REPORT);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
    }

    private void addProfileImage(Document document, ReportDriver reportDriver) {
        getImage(reportDriver).stream()
                .peek(imageData -> {
                    imageData.setHeight(130);
                    imageData.setWidth(150);
                })
                .map(Image::new)
                .peek(image -> image.setFixedPosition(50, 620)) //y 850 x 580
                .findAny()
                .ifPresent(document::add);
    }

    private Optional<ImageData> getImage(ReportDriver reportDriver) {
        Optional<Path> pathFile = Optional.ofNullable(reportDriver.getDriverDto().getImageName())
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

    public void setReportDriver(ReportDriver reportDriver) {
        this.reportDriver = reportDriver;
    }
}
