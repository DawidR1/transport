package pl.dawid.transportapp.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface PdfCreator {

    ByteArrayOutputStream create() throws IOException;
}
