package pl.dawid.transportapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ReportGeneratorException extends RuntimeException {

    public ReportGeneratorException(String message) {
        super(message);
    }
}
