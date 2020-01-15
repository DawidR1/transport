
package pl.dawid.transportapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dawid.transportapp.enums.DrivingLicenseCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Driver extends Employee {

    public Driver(String pesel, String firstName, String lastName, String imageName, String email, LocalDate birth, String phone,
                  DrivingLicenseCategory drivingLicense) {
        super(pesel, firstName, lastName, imageName, email, birth, phone);
        this.drivingLicense = drivingLicense;
    }

    @Enumerated(EnumType.STRING)
    private DrivingLicenseCategory drivingLicense;

    @Override
    public String toString() {
        return super.toString() + " Driver{" +
                "drivingLicense=" + drivingLicense +
                '}';
    }
}
