package pl.dawid.transportapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawid.transportapp.util.Mappings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LoadingPlaceDto {

    private Long id;

    @Size(min = 1)
    private int nr;

    @NotNull
    @DateTimeFormat(pattern = Mappings.DATE_FORMAT)
    private LocalDate date;

    private List<CargoDto> cargo = new ArrayList<>();

    private LocationDto location;

    private BigDecimal income;
}
