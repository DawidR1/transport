package pl.dawid.transportapp.dto;

import lombok.Data;

@Data
public class CargoDto {

    private Long id;

    private int numberOfPallets;

    private int weight;

    private String companyName;

}
