package pl.dawid.transportapp.dto;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static pl.dawid.transportapp.tool.ObjectTestGenerator.getCorrectDriverDto;
import static pl.dawid.transportapp.tool.ObjectTestGenerator.getIncorrectDriverDto;

class DriverDtoTest {


    private Validator validator;

    @BeforeEach
    void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldSaveWhenDataCorrect() {
        DriverDto driver = getCorrectDriverDto();
        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driver);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    void shouldNotSaveWhenDataWrong() {
        DriverDto driver = getIncorrectDriverDto();
        ;
        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driver);
        Assert.assertEquals(3, violations.size());
    }

    @Test
    void shouldNotSaveWhenPeselHasLetter() {
        DriverDto driver = getCorrectDriverDto();
        driver.setPesel("withLetter");
        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driver);
        Assert.assertFalse(violations.isEmpty());
    }

    @Test
    void shouldNotSaveWhenPeselHasIncorrectSize() {
        DriverDto driver = getCorrectDriverDto();
        driver.setPesel("1234567891");
        Set<ConstraintViolation<DriverDto>> violations = validator.validate(driver);
        Assert.assertFalse(violations.isEmpty());

        driver.setPesel("123456789123");
        violations = validator.validate(driver);
        Assert.assertFalse(violations.isEmpty());
    }
}