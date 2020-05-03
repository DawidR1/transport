package pl.dawid.transportapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.dawid.transportapp.property.FileStoragePropertiesImpl;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStoragePropertiesImpl.class
})
public class TransportAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransportAppApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


