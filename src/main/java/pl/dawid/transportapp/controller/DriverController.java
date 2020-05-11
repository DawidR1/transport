package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.controller.tool.LocationCreator;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.DriverService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.*;

@RestController
public class DriverController {

    private final DriverService service;


    @Autowired
    public DriverController(DriverService service) {
        this.service = service;
    }

    @GetMapping(path = RESOURCE_DRIVER_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<DriverDto> getDriverById(@PathVariable Long id) {
        return service.findDtoById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Driver with id= " + id + " not found"));
    }

    @GetMapping(path = RESOURCE_DRIVER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<DriverDto>> getAllDrivers() {
        List<EntityModel<DriverDto>> resourceList = service.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(DriverController.class).getAllDrivers()).withSelfRel();
        return new CollectionModel<>(resourceList, link);
    }

    @GetMapping(path = DRIVER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<EntityModel<DriverDto>>> getAllDrivers(Pageable pageable, PagedResourcesAssembler<EntityModel<DriverDto>> assembler) {
        Page<DriverDto> page = service.findAll(pageable);
        List<EntityModel<DriverDto>> collect = page.getContent().stream()
                .map(this::mapToResourceWithLink)
                .collect(Collectors.toUnmodifiableList());
        Page<EntityModel<DriverDto>> resources = new PageImpl<>(collect, page.getPageable(), page.getTotalElements());
        return assembler.toModel(resources);
    }

    @PostMapping(path = DRIVER_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<URI> postDriver(@Valid @RequestBody DriverDto driverDto) {
        Long id = service.addDriver(driverDto);
        URI location = LocationCreator.getLocation(DRIVER_URL, id);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Expose-Headers", "Location");
        return ResponseEntity.created(location).headers(headers).build();
    }

    @PutMapping(path = DRIVER_URL + ID_PATH)
    public ResponseEntity<URI> updateDriver(@Valid @RequestBody DriverDto driverDto, @PathVariable Long id) {
        Long idUpdated = service.update(driverDto, id);
        URI location = LocationCreator.getLocation(DRIVER_URL, idUpdated);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        return bodyBuilder.location(location).build();
    }

    private EntityModel<DriverDto> mapToResourceWithLink(DriverDto driver) {
        EntityModel<DriverDto> resource = new EntityModel<>(driver);
        resource.add(linkTo(methodOn(DriverController.class).getDriverById(driver.getId())).withSelfRel());
        if (driver.getImageName() != null) {
            resource.add(linkTo(methodOn(FileController.class).getFile(driver.getImageName())).withRel("image"));
        }
        return resource;
    }


}
