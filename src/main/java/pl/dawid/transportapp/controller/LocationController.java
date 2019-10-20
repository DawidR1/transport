package pl.dawid.transportapp.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.dto.LocationDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.LocationService;
import pl.dawid.transportapp.util.Mappings;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.CROSS_ORIGIN_LOCAL_FRONT;

@RestController
@RequestMapping(Mappings.LOCATION_URL)
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = Mappings.ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<LocationDto> getLocationById(@PathVariable Long id) {
        return service.findDtoById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Location with id= " + id + " not found"));
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllLocations() {
        List<Resource> resourceList = service.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(LocationController.class)
                .getAllLocations())
                .withSelfRel();
        return new Resources<>(resourceList, link);
    }

    private Resource<LocationDto> mapToResourceWithLink(LocationDto location) {
        Resource<LocationDto> resource = new Resource<>(location);
        resource.add(linkTo(methodOn(LocationController.class)
                .getLocationById(location.getId()))
                .withSelfRel());
        return resource;
    }
}
