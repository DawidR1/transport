package pl.dawid.transportapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "seq_cou")
    private Long id;

    private int numberOfPallets;

    private int weight;

    private String companyName;

}
