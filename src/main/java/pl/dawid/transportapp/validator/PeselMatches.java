package pl.dawid.transportapp.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.CONSTRUCTOR})
@Constraint(validatedBy = PeselMatchesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PeselMatches {
    String message() default "{pl.dawid.transportapp.validator.PeselMatches}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
