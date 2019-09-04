package pl.dawid.transportapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawid.transportapp.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
