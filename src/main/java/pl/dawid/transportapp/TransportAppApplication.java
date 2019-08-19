package pl.dawid.transportapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.dawid.transportapp.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class TransportAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportAppApplication.class, args);
    }

}
