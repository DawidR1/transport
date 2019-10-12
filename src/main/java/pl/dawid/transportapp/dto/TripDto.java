package pl.dawid.transportapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.enums.TripStatus;
import pl.dawid.transportapp.model.Location;
import pl.dawid.transportapp.util.Mappings;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripDto {

    private TripStatus status;
    private Long id;
    private Location destination;
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateStart;
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate dateFinish;
    private Location placeFinish;

}
