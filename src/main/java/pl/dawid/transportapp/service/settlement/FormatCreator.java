package pl.dawid.transportapp.service.settlement;

import pl.dawid.transportapp.dto.Report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@FunctionalInterface
public interface FormatCreator {
    ByteArrayOutputStream create(Report report) throws IOException;
}
