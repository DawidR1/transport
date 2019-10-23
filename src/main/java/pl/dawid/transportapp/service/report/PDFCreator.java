package pl.dawid.transportapp.service.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface PDFCreator {

    ByteArrayOutputStream create() throws IOException;
}
