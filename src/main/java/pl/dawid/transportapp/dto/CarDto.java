package pl.dawid.transportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private Long id;

    //TODO regex
    private String plate;

    @NotBlank(message = "{pl.dawid.transportapp.dto.empty}")
    private String brand;

    @NotBlank(message = "{pl.dawid.transportapp.dto.empty}")
    private String model;
}
