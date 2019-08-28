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
import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.CarService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.*;

@RestController
@RequestMapping(CAR_URL)
public class CarController {

    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(path = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Resource<CarDto> getCarById(@PathVariable Long id) {
        return service.findById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Car with id= " + id + " not found"));
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public Resources<Resource> getAllCars() {
        List<Resource> resourceList = service.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(CarController.class).getAllCars()).withSelfRel();
        return new Resources<>(resourceList, link);
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @PostMapping
    public ResponseEntity postCar(@Valid @RequestBody CarDto carDto) {
        Long id = service.addCar(carDto);
        URI location = LocationCreator.getLocation(CAR_URL, id);
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @PutMapping(ID_PATH)
    public ResponseEntity updateCar(@Valid @RequestBody CarDto carDto, @PathVariable Long id) {
        service.update(carDto, id);
        URI location = LocationCreator.getLocation(CAR_URL, id);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        return bodyBuilder.location(location).build();
    }

    @DeleteMapping(ID_PATH)
    public ResponseEntity deleteCar(@PathVariable Long id) {
        service.removeCar(id);
        return ResponseEntity.ok().build();
    }

    private Resource<CarDto> mapToResourceWithLink(CarDto car) {
        Resource<CarDto> resource = new Resource<>(car);
        resource.add(linkTo(methodOn(CarController.class).getCarById(car.getId())).withSelfRel());
        return resource;
    }
}
