package pl.dawid.transportapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.dawid.transportapp.service.FileStorageService;

import static pl.dawid.transportapp.util.Mappings.FILE_URL;

@RestController
@RequestMapping(FILE_URL)
public class FileController {


    private final FileStorageService fileStorageService;

    @Autowired
    public FileController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

//    @PostMapping("/{id}") //TODO implementation later
//    public ResponseEntity saveFile(@RequestParam("file") MultipartFile multipartFile, @PathVariable Long id) {
//        Driver driver = driverService.findById(id).orElseThrow(() -> new NotFoundException("Not Found Driver"));
//        driver.getDriverDetails().setImagePath(fileStorageService.storeFile(multipartFile, id));
//        driverService.save(driver);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/{path}")
    public ResponseEntity<Resource> getFile(@PathVariable String path) {
        return fileStorageService.loadFileAsResource(path)
                .map(resource -> ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource))
                .orElse(ResponseEntity.notFound().build());
    }
}
