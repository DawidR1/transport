package pl.dawid.transportapp.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.dawid.transportapp.exception.file.FileStorageException;
import pl.dawid.transportapp.exception.file.FileNotFoundException;
import pl.dawid.transportapp.property.FileStoragePropertiesImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(FileStoragePropertiesImpl fileStoragePropertiesImpl) {
        this.fileStorageLocation = Paths.get(fileStoragePropertiesImpl.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("{could_not_create_directory_for_files}", ex);
        }
    }

    public String storeFile(MultipartFile file, Long id) {
        String name = id + file.getOriginalFilename();
        String fileName = StringUtils.cleanPath(name);
        try {

            if (fileName.contains("..")) {
                throw new FileStorageException("{invalid_file_path} " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Optional<Resource> loadFileAsResource(String fileName) {
        try {
            Path filePath = getPathFile(fileName);
            Resource resource = new UrlResource(filePath.toUri());
            return resource.exists() ? Optional.of(resource) : Optional.empty();
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName, ex);
        }
    }

    public Path getPathFile(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize(); //FIXME null
    }
}
