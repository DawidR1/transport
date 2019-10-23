//package pl.dawid.transportapp.service.settlement;
//
//import org.junit.jupiter.api.Test;
//import pl.dawid.transportapp.dto.ReportEmployee;
//import pl.dawid.transportapp.property.FileStoragePropertiesImpl;
//import pl.dawid.transportapp.service.FileStorageService;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Arrays;
//
//import static pl.dawid.transportapp.tool.ObjectTestGenerator.*;
//
//class PdfCreatorTest {
//
////    private PdfCreator pdfCreator = new PdfCreator(new FileStorageService(new FileStoragePropertiesImpl()));
//
//    @Test
//    void cos() throws IOException {
//        FileStoragePropertiesImpl fileStorageProperties = new FileStoragePropertiesImpl();
//        fileStorageProperties.setUploadDir("./uploads");
//        PdfCreator pdfCreator = new PdfCreator(new FileStorageService(fileStorageProperties));
//        ReportEmployee settlement = new ReportEmployee();
//        settlement.setNumberOfTrips(12);
//        settlement.setSalary(BigDecimal.valueOf(2000));
//        settlement.setTrips(Arrays.asList(getCorrectTripViewDto(1), getCorrectTripViewDto(2)));
//        settlement.setDriverDto(getCorrectDriverDto(1));
//        pdfCreator.create(settlement);
//    }
//
//}