package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.dawid.transportapp.service.DriverService;
import pl.dawid.transportapp.service.FileStorageService;
import pl.dawid.transportapp.util.Mappings;

import static pl.dawid.transportapp.util.Mappings.*;
import static pl.dawid.transportapp.util.Mappings.FILE_URL;

@RestController
@RequestMapping(FILE_URL)
public class FileController {

    private final DriverService service;
    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService, DriverService service) {
        this.fileStorageService = fileStorageService;
        this.service = service;
    }

    @CrossOrigin(CROSS_ORIGIN_LOCAL_FRONT)
    @PostMapping(DRIVER_URL + ID_PATH)
    public ResponseEntity saveFile(@RequestParam("file") MultipartFile multipartFile,
                                   @PathVariable Long id) {
        service.updateDriver(id, multipartFile);
        return ResponseEntity.ok().build();
    }

    @GetMapping(PATH_URL)
    public ResponseEntity<Resource> getFile(@PathVariable String path) {
        return fileStorageService.loadFileAsResource(path)
                .map(resource -> ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource))
                .orElse(ResponseEntity.notFound().build());
    }
}
