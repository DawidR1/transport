package pl.dawid.transportapp.service;

import org.springframework.stereotype.Service;
import pl.dawid.transportapp.dto.CargoDto;
import pl.dawid.transportapp.model.Cargo;
@Service
public class CargoService implements DtoConverter<CargoDto, Cargo> {
}
