package pl.dawid.transportapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.enums.TripStatus;
import pl.dawid.transportapp.util.Mappings;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal income;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Location placeStart;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Location placeFinish;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private Location destination;

    @Column(name = "start_date", nullable = false)
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateStart;

    @Column(name = "finish_date")
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateFinish;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id",referencedColumnName = "id")
    private List<LoadingPlace> loadingPlaces;

    @ManyToOne
    private Driver driver;

    @ManyToOne
    private Car car;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private int distance;

    private BigDecimal cost;

    private Integer fuel;
    private BigDecimal driverSalary;

    public Optional<BigDecimal> getDriverSalary() {
        return Optional.ofNullable(driverSalary);
    }

    public Optional<Location> getPlaceFinish() {
        return Optional.ofNullable(placeFinish);
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", income=" + income +
                ", placeStart=" + placeStart +
                ", placeFinish=" + placeFinish +
                ", destination=" + destination +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", driver=" + driver +
                ", car=" + car +
                ", status=" + status +
                ", distance=" + distance +
                ", cost=" + cost +
                ", fuel=" + fuel +
                ", driverSalary=" + driverSalary +
                '}';
    }

    public static class Builder {
        private Long id;
        private BigDecimal income;
        private Location placeStart;
        private Location placeFinish;
        private Location destination;
        private LocalDate dateStart;
        private LocalDate dateFinish;
        private List<LoadingPlace> loadingPlaces;
        private Driver driver;
        private Car car;
        private TripStatus status;
        private int distance;
        private BigDecimal cost;
        private Integer fuel;
        private BigDecimal driverSalary;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder income(BigDecimal income) {
            this.income = income;
            return this;
        }

        public Builder placeStart(Location placeStart) {
            this.placeStart = placeStart;
            return this;
        }

        public Builder placeFinish(Location placeFinish) {
            this.placeFinish = placeFinish;
            return this;
        }

        public Builder destination(Location destination) {
            this.destination = destination;
            return this;
        }

        public Builder dateStart(LocalDate dateStart) {
            this.dateStart = dateStart;
            return this;
        }

        public Builder dateFinish(LocalDate dateFinish) {
            this.dateFinish = dateFinish;
            return this;
        }

        public Builder loadingPlaces(List<LoadingPlace> loadingPlaces) {
            this.loadingPlaces = loadingPlaces;
            return this;
        }

        public Builder driver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder car(Car car) {
            this.car = car;
            return this;
        }

        public Builder status(TripStatus status) {
            this.status = status;
            return this;
        }

        public Builder distance(int distance) {
            this.distance = distance;
            return this;
        }

        public Builder cost(BigDecimal cost) {
            this.cost = cost;
            return this;
        }

        public Builder fuel(Integer fuel) {
            this.fuel = fuel;
            return this;
        }

        public Builder driverSalary(BigDecimal driverSalary) {
            this.driverSalary = driverSalary;
            return this;
        }

        public Trip build() {
            Trip trip = new Trip();
            trip.setId(this.id);
            trip.setIncome(this.income);
            trip.setPlaceStart(this.placeStart);
            trip.setPlaceFinish(this.placeFinish);
            trip.setDestination(this.destination);
            trip.setDateStart(this.dateStart);
            trip.setDateFinish(this.dateFinish);
            trip.setLoadingPlaces(this.loadingPlaces);
            trip.setDriver(this.driver);
            trip.setCar(this.car);
            trip.setStatus(this.status);
            trip.setDistance(this.distance);
            trip.setCost(this.cost);
            trip.setFuel(this.fuel);
            trip.setDriverSalary(this.driverSalary);
            return trip;
        }
    }
}

