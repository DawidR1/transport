package pl.dawid.transportapp.controller.tool;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class LocationCreator {

    public static URI getLocation(String specificPath, Long expandId) {
        return ServletUriComponentsBuilder
                .fromCurrentServletMapping().path(specificPath + "/{id}")
                .buildAndExpand(expandId)
                .toUri();
    }
}
