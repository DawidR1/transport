package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.model.Driver;
import pl.dawid.transportapp.repository.DriverRepository;
import pl.dawid.transportapp.util.Mappings;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(Mappings.DRIVER)
public class DriverController {

    private final DriverRepository driverRepository;

    @Autowired
    public DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @GetMapping("/{id}")
    public Resource<Driver> getDriverById(@PathVariable Long id) {
        return driverRepository.findById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Driver with id= "+id+" not found"));
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllDrivers() {
        List<Resource> resourceList = driverRepository.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(DriverController.class).getAllDrivers()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    private Resource<Driver> mapToResourceWithLink(Driver driver) {
        Resource<Driver> resource = new Resource<>(driver);
        resource.add(linkTo(methodOn(DriverController.class).getDriverById(driver.getId())).withSelfRel());
        return resource;
    }
}
