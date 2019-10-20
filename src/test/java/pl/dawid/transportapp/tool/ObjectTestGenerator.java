package pl.dawid.transportapp.tool;

import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.enums.DrivingLicenseCategory;
import pl.dawid.transportapp.enums.TripStatus;
import pl.dawid.transportapp.model.Car;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.model.Trip;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

public class ObjectTestGenerator {

    public static DriverDto getIncorrectDriverDto() {
        DriverDto driver = new DriverDto();
        driver.setFirstName(" ");
        driver.setLastName("    ");
        driver.setPesel("pesel");
        driver.setBirth(LocalDate.of(2000,12,1));
        driver.setDrivingLicense(DrivingLicenseCategory.A);
        driver.setEmail("example@cos.com");
        return driver;
    }

    public static DriverDto getCorrectDriverDto(int number) {
        DriverDto driver = new DriverDto();
        driver.setFirstName("firstName" + number);
        driver.setLastName("lastName" + number);
        driver.setPesel("123456789123");
        driver.setBirth(LocalDate.of(2000,12,1));
        driver.setDrivingLicense(DrivingLicenseCategory.A);
        driver.setEmail("example@cos.com");
        return driver;
    }

    public static Driver getCorrectDriver(int number) {
        Driver driver = new Driver();
        driver.setFirstName("firstName" + number);
        driver.setLastName("lastName" + number);
        driver.setPesel("123456789123");
        driver.setBirth(LocalDate.of(2000,12,1));
        driver.setDrivingLicense(DrivingLicenseCategory.A);
        driver.setEmail("example@cos.com");
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

    public static Location getCorrectLocation(int number) {
        Location location = new Location();
        location.setCity("Katowice");
        location.setCountry("Poland");
        location.setId((long) number);
        location.setPostalCode("PostalCode");
        location.setStreetAddress("mainRoad");
        return location;
    }

    public static Trip getCorrectTrip(int number) {
        Trip trip = new Trip();
        trip.setCar(getCorrectCar(1));
        trip.setEmployee(getCorrectDriver(1));
        trip.setDateStart(LocalDate.of(2000, Month.JANUARY, 1));
        trip.setDateFinish(LocalDate.of(2001, Month.JANUARY, 1));
        trip.setId((long) number);
        trip.setIncome(BigDecimal.ZERO);
        trip.setPlaceFinish(getCorrectLocation(1));
        trip.setPlaceStart(getCorrectLocation(2));
        trip.setDestination(getCorrectLocation(3));
        trip.setStatus(TripStatus.IN_PROGRESS);
        trip.setFuel(10);
        trip.setDriverSalary(BigDecimal.valueOf(100));
        return trip;
    }

    public static TripDto getCorrectTripViewDto(int number) {
        TripDto trip = new TripDto();
        trip.setCar(getCorrectCar(1));
        trip.setEmployee(getCorrectDriver(1));
        trip.setDateStart(LocalDate.of(2000, Month.JANUARY, 1));
        trip.setDateFinish(LocalDate.of(2001, Month.JANUARY, 1));
        trip.setId((long) number);
        trip.setIncome(BigDecimal.ZERO);
        trip.setPlaceFinish(getCorrectLocation(1));
        trip.setPlaceStart(getCorrectLocation(2));
        trip.setDestination(getCorrectLocation(3));
        trip.setStatus(TripStatus.IN_PROGRESS);
        trip.setFuel(10);
        trip.setDriverSalary(BigDecimal.valueOf(100));
        return trip;
    }
}
