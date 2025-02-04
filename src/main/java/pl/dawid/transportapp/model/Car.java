package pl.dawid.transportapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(columnDefinition = "serial")
    private Long id;

    @Column(nullable = false, unique = true)
    private String plate;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;
}
