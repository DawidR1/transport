package pl.dawid.transportapp.controller;

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
import pl.dawid.transportapp.dto.LocationDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.LocationService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.*;

@RestController
public class LocationController {

    private final LocationService service;

    public LocationController(LocationService service) {
        this.service = service;
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = RESOURCE_LOCATION_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<LocationDto> getLocationById(@PathVariable Long id) {
        return service.findDtoById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Location with id= " + id + " not found"));
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = RESOURCE_LOCATION_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllLocations() {
        List<Resource> resourceList = service.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(LocationController.class).getAllLocations()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = LOCATION_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public PagedResources<Resource<Resource<LocationDto>>> getAllLocations(Pageable pageable, PagedResourcesAssembler<Resource<LocationDto>> assembler) {
        Page<LocationDto> page = service.findAll(pageable);
        List<Resource<LocationDto>> content = page.getContent().stream()
                .map(this::mapToResourceWithLink)
                .collect(Collectors.toUnmodifiableList());
        Page<Resource<LocationDto>> resources = new PageImpl<>(content, page.getPageable(), page.getTotalElements());
        return assembler.toResource(resources);
    }

    @CrossOrigin(value = CROSS_ORIGIN_LOCAL_FRONT, exposedHeaders = "Location")
    @PostMapping(path = LOCATION_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity postLocation(@Valid @RequestBody LocationDto locationDto) {
        Long id = service.addLocation(locationDto);
        URI location = LocationCreator.getLocation(LOCATION_URL, id);
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin(value = CROSS_ORIGIN_LOCAL_FRONT, exposedHeaders = "Location")
    @PutMapping(path = LOCATION_URL + ID_PATH)
    public ResponseEntity updateLocation(@Valid @RequestBody LocationDto locationDto, Long id) {
        Long idUpdated = service.updateLocation(locationDto);
        URI location = LocationCreator.getLocation(LOCATION_URL, idUpdated);
        return ResponseEntity.created(location).build();
    }

    private Resource<LocationDto> mapToResourceWithLink(LocationDto location) {
        Resource<LocationDto> resource = new Resource<>(location);
        resource.add(linkTo(methodOn(LocationController.class).getLocationById(location.getId())).withSelfRel());
        return resource;
    }
}
