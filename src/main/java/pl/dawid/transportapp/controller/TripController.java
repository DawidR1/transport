package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
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
import java.util.stream.Stream;

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

    @GetMapping(path = RESOURCE_TRIP_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<TripDto> getTripById(@PathVariable Long id) {
        return service.getDtoByIdWithLoadingPlaces(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Trip with id= " + id + " not found"));
    }

    @GetMapping(path = RESOURCE_TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllTrip() {
        List<Resource> resourceList = service.findAllWithChildren().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(TripController.class).getAllTrip()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    @GetMapping(path = TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public PagedResources<Resource<Resource<TripDto>>> getAllTrip(Pageable pageable,
                                                                  PagedResourcesAssembler<Resource<TripDto>> assembler,
                                                                  @RequestParam Optional<LocalDate> fromDate,
                                                                  @RequestParam Optional<LocalDate> toDate) {
        Page<TripDto> page = fromDate.isPresent() && toDate.isPresent()
                ? service.findAllWithChildren(pageable, fromDate.get(), toDate.get())
                : service.findAllWithChildren(pageable);
        List<Resource<TripDto>> content = page.getContent().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Page<Resource<TripDto>> resources = new PageImpl<>(content, page.getPageable(), page.getTotalElements());
        return assembler.toResource(resources);
    }

    @PostMapping(path = TRIP_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postDriver(@Valid @RequestBody TripDto tripDto) {
        Long id = service.addTrip(tripDto);
        URI location = LocationCreator.getLocation(DRIVER_URL, id);
        return ResponseEntity.created(location).build();
    }

    @PutMapping(path = TRIP_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateDriver(@Valid @RequestBody TripDto tripDto) {
        Long id = service.addTrip(tripDto);
        URI location = LocationCreator.getLocation(DRIVER_URL, id);
        return ResponseEntity.created(location).build();
    }

    private Resource<TripDto> mapToResourceWithLink(TripDto tripDto) {
        Resource<TripDto> resource = new Resource<>(tripDto);
        resource.add(linkTo(methodOn(TripController.class).getTripById(tripDto.getId())).withSelfRel());
        return resource;
    }

}
