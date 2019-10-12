package pl.dawid.transportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.dto.TripViewDto;
import pl.dawid.transportapp.model.Trip;
import pl.dawid.transportapp.repository.TripRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TripService implements DtoConverter<TripDto, Trip> {

    private TripRepository repository;

    @Autowired
    public TripService(TripRepository tripRepository) {
        this.repository = tripRepository;
    }

    public Optional<TripDto> getDtoByIdWithLoadingPlaces(long id) {
        return repository.findByIdWithLoadingPlaces(id) //TODO dorobic zaciaganie loading places
                .map(entity -> convertToDto(entity, new TripViewDto()));
    }

    public Optional<TripDto> getNarrowDtoById(long id) {
        return repository.findById(id)
                .map(entity -> convertToDto(entity, new TripDto()));
    }

    public List<TripDto> findAll() {
        return repository.findAll().stream()
                .map(entity -> convertToDto(entity, new TripViewDto()))
                .collect(toList());
    }

    public List<TripDto> findNarrowAll() {
        return repository.findAll().stream()
                .map(entity -> convertToDto(entity, new TripDto()))
                .collect(toList());
    }
}
