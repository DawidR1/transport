package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.DriverService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.DRIVER;
import static pl.dawid.transportapp.util.Mappings.ID_PATH;

@RestController
@RequestMapping(DRIVER)
public class DriverController {

    private final DriverService service;

    @Autowired
    public DriverController(DriverService service) {
        this.service = service;
    }

    @GetMapping(ID_PATH)
    public Resource<DriverDto> getDriverById(@PathVariable Long id) {
        return service.findById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Driver with id= " + id + " not found"));
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllDrivers() {
        List<Resource> resourceList = service.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(DriverController.class).getAllDrivers()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    private Resource<DriverDto> mapToResourceWithLink(DriverDto driver) {
        Resource<DriverDto> resource = new Resource<>(driver);
        resource.add(linkTo(methodOn(DriverController.class).getDriverById(driver.getId())).withSelfRel());
        return resource;
    }

    @PostMapping
    public ResponseEntity postDriver(@Valid @RequestBody DriverDto driverDto) {
        Long id = service.addDriver(driverDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/car/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity deleteDriver(@PathVariable Long id) {
        service.removeDriver(id);
        return ResponseEntity.ok().build();
    }
}
