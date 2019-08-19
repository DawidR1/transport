package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.dawid.transportapp.dto.DriverDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.DriverService;
import pl.dawid.transportapp.util.Mappings;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.DRIVER_URL;
import static pl.dawid.transportapp.util.Mappings.ID_PATH;

@RestController
@RequestMapping(DRIVER_URL)
public class DriverController {

    private final DriverService service;

    @Autowired
    public DriverController(DriverService service) {
        this.service = service;
    }

    @CrossOrigin(Mappings.CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<DriverDto> getDriverById(@PathVariable Long id) {
        return service.findById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Driver with id= " + id + " not found"));
    }

    @CrossOrigin(Mappings.CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllDrivers() {
        List<Resource> resourceList = service.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(DriverController.class).getAllDrivers()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    @PostMapping
    public ResponseEntity postDriver(@Valid @RequestBody DriverDto driverDto) {
        Long id = service.addDriver(driverDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/car/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(ID_PATH) //FIXME remove entity with picture
    public ResponseEntity deleteDriver(@PathVariable Long id) {
        service.removeDriver(id);
        return ResponseEntity.ok().build();
    }

    private Resource<DriverDto> mapToResourceWithLink(DriverDto driver) {
        Resource<DriverDto> resource = new Resource<>(driver);
        resource.add(linkTo(methodOn(DriverController.class).getDriverById(driver.getId())).withSelfRel());
        if(driver.getImageName() != null){
            resource.add(linkTo(methodOn(FileController.class).getFile(driver.getImageName())).withRel("image"));
        }
        return resource;
    }
}
