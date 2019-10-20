package pl.dawid.transportapp.controller.tool;

import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import pl.dawid.transportapp.util.Mappings;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ControllerAdvice
public class LocalDateControllerAdvice {

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern(Mappings.DATE_FORMAT));
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ofPattern(Mappings.DATE_FORMAT).format(object);
            }
        };
    }
}
