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
import pl.dawid.transportapp.dto.CarDto;
import pl.dawid.transportapp.exception.NotFoundException;
import pl.dawid.transportapp.service.CarService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static pl.dawid.transportapp.util.Mappings.*;

@RestController
public class CarController {

    private final CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping(path = RESOURCE_CAR_URL + ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<CarDto> getCarById(@PathVariable Long id) {
        return service.findDtoById(id)
                .map(this::mapToResourceWithLink)
                .orElseThrow(() -> new NotFoundException("Car with id= " + id + " not found"));
    }

    @GetMapping(path = RESOURCE_CAR_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<CarDto>> getAllCars() {
        List<EntityModel<CarDto>> resourceList = service.findAll().stream()
                .map(this::mapToResourceWithLink)
                .collect(toList());
        Link link = linkTo(methodOn(CarController.class).getAllCars()).withSelfRel();
        return new CollectionModel<>(resourceList, link);
    }

    @GetMapping(path = CAR_URL, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.OK)
    public CollectionModel<EntityModel<EntityModel<CarDto>>> getAllCars(Pageable pageable, PagedResourcesAssembler<EntityModel<CarDto>> assembler) {
        Page<CarDto> page = service.findAll(pageable);
        List<EntityModel<CarDto>> collect = page.getContent().stream()
                .map(this::mapToResourceWithLink)
                .collect(Collectors.toUnmodifiableList());
        Page<EntityModel<CarDto>> resources = new PageImpl<>(collect, page.getPageable(), page.getTotalElements());
        return assembler.toModel(resources);
    }

    @PostMapping(path = CAR_URL)
    public ResponseEntity<URI> postCar(@Valid @RequestBody CarDto carDto) {
        Long id = service.addCar(carDto);
        URI location = LocationCreator.getLocation(CAR_URL, id);
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin(exposedHeaders = "Location")
    @PutMapping(path = CAR_URL + ID_PATH)
    public ResponseEntity<URI> updateCar(@Valid @RequestBody CarDto carDto, @PathVariable Long id) {
        service.update(carDto, id);
        URI location = LocationCreator.getLocation(CAR_URL, id);
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();
        return bodyBuilder.location(location).build();
    }

    private EntityModel<CarDto> mapToResourceWithLink(CarDto car) {
        EntityModel<CarDto> resource = new EntityModel<>(car);
        resource.add(linkTo(methodOn(CarController.class).getCarById(car.getId())).withSelfRel());
        return resource;
    }
}
