package pl.dawid.transportapp.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PlateMatchesValidator.class)
public @interface PlateMatches {
    String message() default "{pl.dawid.transportapp.validator.PlateMatches.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
