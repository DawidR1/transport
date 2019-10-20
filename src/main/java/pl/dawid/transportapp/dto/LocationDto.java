package pl.dawid.transportapp.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LocationDto {

    private Long id;

    @NotBlank
    private String streetAddress;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
}
