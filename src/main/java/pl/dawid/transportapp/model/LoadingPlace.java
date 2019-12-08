package pl.dawid.transportapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.util.Mappings;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class LoadingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int nr;

    @Column(nullable = false)
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate date;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Cargo> cargo = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Location location;

    private BigDecimal income;

    @Column(name = "finished")
    private boolean isFinished;

}
