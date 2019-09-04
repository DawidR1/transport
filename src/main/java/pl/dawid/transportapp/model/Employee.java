package pl.dawid.transportapp.model;

import lombok.Data;

import javax.persistence.*;

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

    public Employee() {
    }

    public Employee(String pesel, String firstName, String lastName, String imageName) {
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageName = imageName;
    }
}
