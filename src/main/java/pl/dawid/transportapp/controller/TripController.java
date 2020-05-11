package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.controller.tool.LocationCreator;
import pl.dawid.transportapp.dto.TripDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.TripService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.*;

@RestController
public class TripController {

    private final TripService service;

    @Autowired
    public TripController(TripService service) {
        this.service = service;
    }

    @GetMapping(path = RESOURCE_TRIP_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<TripDto> getTripById(@PathVariable Long id) {
        return service.getDtoByIdWithLoadingPlaces(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Trip with id= " + id + " not found"));
    }

    @GetMapping(path = RESOURCE_TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<TripDto>> getAllTrip() {
        List<EntityModel<TripDto>> resourceList = service.findAllWithChildren().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(TripController.class).getAllTrip()).withSelfRel();
        return new CollectionModel<>(resourceList, link);
    }

    @GetMapping(path = TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<EntityModel<TripDto>>> getAllTrip(Pageable pageable,
                                                                         PagedResourcesAssembler<EntityModel<TripDto>> assembler,
                                                                         @RequestParam Optional<LocalDate> fromDate,
                                                                         @RequestParam Optional<LocalDate> toDate) {
        Page<TripDto> page = fromDate.isPresent() && toDate.isPresent()
                ? service.findAllWithChildren(pageable, fromDate.get(), toDate.get())
                : service.findAllWithChildren(pageable);
        List<EntityModel<TripDto>> content = page.getContent().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Page<EntityModel<TripDto>> resources = new PageImpl<>(content, page.getPageable(), page.getTotalElements());
        return assembler.toModel(resources);
    }

    @PostMapping(path = TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<URI> postDriver(@Valid @RequestBody TripDto tripDto) {
        Long id = service.addTrip(tripDto);
        URI location = LocationCreator.getLocation(DRIVER_URL, id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = TRIP_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<URI> updateDriver(@Valid @RequestBody TripDto tripDto) {
        Long id = service.addTrip(tripDto);
        URI location = LocationCreator.getLocation(DRIVER_URL, id);
        return ResponseEntity.created(location).build();
    }

    private EntityModel<TripDto> mapToResourceWithLink(TripDto tripDto) {
        EntityModel<TripDto> resource = new EntityModel<>(tripDto);
        resource.add(linkTo(methodOn(TripController.class).getTripById(tripDto.getId())).withSelfRel());
        return resource;
    }

}
