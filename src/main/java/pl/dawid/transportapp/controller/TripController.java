package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.controller.tool.LocationCreator;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.dto.TripPostDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.TripService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.*;

@RestController
public class TripController {

    private final TripService service;

    @Autowired
    public TripController(TripService service) {
        this.service = service;
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = TRIP_NARROW_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllNarrowTrip() {
        List<Resource> resourceList = service.findNarrowAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(TripController.class).getAllNarrowTrip()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = TRIP_NARROW_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<TripDto> getNarrowTripById(@PathVariable Long id) {
        return service.getNarrowDtoById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Trip with id= " + id + " not found"));
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = TRIP_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<TripDto> getTripById(@PathVariable Long id) {
        return service.getDtoByIdWithLoadingPlaces(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Trip with id= " + id + " not found"));
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllTrip() {
        List<Resource> resourceList = service.getAllWithChildren().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(TripController.class).getAllNarrowTrip()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    @CrossOrigin(value = CROSS_ORIGIN_LOCAL_FRONT, exposedHeaders = "Location")
    @PostMapping(path = TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postDriver(@Valid @RequestBody TripPostDto tripDto) {
        Long id = service.addTrip(tripDto);
        URI location = LocationCreator.getLocation(DRIVER_URL, id);
        return ResponseEntity.created(location).build();
    }


    private Resource<TripDto> mapToResourceWithLink(TripDto tripDto) { //FIXME trzeba zrobic dla narrow i dla dto
        Resource<TripDto> resource = new Resource<>(tripDto);
        resource.add(linkTo(methodOn(TripController.class).getTripById(tripDto.getId())).withSelfRel());
        return resource;
    }

}
