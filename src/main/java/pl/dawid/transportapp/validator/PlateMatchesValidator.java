package pl.dawid.transportapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PlateMatchesValidator implements ConstraintValidator<PlateMatches, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String pattern = "[A-Z]{2,3}[0-9]{4,5}";
        return Pattern.matches(pattern,s);
    }
}
