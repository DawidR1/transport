package pl.dawid.transportapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PeselMatchesValidator implements ConstraintValidator<PeselMatches, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String pattern = "\\d{12}";
        return Pattern.matches(pattern,s);
    }
}
