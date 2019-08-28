package pl.dawid.transportapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dawid.transportapp.validator.PeselMatches;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class DriverDto {

    private Long id;

    @PeselMatches
    private String pesel;

    @NotBlank(message = "{pl.dawid.transportapp.dto.empty}")
    private String firstName;

    @NotBlank(message = "{pl.dawid.transportapp.dto.empty}")
    private String lastName;

    private String imageName;

    public DriverDto(Long id, String pesel, String firstName, String lastName) {
        this.id = id;
        this.pesel = pesel;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
