package pl.dawid.transportapp.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.dawid.transportapp.model.Trip;
import pl.dawid.transportapp.repository.TripRepository;
import pl.dawid.transportapp.tool.ObjectTestGenerator;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository repository;

    @InjectMocks
    private TripService service;

    private Trip trip;

    @BeforeEach
    void init() {
        trip = ObjectTestGenerator.getCorrectTrip(1);
        when(repository.findById(any())).thenReturn(Optional.of(trip));
    }

//    @Test
//    void shouldReturnNullCarWhenNarrowDto(){
//        service.getDtoById(1).get()
//    }

}