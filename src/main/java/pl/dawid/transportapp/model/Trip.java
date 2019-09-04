package pl.dawid.transportapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.enums.TripStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal income;

    @OneToOne
    private Location placeStart;

    @OneToOne
    private Location placeFinish;

    @OneToOne
    private Location destination;

    @Column(name = "start_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateStart;

    @Column(name = "finish_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFinish;

    @OneToMany(fetch = FetchType.LAZY)
    private List<LoadingPlace> loadingPlaces;

    @OneToOne
    private Employee employee;

    @OneToOne
    private Car car;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    private Integer distance;

    private BigDecimal cost;

    private Integer fuel;
}

