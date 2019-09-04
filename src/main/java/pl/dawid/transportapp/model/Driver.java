
package pl.dawid.transportapp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.dawid.transportapp.enums.DrivingLicenseCategory;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@NoArgsConstructor
public class Driver extends Employee{

    public Driver(String pesel, String firstName, String lastName, String imageName, DrivingLicenseCategory drivingLicense) {
        super(pesel, firstName, lastName, imageName);
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
