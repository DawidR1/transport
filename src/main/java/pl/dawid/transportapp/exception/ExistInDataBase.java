package pl.dawid.transportapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistInDataBase extends RuntimeException {

    public ExistInDataBase(String message) {
        super(message);
    }
}
