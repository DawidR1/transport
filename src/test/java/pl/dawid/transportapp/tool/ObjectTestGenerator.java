package pl.dawid.transportapp.tool;

import pl.dawid.transportapp.dto.DriverDto;

public class ObjectTestGenerator {

    public static DriverDto getIncorrectDriverDto() {
        DriverDto driver = new DriverDto();
        driver.setFirstName(" ");
        driver.setLastName("    ");
        driver.setPesel("pesel");
        return driver;
    }

    public static DriverDto getCorrectDriverDto() {
        DriverDto driver = new DriverDto();
        driver.setFirstName("firstName");
        driver.setLastName("lastName");
        driver.setPesel("12345678912");
        return driver;
    }
}
