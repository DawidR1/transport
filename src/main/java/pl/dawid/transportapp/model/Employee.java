package pl.dawid.transportapp.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import static pl.dawid.transportapp.util.Mappings.DATE_FORMAT;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String pesel;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String imageName;

    private String email;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate birth;

    public Employee() {
    }

    public Employee(String pesel, String firstName, String lastName, String imageName, String email, LocalDate birth) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageName = imageName;
        this.email = email;
        this.birth = birth;
    }
}
