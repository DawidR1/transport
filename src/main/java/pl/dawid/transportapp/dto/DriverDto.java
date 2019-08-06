package pl.dawid.transportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {

    private Long id;

    @Size(min = 11, max = 11, message = "{pl.dawid.transportapp.dto.DriverDto.pesel}")
    private String pesel;

    @NotBlank(message = "{pl.dawid.transportcompanyapp.model.entity.Driver.firstName.empty}")
    private String firstName;

    @NotBlank(message = "{pl.dawid.transportcompanyapp.model.entity.Driver.lastName.empty}")
    private String lastName;
}
