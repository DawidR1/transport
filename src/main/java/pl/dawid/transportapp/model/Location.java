package pl.dawid.transportapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String streetAddress;

    private String postalCode;

    private String city;

    private String country;

}
