package pl.dawid.transportapp.tool;

import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.model.Driver;

public class ObjectTestGenerator {

    public static DriverDto getIncorrectDriverDto() {
        DriverDto driver = new DriverDto();
        driver.setFirstName(" ");
        driver.setLastName("    ");
        driver.setPesel("pesel");
        return driver;
    }

    public static DriverDto getCorrectDriverDto(int number) {
        DriverDto driver = new DriverDto();
        driver.setFirstName("firstName" + number);
        driver.setLastName("lastName" + number);
        driver.setPesel("123456789123");
        return driver;
    }

    public static Driver getCorrectDriver(int number) {
        Driver driver = new Driver();
        driver.setFirstName("firstName" + number);
        driver.setLastName("lastName" + number);
        driver.setPesel("123456789123");
        return driver;
    }

    public static CarDto getCorrectCarDto(int number) {
        CarDto car = new CarDto();
        car.setId((long) number);
        car.setPlate("XX9999");
        car.setModel("model" + number);
        car.setBrand("brand" + number);
        return car;
    }

    public static CarDto getIncorrectCarDto() {
        CarDto car = new CarDto();
        car.setPlate(" ");
        car.setModel("    ");
        car.setBrand("");
        return car;
    }

    public static Car getCorrectCar(int number) {
        Car car = new Car();
        car.setId((long) number);
        car.setPlate("XX9999");
        car.setModel("model" + number);
        car.setBrand("brand" + number);
        return car;
    }
}
