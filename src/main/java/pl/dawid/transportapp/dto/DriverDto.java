package pl.dawid.transportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.enums.DrivingLicenseCategory;
import pl.dawid.transportapp.validator.PeselMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

import static pl.dawid.transportapp.util.Mappings.DATE_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private Long id;

    @PeselMatches
    private String pesel;

    @NotBlank(message = "{pl.dawid.transportapp.dto.empty}")
    private String firstName;

    @NotBlank(message = "{pl.dawid.transportapp.dto.empty}")
    private String lastName;

    private String imageName;

    @Email
    private String email;

    private DrivingLicenseCategory drivingLicense;

    private String phone;

    @DateTimeFormat(pattern = DATE_FORMAT)
    private LocalDate birth;

}
